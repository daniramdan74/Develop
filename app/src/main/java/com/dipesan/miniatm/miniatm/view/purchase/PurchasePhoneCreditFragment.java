package com.dipesan.miniatm.miniatm.view.purchase;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.SQLiteDatabase.DatabaseHelper;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasePhoneCreditFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private YoucubeService youcubeService;
    private static final String TAG = "PhoneCredit";
    private final CharSequence[] itemProviders = {"Telkomsel", "Indosat Ooredo0", "XL", "Three", "Smartfren"};
    private final CharSequence[] itemsNominal = {"25.000", "50.000", "100.000","500.000"};
    private final int[] itemPrices = {30000, 50000, 100000, 150000, 200000, 300000, 500000};
    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
    @BindView(R.id.fragment_phone_credit_provider_edit_text) EditText fragmentPhoneCreditProviderEditText;
    @BindView(R.id.fragment_phone_credit_process_button) Button fragmentPhoneCreditProcessButton;
    @BindView(R.id.fragment_phone_credit_nominal_edit_text) EditText fragmentPhoneCreditNominalEditText;
    @BindView(R.id.fragment_phone_number_edit_text) EditText fragmentPhoneNumberEditText;
    @BindView(R.id.fragphone_text_input_layout_credit_provider) TextInputLayout fragphoneTextInputLayoutCreditProvider;
    @BindView(R.id.fragphone_text_input_layout_nominal) TextInputLayout fragphoneTextInputLayoutNominal;
    @BindView(R.id.fragphone_text_input_layout_phone_number) TextInputLayout fragphoneTextInputLayoutPhoneNumber;
    private TextView ProviderTextView, NominalTextView, NumberTextView;
    private CheckBox ConfirmCheckbox;
    private Button cancelButton, confirmButton;
    private boolean checkflag;
    private int whichItemProvider = 0;
    private int whichItemNominal = 0;
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

        return view;
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
            fragphoneTextInputLayoutCreditProvider.setError("Provider Masih Kosong");
            return;
        }
        else {
            fragphoneTextInputLayoutCreditProvider.setErrorEnabled(false);
        }
        if (fragmentPhoneCreditNominalEditText.getText().toString().isEmpty()) {
            fragphoneTextInputLayoutNominal.setError("Nominal Masih Kosong");
            return;
        }
        else {
            fragphoneTextInputLayoutNominal.setErrorEnabled(false);
        }
        if (fragmentPhoneNumberEditText.getText().toString().isEmpty()) {
            fragphoneTextInputLayoutPhoneNumber.setError("No Telepon Kosong");
            return;
        }
        else if (fragmentPhoneNumberEditText.getText().toString().length() < 9) {
            fragphoneTextInputLayoutPhoneNumber.setError("No Telepon Anda Kurang");
        }
        else {
            fragphoneTextInputLayoutPhoneNumber.setErrorEnabled(false);
        }
        if (fragmentPhoneCreditProviderEditText.getText().toString().length() > 0 &&
                fragmentPhoneCreditNominalEditText.getText().toString().length() > 0 &&
                fragmentPhoneNumberEditText.getText().toString().length() >= 9) {

//
//
//            String message = String.format("Pembelian pulsa %s ke %s sebesar %s telah berhasil",
//                    itemProviders[whichItemProvider],
//                    fragmentPhoneNumberEditText.getText().toString(),
//                    itemsNominal[whichItemNominal]);
//            showDialog(getActivity(), message);


            showAlert2();
        }
    }

    private void showAlert2() {
        boolean checkflag;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_custom_dialog_phone_credit, null);
        builder.setView(dialogView);
        builder.setTitle("Pembelian Pulsa");
        ProviderTextView = ButterKnife.findById(dialogView, R.id.detail_data_provider_text_view);
        NominalTextView = ButterKnife.findById(dialogView, R.id.detail_data_nominal_text_view);
        NumberTextView = ButterKnife.findById(dialogView, R.id.detail_data_number_text_view);
        ConfirmCheckbox = ButterKnife.findById(dialogView, R.id.confirm_checkbox);
        cancelButton = ButterKnife.findById(dialogView, R.id.cancel_button);
        confirmButton = ButterKnife.findById(dialogView, R.id.confirm_button);
        confirmButton.setTextColor(getResources().getColor(R.color.colorDivide));
        confirmButton.setEnabled(false);
        ConfirmCheckbox = ButterKnife.findById(dialogView, R.id.confirm_checkbox);
        ConfirmCheckbox.setOnCheckedChangeListener(this);
        ProviderTextView.setText(fragmentPhoneCreditProviderEditText.getText().toString());
        NominalTextView.setText(fragmentPhoneCreditNominalEditText.getText().toString());
        NumberTextView.setText(fragmentPhoneNumberEditText.getText().toString());


        final AlertDialog alertDialog = builder.create();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youcubeService.setIsMessage(true);
                youcubeService.setMessage("Make Payment");
                youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
                    @Override
                    public void onApproved() {
                        printPhoneCredit();
                        Toast.makeText(getActivity(), "Success",Toast.LENGTH_SHORT).show();
                    }
                });
               }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private void printPhoneCredit() {
        com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nPembelian Pulsa");
                printer.printText("\n===============================");
                printer.printText("\nProvider : \n" + fragmentPhoneCreditProviderEditText.getText().toString());
                printer.printText("\nNominal : \n" + fragmentPhoneCreditNominalEditText.getText().toString());
                printer.printText("\nNomor Telpon : \n" + fragmentPhoneNumberEditText.getText().toString());
                printer.printText("\nMerchant :");
                printer.printText("\nPT. Qreatif Unggul Berniaga");
                printer.printText("\n== TERIMAKASIH ==");
                printer.lineWrap(4);
                printer.commitTransaction();
                fragmentPhoneCreditProviderEditText.setText(null);
                fragmentPhoneCreditNominalEditText.setText(null);
                fragmentPhoneNumberEditText.setText(null);
            }
        });
    }

    private void addData() {
        HashMap<String, String> queryValues = new HashMap<String, String>();
        queryValues.put("providerName", fragmentPhoneCreditProviderEditText.getText().toString());
        queryValues.put("nominal", fragmentPhoneCreditProcessButton.getText().toString());
        queryValues.put("telephoneNumber", fragmentPhoneNumberEditText.getText().toString());
        databaseHelper.addPhoneCredit(queryValues);
    }

    private void showNominal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                //               .setTitle("Pilih Provider")
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Nominal Pulsa</font>"));
        builder.setSingleChoiceItems(itemsNominal, whichItemNominal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemNominal = which;
                fragmentPhoneCreditNominalEditText.setText(itemsNominal[which]);
                dialogInterface.dismiss();
            }
        });

        builder.create().show();

    }

    private void showProvider() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                //               .setTitle("Pilih Provider")
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Pilih Provider</font>"));
        builder.setSingleChoiceItems(itemProviders, whichItemProvider, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemProvider = which;
                fragmentPhoneCreditProviderEditText.setText(itemProviders[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            ConfirmCheckbox.setTextColor(getResources().getColor(R.color.colorPrimary));
            confirmButton.setEnabled(true);
            confirmButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            ConfirmCheckbox.setTextColor(getResources().getColor(R.color.colorDivide));
            confirmButton.setEnabled(false);
            confirmButton.setTextColor(getResources().getColor(R.color.colorDivide));
        }
    }
}
