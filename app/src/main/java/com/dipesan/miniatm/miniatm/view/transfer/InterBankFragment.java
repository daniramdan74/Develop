package com.dipesan.miniatm.miniatm.view.transfer;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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

import com.dipesan.miniatm.miniatm.Activity.LoginActivity;
import com.dipesan.miniatm.miniatm.Activity.MainActivity;
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
public class InterBankFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.fraginter_bank_destination_account_text_input_layout) TextInputLayout fraginterBankDestinationAccountTextInputLayout;
    @BindView(R.id.fraginter_bank_amount_transfer_text_input_layout) TextInputLayout fraginterBankAmountTransferTextInputLayout;
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
        youcubeService = new YoucubeService(getActivity());

//        fraginterBankAmountTransferEditText.addTextChangedListener(new MoneyTextWatcher(fraginterBankAmountTransferEditText));

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
                youcubeService.setAmount(Double.parseDouble(fraginterBankAmountTransferEditText.getText().toString()));
                youcubeService.setIsMessage(true);
                youcubeService.setMessage(getString(R.string.insertCard));
                youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
                    @Override
                    public void onApproved() {
                        print();
                        Toast.makeText(getActivity(), "Print", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
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
                printer.printText("\n"+getString(R.string.interbank));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.to));
                printer.printText("\n"+getString(R.string.interbankDestinationBank) + fraginterbankDestinationBankTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAccountNumber)+ fraginterbankNumberAccountTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAccountName) + fraginterbankNameAccountTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAmount)+ fraginterbankNominalTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\n--------------------------");
                printer.printText("\n"+getString(R.string.from));
                printer.printText("\n"+getString(R.string.interbankBank)+ fraginterbankFromBankTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAccountNumber)+ fraginterbankFromAccountNumberTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAccountName)+ fraginterbankFromAccountNameTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
        fraginterbankDetailLinearLayout.setVisibility(View.INVISIBLE);
        editDataEnabled();
        fraginterBankDestinationAccountEditText.setText(null);
        fraginterBankAmountTransferEditText.setText(null);
        Toast.makeText(getActivity(), ""+getString(R.string.transfersuccess), Toast.LENGTH_SHORT).show();
        fraginterbankDataMatchesCheckBox.setChecked(false);
        showAlert();
    }

    private void showDetail() {
        fraginterbankDataMatchesCheckBox.setChecked(true);
        if (fraginterBankDestinationAccountEditText.getText().toString().isEmpty()) {
            fraginterBankDestinationAccountTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fraginterBankDestinationAccountTextInputLayout.setErrorEnabled(false);
        }
        if (fraginterBankAmountTransferEditText.getText().toString().isEmpty()){
            fraginterBankAmountTransferTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fraginterBankAmountTransferTextInputLayout.setErrorEnabled(false);
        }

        if (fraginterBankDestinationAccountEditText.getText().toString().length()>0&&
                fraginterBankAmountTransferEditText.getText().toString().length()>0){

            fraginterbankDataMatchesCheckBox.setOnCheckedChangeListener(this);
            fraginterbankDetailLinearLayout.setVisibility(View.VISIBLE);
            fraginterbankDestinationBankTextView.setText("Bank Mandiri");
            fraginterbankNumberAccountTextView.setText(fraginterBankDestinationAccountEditText.getText().toString());
            fraginterbankNameAccountTextView.setText("Maulana");
            fraginterbankNominalTextView.setText(fraginterBankAmountTransferEditText.getText().toString());
            fraginterbankFromBankTextView.setText("Bank Mandiri");
            fraginterbankFromAccountNumberTextView.setText("1234-567-890");
            fraginterbankFromAccountNameTextView.setText("Dani Ramdan");
            editDataDisabled();
        }else {
            editDataEnabled();
        }
    }

    private void editDataEnabled() {
        fraginterBankDestinationAccountEditText.setEnabled(true);
        fraginterBankAmountTransferEditText.setEnabled(true);
        fraginterBankProcessButton.setEnabled(true);
    }

    private void editDataDisabled() {
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
    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.continuetransaction))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().overridePendingTransition(0, R.anim.fade_out);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().overridePendingTransition(0, R.anim.fade_out);
                        getActivity().finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
