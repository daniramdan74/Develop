package com.dipesan.miniatm.miniatm.view.purchase;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.SQLiteDatabase.DatabaseHelper;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasePhoneCreditFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "PhoneCredit";
    private final CharSequence[] itemProviders = {"Telkomsel", "Indosat Ooredoo", "XL", "Three", "Smartfren"};
    private final CharSequence[] itemsNominal = {"25.000", "50.000", "100.000", "500.000"};
    private final int[] itemPrices = {30000, 50000, 100000, 150000, 200000, 300000, 500000};
    @BindView(R.id.fragment_phone_credit_data_matches_check_box) CheckBox fragmentPhoneCreditDataMatchesCheckBox;
    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
    @BindView(R.id.fragment_phone_credit_provider_edit_text) EditText fragmentPhoneCreditProviderEditText;
    @BindView(R.id.fragment_phone_credit_process_button) Button fragmentPhoneCreditProcessButton;
    @BindView(R.id.fragment_phone_credit_nominal_edit_text) EditText fragmentPhoneCreditNominalEditText;
    @BindView(R.id.fragment_phone_number_edit_text) EditText fragmentPhoneNumberEditText;
    @BindView(R.id.fragphone_text_input_layout_credit_provider) TextInputLayout fragphoneTextInputLayoutCreditProvider;
    @BindView(R.id.fragphone_text_input_layout_nominal) TextInputLayout fragphoneTextInputLayoutNominal;
    @BindView(R.id.fragphone_text_input_layout_phone_number) TextInputLayout fragphoneTextInputLayoutPhoneNumber;
    @BindView(R.id.fragpurchase_electric_choices_radio_group) RadioGroup fragpurchaseElectricChoicesRadioGroup;
    @BindView(R.id.fragpurchase_electric_reguler_radio_button) RadioButton fragpurchaseElectricRegulerRadioButton;
    @BindView(R.id.fragpurchase_electric_data_radio_button) RadioButton fragpurchaseElectricDataRadioButton;
    private TextView ProviderTextView, NominalTextView, NumberTextView;
    private CheckBox ConfirmCheckbox;
    private Button cancelButton, confirmButton;
    private boolean checkflag;
    private int whichItemProvider = 0;
    private int whichItemNominal = 0;
    private YoucubeService youcubeService;
    private V1Printer printer;
    private ICallback iCallback = new ICallback() {

        @Override
        public void onRunResult(boolean isSuccess) {
            Log.i(TAG, "onRunResult:" + isSuccess);
        }

        @Override
        public void onReturnString(String result) {
            Log.i(TAG, "onReturnString:" + result);
        }

        @Override
        public void onRaiseException(int code, String msg) {
            Log.i(TAG, "onRaiseException:" + code + ":" + msg);
        }

    };

    public PurchasePhoneCreditFragment() {
        // Required empty public constructor
    }

    public static PurchasePhoneCreditFragment newIstance() {
        return new PurchasePhoneCreditFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_phone_credit, container, false);
        ButterKnife.bind(this, view);
        youcubeService = new YoucubeService(getActivity());
        fragmentPhoneCreditProviderEditText.setInputType(InputType.TYPE_NULL);
        fragmentPhoneCreditNominalEditText.setInputType(InputType.TYPE_NULL);
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        fragmentPhoneCreditProcessButton.setEnabled(false);
        fragmentPhoneCreditDataMatchesCheckBox.setOnCheckedChangeListener(this);
        youcubeService = new YoucubeService(getActivity());
        fragpurchaseElectricRegulerRadioButton.setChecked(true);
       choicesRadioGroup();
        return view;
    }

    private void choicesRadioGroup() {
        fragpurchaseElectricChoicesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int chechkedId) {
                int state;
                state = fragpurchaseElectricChoicesRadioGroup.indexOfChild(getActivity().findViewById(chechkedId));
                    switch (state){
                        case 0:
                            fragmentPhoneCreditNominalEditText.setText(null);
                            break;
                        case 1:
                            fragmentPhoneCreditNominalEditText.setText(null);
                            break;
                    }
                }
        });
    }

    @OnClick({R.id.fragment_phone_credit_provider_edit_text, R.id.fragment_phone_credit_nominal_edit_text, R.id.fragment_phone_credit_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_phone_credit_provider_edit_text:
                hideKeyboard(view);
                showProvider();
                break;
            case R.id.fragment_phone_credit_nominal_edit_text:
                hideKeyboard(view);
                showNominal();
                break;
            case R.id.fragment_phone_credit_process_button:
                showProcess();
                break;
        }
    }

    private void showProcess() {
        if (fragmentPhoneCreditProviderEditText.getText().toString().isEmpty()) {
            fragphoneTextInputLayoutCreditProvider.setError("Tidak Boleh Kosong");
        }
        else {
            fragphoneTextInputLayoutCreditProvider.setErrorEnabled(false);
        }
        if (fragmentPhoneCreditNominalEditText.getText().toString().isEmpty()) {
            fragphoneTextInputLayoutNominal.setError("Tidak Boleh Kosong");
        }
        else {
            fragphoneTextInputLayoutNominal.setErrorEnabled(false);
        }
        if (fragmentPhoneNumberEditText.getText().toString().isEmpty()) {
            fragphoneTextInputLayoutPhoneNumber.setError("Tidak Boleh Kosong");
        }
        else {
            fragphoneTextInputLayoutPhoneNumber.setErrorEnabled(false);
        }
        if (fragmentPhoneCreditProviderEditText.getText().toString().length() > 0 &&
                fragmentPhoneCreditNominalEditText.getText().toString().length() > 0 &&
                fragmentPhoneNumberEditText.getText().toString().length() > 0) {
            youcubeService.setIsMessage(true);
            youcubeService.setMessage(getString(R.string.insertCard));
            youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
                @Override
                public void onApproved() {
                    //print();
                    Toast.makeText(getActivity(), "Print", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void print() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nPembelian Pulsa");
                printer.printText("\n===============================");
                printer.printText("\nProvider :");
                printer.printText("\n" + itemProviders[whichItemProvider]);
                printer.printText("\nNominal :");
                printer.printText("\n" + itemsNominal[whichItemNominal]);
                printer.printText("\nNomor Telpon : \n" + fragmentPhoneNumberEditText.getText().toString());
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID" + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
        fragmentPhoneCreditProviderEditText.setText(null);
        fragmentPhoneCreditNominalEditText.setText(null);
        fragmentPhoneNumberEditText.setText(null);
        fragmentPhoneCreditProcessButton.setEnabled(false);
        editDataEnabled();
        Toast.makeText(getActivity(), "Pembelian Pulsa \n Berhasil dilakukan", Toast.LENGTH_SHORT).show();
        fragmentPhoneCreditDataMatchesCheckBox.setChecked(false);
    }

    private void showNominal() {
        if (fragpurchaseElectricRegulerRadioButton.isChecked()){
            List<String> listItems = new ArrayList<String>();
            listItems.add("25.000");
            listItems.add("50.000");
            listItems.add("100.000");
            listItems.add("500.000");
            final CharSequence[] itemsNominal = listItems.toArray(new CharSequence[listItems.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setSingleChoiceItems(itemsNominal, whichItemNominal, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    whichItemNominal = which;
                    fragmentPhoneCreditNominalEditText.setText(itemsNominal[which]);
                    dialogInterface.dismiss();
                }
            });
            builder.setTitle(R.string.phoneCreditRegular);
            builder.setCancelable(false);
            builder.setNegativeButton(R.string.cancel, null);
            builder.create().show();
        }
        if (fragpurchaseElectricDataRadioButton.isChecked()){
            List<String> listItems1 = new ArrayList<String>();
            listItems1.add("1.5 GB");
            listItems1.add("2 GB");
            listItems1.add("5 GB");
            listItems1.add("20 GB");
            listItems1.add("50 GB");
            final CharSequence[] itemsNominal1 = listItems1.toArray(new CharSequence[listItems1.size()]);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setSingleChoiceItems(itemsNominal1, whichItemNominal, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    whichItemNominal = which;
                    fragmentPhoneCreditNominalEditText.setText(itemsNominal1[which]);
                    dialogInterface.dismiss();
                }
            });
            builder1.setTitle(R.string.phonecreditData);
            builder1.setCancelable(false);
            builder1.setNegativeButton(R.string.cancel, null);
            builder1.create().show();
        }

    }

    private void showProvider() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.phonecreditProvider));
        builder.setSingleChoiceItems(itemProviders, whichItemProvider, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemProvider = which;
                fragmentPhoneCreditProviderEditText.setText(itemProviders[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.create().show();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            editDataDisabled();
            fragmentPhoneCreditDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragmentPhoneCreditProcessButton.setEnabled(true);
        }
        else {
            editDataEnabled();
            fragmentPhoneCreditDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragmentPhoneCreditProcessButton.setEnabled(false);

        }
    }

    private void editDataEnabled() {
        fragmentPhoneCreditProviderEditText.setEnabled(true);
        fragmentPhoneCreditNominalEditText.setEnabled(true);
        fragmentPhoneNumberEditText.setEnabled(true);
        fragpurchaseElectricRegulerRadioButton.setEnabled(true);
        fragpurchaseElectricDataRadioButton.setEnabled(true);


    }

    private void editDataDisabled() {
        fragmentPhoneCreditProviderEditText.setEnabled(false);
        fragmentPhoneCreditNominalEditText.setEnabled(false);
        fragmentPhoneNumberEditText.setEnabled(false);
        fragpurchaseElectricRegulerRadioButton.setEnabled(false);
        fragpurchaseElectricDataRadioButton.setEnabled(false);
    }
}

