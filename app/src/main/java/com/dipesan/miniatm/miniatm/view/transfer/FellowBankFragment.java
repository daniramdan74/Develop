package com.dipesan.miniatm.miniatm.view.transfer;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.R.id.fragfellowbank_send_button;

/**
 * A simple {@link Fragment} subclass.
 */
public class FellowBankFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "FellowBankFragment";

    //    final CharSequence[] itemsBank = {
//            "002 BANK BRI","002 BANK MANDIRI","009 BANK BNI","011 BANK DANAMON","013 PERMATA BANK"
//            ,"014 BANK BCA","016 BANK BII","019 BANK PANIN","028 BANK NISP","037 BANK ARTHA GRAHA"
//            ,"058 BANK UOB INDONESIA","076 BANK BUMI ARTA","110 BANK JABAR","111 BANK DKI","200 BANK BTN"
//            ,"426 BANK MEGA","441 BANK BUKOPIN","451 BANK SYARIAH MANDIRI"
//    };
    private boolean checkflag;
    final CharSequence[] itemsBank = {
            "BANK BRI", "BANK MANDIRI", "BANK BNI", "BANK DANAMON", "PERMATA BANK"
            , "BANK BCA", "BANK BII", "BANK PANIN", "BANK NISP", "BANK ARTHA GRAHA"
            , "BANK UOB INDONESIA", "BANK BUMI ARTA", "BANK JABAR", "BANK DKI", "BANK BTN"
            , "BANK MEGA", "BANK BUKOPIN", "BANK SYARIAH MANDIRI"
    };


    @BindView(R.id.fragfellow_bank_destination_bank_edit_text) EditText fragfellowBankDestinationBankEditText;
    @BindView(R.id.fragfellow_bank_destination_bank_text_input_layout) TextInputLayout fragfellowBankDestinationBankTextInputLayout;
    @BindView(R.id.fragfellow_bank_destination_account_edit_text) EditText fragfellowBankDestinationAccountEditText;
    @BindView(R.id.fragfellow_bank_destination_account_text_input_layout) TextInputLayout fragfellowBankDestinationAccountTextInputLayout;
    @BindView(R.id.fragfellow_bank_amount_transfer_edit_text) EditText fragfellowBankAmountTransferEditText;
    @BindView(R.id.fragfellow_bank_amount_transfer_text_input_layout) TextInputLayout fragfellowBankAmountTransferTextInputLayout;
    @BindView(R.id.fragfellow_bank_process_button) Button fragfellowBankProcessButton;
    @BindView(R.id.fragfellowbank_destination_bank_text_view) TextView fragfellowbankDestinationBankTextView;
    @BindView(R.id.fragfellowbank_number_account_text_view) TextView fragfellowbankNumberAccountTextView;
    @BindView(R.id.fragfellowbank_name_account_text_view) TextView fragfellowbankNameAccountTextView;
    @BindView(R.id.fragfellowbank_nominal_text_view) TextView fragfellowbankNominalTextView;
    @BindView(R.id.fragfellowbank_from_Bank_text_view) TextView fragfellowbankFromBankTextView;
    @BindView(R.id.fragfellowbank_from_account_number_text_view) TextView fragfellowbankFromAccountNumberTextView;
    @BindView(R.id.fragfellowbank_from_account_name_text_view) TextView fragfellowbankFromAccountNameTextView;
    @BindView(R.id.fragfellowbank_data_matches_check_box) CheckBox fragfellowbankDataMatchesCheckBox;
    @BindView(R.id.fragfellowbank_detail_linear_layout) LinearLayout fragfellowbankDetailLinearLayout;
    @BindView(fragfellowbank_send_button) Button fragfellowbankSendButton;


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

    public FellowBankFragment() {
        // Required empty public constructor
    }

    public static FellowBankFragment newInstance() {
        FellowBankFragment fragment = new FellowBankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fellow_bank, container, false);
        ButterKnife.bind(this, view);
        fragfellowBankDestinationBankEditText.setInputType(InputType.TYPE_NULL);
        fragfellowbankDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragfellowbankDataMatchesCheckBox.setOnCheckedChangeListener(this);
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        return view;
    }

    @OnClick({R.id.fragfellow_bank_destination_bank_edit_text, R.id.fragfellow_bank_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragfellow_bank_destination_bank_edit_text:
                showDestinationBank();
                break;
            case R.id.fragfellow_bank_process_button:
                hideKeyboard(view);
                showDetail();
                break;
        }
    }

    private void showDetail() {
        if (fragfellowBankDestinationBankEditText.getText().toString().isEmpty()){
            fragfellowBankDestinationBankTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragfellowBankDestinationBankTextInputLayout.setErrorEnabled(false);
        }
        if (fragfellowBankDestinationAccountEditText.getText().toString().isEmpty()) {
        fragfellowBankDestinationAccountTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragfellowBankDestinationAccountTextInputLayout.setErrorEnabled(false);

        }
        if (fragfellowBankAmountTransferEditText.getText().toString().isEmpty()){
            fragfellowBankAmountTransferTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragfellowBankAmountTransferTextInputLayout.setErrorEnabled(false);
        }

        if (fragfellowBankDestinationBankEditText.getText().toString().length()>0&&
        fragfellowBankDestinationAccountEditText.getText().toString().length()>0&&
                fragfellowBankAmountTransferEditText.getText().toString().length()>0
        ) {
            fragfellowbankDetailLinearLayout.setVisibility(View.VISIBLE);
            fragfellowbankDestinationBankTextView.setText(fragfellowBankDestinationBankEditText.getText().toString());
            fragfellowbankNumberAccountTextView.setText(fragfellowBankDestinationAccountEditText.getText().toString());
            fragfellowbankNominalTextView.setText(fragfellowBankAmountTransferEditText.getText().toString());
            fragfellowbankSendButton.setEnabled(false);
            fragfellowbankFromBankTextView.setText("BANK MANDIRI");
            fragfellowbankFromAccountNumberTextView.setText("1234-567-890");
            fragfellowbankFromAccountNameTextView.setText("Dani Ramdan");
        }
    }

    private void editDataEnabled() {
        fragfellowBankDestinationBankEditText.setEnabled(true);
        fragfellowBankDestinationAccountEditText.setEnabled(true);
        fragfellowBankAmountTransferEditText.setEnabled(true);
        fragfellowBankProcessButton.setEnabled(true);

    }

    private void editDataDisabled() {
        fragfellowBankDestinationBankEditText.setEnabled(false);
        fragfellowBankDestinationAccountEditText.setEnabled(false);
        fragfellowBankAmountTransferEditText.setEnabled(false);
    }

    private void showDestinationBank() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Daftar Bank");
        builder.setItems(itemsBank, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                fragfellowBankDestinationBankEditText.setText(itemsBank[item]);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        AlertDialog alert = builder.create();
        alert.show();


    }

    @OnClick(fragfellowbank_send_button)
    public void onClickSend() {
        print();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            fragfellowbankDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragfellowBankProcessButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            fragfellowBankProcessButton.setEnabled(false);
            fragfellowbankSendButton.setEnabled(true);
            fragfellowbankSendButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragfellowbankSendButton.setTextColor(getResources().getColor(R.color.colorTextIcons));

            editDataDisabled();
        }
        else {
            fragfellowBankProcessButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragfellowbankDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragfellowBankProcessButton.setEnabled(true);
            fragfellowbankSendButton.setEnabled(false);
            fragfellowbankSendButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragfellowbankSendButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            fragfellowbankDetailLinearLayout.setVisibility(View.INVISIBLE);
            editDataEnabled();
        }

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void print() {
        com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nTransfer Antar Bank");
                printer.printText("\n===============================");
                printer.printText("\n Tujuan Transfer");
                printer.printText("\nBank Tujuan  : "+fragfellowbankDestinationBankTextView.getText().toString());
                printer.printText("\nNo Rekening  : "+fragfellowBankDestinationAccountEditText.getText().toString());
                printer.printText("\nNama Pemilik : "+"Maulana");
                printer.printText("\n     Nominal : "+fragfellowBankAmountTransferEditText.getText().toString());
                printer.printText("\n");
                printer.printText("\n--------------------------");
                printer.printText("\n Dari");
                printer.printText("\n       Bank  : "+fragfellowbankFromBankTextView.getText().toString());
                printer.printText("\nNo Rekening  : "+fragfellowbankFromAccountNumberTextView.getText().toString());
                printer.printText("\nNama Pemilik : "+fragfellowbankFromAccountNameTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n"+ AppConstant.NAME_MERCHANT);
                printer.printText("\nID "+ AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
        fragfellowbankDetailLinearLayout.setVisibility(View.INVISIBLE);
        editDataEnabled();
        fragfellowBankDestinationBankEditText.setText(null);
        fragfellowBankDestinationAccountEditText.setText(null);
        fragfellowBankAmountTransferEditText.setText(null);
        Toast.makeText(getActivity(), "Transfer Berhasil", Toast.LENGTH_SHORT).show();
        fragfellowbankDataMatchesCheckBox.setChecked(false);
    }

}

