package com.dipesan.miniatm.miniatm.view.transfer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class InterBankFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private boolean checkflag;
    private static final String TAG = "InterBankFragment";

    @BindView(R.id.fraginter_bank_destination_account_edit_text) EditText fraginterBankDestinationAccountEditText;
    @BindView(R.id.fraginter_bank_amount_transfer_edit_text) EditText fraginterBankAmountTransferEditText;
    @BindView(R.id.fraginter_bank_process_button) Button fraginterBankProcessButton;
    @BindView(R.id.fraginterbank_destination_bank_text_view) TextView fraginterbankDestinationBankTextView;
    @BindView(R.id.fraginterbank_number_account_text_view) TextView fraginterbankNumberAccountTextView;
    @BindView(R.id.fraginterbank_name_account_text_view) TextView fraginterbankNameAccountTextView;
    @BindView(R.id.fraginterbank_nominal_text_view) TextView fraginterbankNominalTextView;
    @BindView(R.id.fraginterbank_from_Bank_text_view) TextView fraginterbankFromBankTextView;
    @BindView(R.id.fraginterbank_from_account_number_text_view) TextView fraginterbankFromAccountNumberTextView;
    @BindView(R.id.fraginterbank_from_account_name_text_view) TextView fraginterbankFromAccountNameTextView;
    @BindView(R.id.fraginterbank_data_matches_check_box) CheckBox fraginterbankDataMatchesCheckBox;
    @BindView(R.id.fraginterbank_send_button) Button fraginterbankSendButton;
    @BindView(R.id.fraginterbank_detail_linear_layout) LinearLayout fraginterbankDetailLinearLayout;

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

    public InterBankFragment() {
        // Required empty public constructor
    }

    public static InterBankFragment newInstance() {
        InterBankFragment fragment = new InterBankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inter_bank, container, false);
        ButterKnife.bind(this, view);
        fraginterbankDetailLinearLayout.setVisibility(View.INVISIBLE);
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        return view;
    }

    @OnClick({R.id.fraginter_bank_process_button, R.id.fraginterbank_send_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fraginter_bank_process_button:
                hideKeyboard(getView());
                showDetail();
                break;
            case R.id.fraginterbank_send_button:
                print();
                break;
        }
    }

    private void print() {
        com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nTransfer Sesama Bank");
                printer.printText("\n===============================");
                printer.printText("\n Tujuan Transfer");
                printer.printText("\nBank Tujuan  : "+fraginterbankDestinationBankTextView.getText().toString());
                printer.printText("\nNo Rekening  : "+fraginterbankNumberAccountTextView.getText().toString());
                printer.printText("\nNama Pemilik : "+fraginterbankNameAccountTextView.getText().toString());
                printer.printText("\n     Nominal : "+fraginterbankNominalTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\n--------------------------");
                printer.printText("\n Dari");
                printer.printText("\n       Bank  : "+fraginterbankFromBankTextView.getText().toString());
                printer.printText("\nNo Rekening  : "+fraginterbankFromAccountNumberTextView.getText().toString());
                printer.printText("\nNama Pemilik : "+fraginterbankFromAccountNameTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n"+ AppConstant.NAME_MERCHANT);
                printer.printText("\nID "+ AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
        fraginterbankDetailLinearLayout.setVisibility(View.INVISIBLE);
        editDataEnabled();
        fraginterBankDestinationAccountEditText.setText(null);
        fraginterBankAmountTransferEditText.setText(null);
        Toast.makeText(getActivity(), "Transfer Berhasil", Toast.LENGTH_SHORT).show();
        fraginterbankDataMatchesCheckBox.setChecked(false);
    }

    private void showDetail() {
        fraginterbankDataMatchesCheckBox.setOnCheckedChangeListener(this);
        fraginterbankDetailLinearLayout.setVisibility(View.VISIBLE);
        fraginterbankDestinationBankTextView.setText("Bank Mandiri");
                fraginterbankNumberAccountTextView.setText(fraginterBankDestinationAccountEditText.getText().toString());
        fraginterbankNameAccountTextView.setText("Maulana");
                fraginterbankNominalTextView.setText(fraginterBankAmountTransferEditText.getText().toString());
        fraginterbankFromBankTextView.setText("Bank Mandiri");
                fraginterbankFromAccountNumberTextView.setText("1234-567-890");
        fraginterbankFromAccountNameTextView.setText("Dani Ramdan");
        fraginterbankSendButton.setEnabled(false);
    }
    private void editDataEnabled(){
        fraginterBankDestinationAccountEditText.setEnabled(true);
        fraginterBankAmountTransferEditText.setEnabled(true);
        fraginterBankProcessButton.setEnabled(true);
    }
    private void editDataDisabled(){
        fraginterBankDestinationAccountEditText.setEnabled(false);
        fraginterBankAmountTransferEditText.setEnabled(false);
        fraginterBankProcessButton.setEnabled(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (checkflag = isChecked) {
                fraginterbankDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
                fraginterBankProcessButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
                fraginterBankProcessButton.setEnabled(false);
                fraginterbankSendButton.setEnabled(true);
                fraginterbankSendButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
                fraginterbankSendButton.setTextColor(getResources().getColor(R.color.colorTextIcons));

                editDataDisabled();
            }
            else {
                fraginterBankProcessButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
                fraginterbankDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
                fraginterBankProcessButton.setEnabled(true);
                fraginterbankSendButton.setEnabled(false);
                fraginterbankSendButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                fraginterbankSendButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
                fraginterbankDetailLinearLayout.setVisibility(View.INVISIBLE);
                editDataEnabled();
            }


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
