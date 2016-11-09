package com.dipesan.miniatm.miniatm.view.settings;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.BluetoothConnexionManager;
import com.dipesan.miniatm.miniatm.services.UIUtils;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.youTransactor.uCube.ITaskMonitor;
import com.youTransactor.uCube.TaskEvent;
import com.youTransactor.uCube.mdm.MDMManager;
import com.youTransactor.uCube.rpc.Constants;
import com.youTransactor.uCube.rpc.DeviceInfos;
import com.youTransactor.uCube.rpc.command.DisplayMessageCommand;
import com.youTransactor.uCube.rpc.command.GetInfosCommand;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private static final String TAG = "BalanceFragment";
    @BindView(R.id.settings_save_button) Button settingsSaveButton;
    @BindView(R.id.settings_device_edit_text) EditText settingsDeviceEditText;
    @BindView(R.id.settings_auto_connect_switch) Switch settingsAutoConnectSwitch;
    @BindView(R.id.settings_server_edit_text) EditText settingsServerEditText;

    private BluetoothDevice selectDevice;
    private boolean nfcEnabled;
    private String deviceSerial,devicepartNumber;

    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_settings, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences (getContext());

        nfcEnabled = settings.getBoolean(AppConstant.NFC_ENABLE_DEVICE_SETTINGS_KEY, false);

        deviceSerial = settings.getString(MDMManager.MDM_DEVICE_SERIAL_SETTINGS_KEY, null);
        devicepartNumber = settings.getString(MDMManager.MDM_DEVICE_PART_NUMBER_SETTINGS_KEY, null);
        settingsServerEditText.setText(settings.getString(MDMManager.MDM_SERVER_URL_SETTINGS_KEY, MDMManager.DEFAULT_URL));
        settingsDeviceEditText.setText(settings.getString(AppConstant.DEVICE_NAME_SETTINGS_KEY,""));
        settingsDeviceEditText.setInputType(InputType.TYPE_NULL);
         return view;
    }

    private void saveSettings() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = settings.edit();

        if (selectDevice != null) {
            editor.putString(AppConstant.DEVICE_MAC_ADDR_SETTINGS_KEY, selectDevice.getAddress());

            if (StringUtils.isBlank(selectDevice.getName())) {
                editor.putString(AppConstant.DEVICE_NAME_SETTINGS_KEY, selectDevice.getAddress());
            } else {
                editor.putString(AppConstant.DEVICE_NAME_SETTINGS_KEY, selectDevice.getName());
            }
        }

        editor.putString(MDMManager.MDM_SERVER_URL_SETTINGS_KEY, settingsServerEditText.getText().toString());

        editor.putBoolean(AppConstant.NFC_ENABLE_DEVICE_SETTINGS_KEY, nfcEnabled);

        editor.putString(MDMManager.MDM_DEVICE_SERIAL_SETTINGS_KEY, deviceSerial);
        editor.putString(MDMManager.MDM_DEVICE_PART_NUMBER_SETTINGS_KEY, devicepartNumber);

        // editor.putBoolean(LogManager.LOG_MANAGER_STATE_SETTINGS_KEY, logSwitch.isChecked());

        editor.apply();

        BluetoothConnexionManager.getInstance().initialize(settings);
        //   LogManager.setEnabled(logSwitch.isChecked());

        Toast.makeText(getContext(), "Settings stored successfuly", Toast.LENGTH_SHORT).show();
    }

    private void showDevice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Select uCube device");

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        final List<BluetoothDevice> deviceList = new ArrayList<>(adapter.getBondedDevices());
        Collections.sort(deviceList, new Comparator<BluetoothDevice>() {
            @Override
            public int compare(BluetoothDevice lhs, BluetoothDevice rhs) {
                String v1 = lhs.getName();
                if (StringUtils.isBlank(v1)) {
                    v1 = lhs.getAddress();
                }

                String v2 = rhs.getName();
                if (StringUtils.isBlank(v1)) {
                    v2 = rhs.getAddress();
                }

                return v1.compareToIgnoreCase(v2);
            }
        });

        final String[] labels = new String[deviceList.size()];
        for (int i = 0; i < deviceList.size(); i++) {
            labels[i] = deviceList.get(i).getName();

            if (StringUtils.isEmpty(labels[i])) {
                labels[i] = deviceList.get(i).getAddress();
            }
        }

        builder.setItems(labels, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectDevice = deviceList.get(which);

                BluetoothConnexionManager.getInstance().setDeviceAddr(selectDevice.getAddress());

                UIUtils.showProgress(getContext(), "Check device model");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new GetInfosCommand(Constants.TAG_TERMINAL_SN, Constants.TAG_TERMINAL_PN, Constants.TAG_MPOS_MODULE_STATE).execute(new ITaskMonitor() {
                            @Override
                            public void handleEvent(final TaskEvent event, final Object... params) {
                                if (event == TaskEvent.PROGRESS) {
                                    return;
                                }

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        switch (event) {
                                            case FAILED:
                                                nfcEnabled = false;
                                                deviceSerial = null;
                                                devicepartNumber = null;
                                                UIUtils.showMessageDialog(getContext(), "Unable to connect to device !\nNFC will be disabled.");
                                                break;

                                            case SUCCESS:
                                                DeviceInfos deviceInfos = new DeviceInfos(((GetInfosCommand) params[0]).getResponseData());
                                                nfcEnabled = deviceInfos != null && deviceInfos.getNfcModuleState() != 0;
                                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(AppConstant.NFC_ENABLE_DEVICE_SETTINGS_KEY, nfcEnabled).apply();
                                                deviceSerial = deviceInfos.getSerial();
                                                devicepartNumber = deviceInfos.getPartNumber();

                                                break;
                                        }

                                        UIUtils.hideProgressDialog();
                                    }
                                });
                            }
                        });
                    }
                }).start();

                settingsDeviceEditText.setText(labels[which]);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();


}

    @OnClick({R.id.settings_save_button, R.id.settings_device_edit_text, R.id.settings_server_edit_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_save_button:
                saveSettings();
                break;
            case R.id.settings_device_edit_text:
                showDevice();
                break;
            case R.id.settings_server_edit_text:

                break;
        }
    }

    private void startDisplay() {
        final ProgressDialog progressDlg = UIUtils.showProgress(getContext(), "Display message");

        new Thread(new Runnable() {
            @Override
            public void run() {
                new DisplayMessageCommand("Selamat Datang").execute(new ITaskMonitor() {
                    @Override
                    public void handleEvent(final TaskEvent event, Object... params) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (event) {
                                    case FAILED:
                                        UIUtils.showMessageDialog(getContext(), "Message display failure");
                                        break;

                                    case SUCCESS:
                                        Toast.makeText(getContext(), "Message displayed successfully", Toast.LENGTH_LONG).show();
                                        break;

                                    default:
                                        return;
                                }

                                progressDlg.dismiss();
                            }
                        });
                    }
                });
            }
        }).start();
    }



}
