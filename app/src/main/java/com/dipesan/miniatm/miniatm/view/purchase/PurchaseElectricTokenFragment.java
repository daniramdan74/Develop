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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseElectricTokenFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private boolean checkflag;
    private static final String TAG = "electricTokenFragment";
    private final CharSequence[] itemsNominal = {"25.000", "50.000", "100.000", "500.000"};
    @BindView(R.id.fragment_electric_nominal_edit_text) EditText fragmentElectricNominalEditText;
    @BindView(R.id.fragelectric_text_input_layout_nominal) TextInputLayout fragelectricTextInputLayoutNominal;
    @BindView(R.id.fragment_electric_meter_number_edit_text) EditText fragmentElectricMeterNumberEditText;
    @BindView(R.id.fragpelectric_text_input_layout_meter_number) TextInputLayout fragpelectricTextInputLayoutMeterNumber;
    @BindView(R.id.fragment_electric_process_button) Button fragmentElectricProcessButton;
    @BindView(R.id.fragpurchase_electric_id_customer_text_view) TextView fragpurchaseElectricIdCustomerTextView;
    @BindView(R.id.fragpurchase_electric_name_customer_text_view) TextView fragpurchaseElectricNameCustomerTextView;
    @BindView(R.id.fragpurchase_electric_fare_text_view) TextView fragpurchaseElectricFareTextView;
    @BindView(R.id.fragpurchase_electric_nominal_text_view) TextView fragpurchaseElectricNominalTextView;
    @BindView(R.id.fragpurchase_electric_data_matches_checkbox) CheckBox fragpurchaseElectricDataMatchesCheckbox;
    @BindView(R.id.fragpurchase_electric_send_button) Button fragpurchaseElectricSendButton;
    @BindView(R.id.fragpurchase_electric_detail_data_linear_layout) LinearLayout fragpurchaseElectricDetailDataLinearLayout;
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


    public PurchaseElectricTokenFragment() {
        // Required empty public constructor
    }

    public static PurchaseElectricTokenFragment newIstance() {
        return new PurchaseElectricTokenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_electric_token, container, false);
        ButterKnife.bind(this, view);

        fragmentElectricNominalEditText.setInputType(InputType.TYPE_NULL);
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        fragpurchaseElectricDetailDataLinearLayout.setVisibility(View.INVISIBLE);
        fragpurchaseElectricDataMatchesCheckbox.setOnCheckedChangeListener(this);
        youcubeService = new YoucubeService(getActivity());

        return view;
    }

    private void showNominal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.phonecreditAmount));
        builder.setSingleChoiceItems(itemsNominal, whichItemNominal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemNominal = which;
                fragmentElectricNominalEditText.setText(itemsNominal[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    @OnClick({R.id.fragment_electric_nominal_edit_text, R.id.fragment_electric_meter_number_edit_text, R.id.fragment_electric_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_electric_nominal_edit_text:
                hideKeyboard(view);
                showNominal();
                break;
            case R.id.fragment_electric_meter_number_edit_text:
                break;
            case R.id.fragment_electric_process_button:
                showDetail();
                hideKeyboard(view);
                break;
        }
    }

    private void showDetail() {
        fragpurchaseElectricDataMatchesCheckbox.setChecked(true);
        hideKeyboard(getView());
        if (fragmentElectricNominalEditText.getText().toString().isEmpty()) {
            fragelectricTextInputLayoutNominal.setError("Tidak Boleh Kosong");
        }
        else {
            fragelectricTextInputLayoutNominal.setErrorEnabled(false);
        }
        if (fragmentElectricMeterNumberEditText.getText().toString().isEmpty()) {
            fragpelectricTextInputLayoutMeterNumber.setError("Tidak Boleh Kosong");
        }
        else {
            fragpelectricTextInputLayoutMeterNumber.setErrorEnabled(false);
        }
        if (fragmentElectricNominalEditText.getText().toString().length() > 0 &&
                fragmentElectricMeterNumberEditText.getText().toString().length() > 0) {
            fragpurchaseElectricDetailDataLinearLayout.setVisibility(View.VISIBLE);
            fragpurchaseElectricIdCustomerTextView.setText(fragmentElectricMeterNumberEditText.getText().toString());
            fragpurchaseElectricNominalTextView.setText(fragmentElectricNominalEditText.getText().toString());
            fragpurchaseElectricNameCustomerTextView.setText("Randy Riawan");
            fragpurchaseElectricFareTextView.setText("R1/900");
            editDataDisabled();
        }else {
            editDataEnabled();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick(R.id.fragpurchase_electric_send_button)
    public void onClickSend() {
        youcubeService.setIsMessage(true);
        youcubeService.setMessage(getString(R.string.insertCard));
        youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
            @Override
            public void onApproved() {
                Print();
            }
        });
    }

    private void Print() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nPembelian Token Listrik");
                printer.printText("\n===============================");
                printer.printText("\n");
                printer.printText("\nID PEL     : " + fragpurchaseElectricIdCustomerTextView.getText().toString());
                printer.printText("\nNAMA       : " + fragpurchaseElectricNameCustomerTextView.getText().toString());
                printer.printText("\nTARIF/DAYA : " + fragpurchaseElectricFareTextView.getText().toString());
                printer.printText("\nRP BAYAR   : " + fragpurchaseElectricNominalTextView.getText().toString());
                printer.printText("\nADMIN BANK : 1.600");
                printer.printText("\nPPn        : 0");
                printer.printText("\nPPj        : 707");
                printer.printText("\nJML KWH    : 50,98");
                printer.setFontSize(30);
                printer.printText("\nTOKEN :");
                printer.printText("\n1234-5678-9012-3456-7890");
                printer.setFontSize(24);
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpurchaseElectricDetailDataLinearLayout.setVisibility(View.INVISIBLE);
        fragmentElectricMeterNumberEditText.setText(null);
        fragmentElectricNominalEditText.setText(null);
        fragmentElectricProcessButton.setEnabled(true);
        Toast.makeText(getActivity(), "Pembelian Token Listrik\n Berhasil dilakukan", Toast.LENGTH_SHORT).show();
        fragpurchaseElectricDataMatchesCheckbox.setChecked(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            fragpurchaseElectricDataMatchesCheckbox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragmentElectricProcessButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            fragmentElectricProcessButton.setEnabled(false);
            fragpurchaseElectricSendButton.setEnabled(true);
            fragpurchaseElectricSendButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpurchaseElectricSendButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            editDataDisabled();
        }
        else {
            fragmentElectricProcessButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpurchaseElectricDataMatchesCheckbox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragmentElectricProcessButton.setEnabled(true);
            fragpurchaseElectricSendButton.setEnabled(false);
            fragpurchaseElectricSendButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpurchaseElectricSendButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            fragpurchaseElectricDetailDataLinearLayout.setVisibility(View.INVISIBLE);
            editDataEnabled();
        }
    }

    private void editDataEnabled() {
        fragmentElectricMeterNumberEditText.setEnabled(true);
        fragmentElectricNominalEditText.setEnabled(true);
        fragmentElectricProcessButton.setEnabled(true);
    }

    private void editDataDisabled() {
        fragmentElectricMeterNumberEditText.setEnabled(false);
        fragmentElectricNominalEditText.setEnabled(false);
        fragmentElectricProcessButton.setEnabled(false);
    }

}
