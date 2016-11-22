package com.dipesan.miniatm.miniatm.view.payment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dipesan.miniatm.miniatm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.R.id.fragpayment_credit_card_providers_edit_text;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentCreditCardFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    final CharSequence[] itemsBank = {"BNI", "ANZ Indonesia", "BRI",
            "Bukopin", "Citibank", "CIMB NIAGA", "Danamon", "HSBC",
            "Bank Mega", "Bank Permata", "Standard Chartered Bank", "Panin Bank"};
    @BindView(R.id.fragpayment_credit_card_customer_number_edit_text) EditText fragpaymentCreditCardCustomerNumberEditText;
    @BindView(R.id.fragpayment_credit_card_process_button) Button fragpaymentCreditCardProcessButton;
    @BindView(R.id.fragpayment_credit_card_bank_text_view) TextView fragpaymentCreditCardBankTextView;
    @BindView(R.id.fragpayment_card_number_text_view) TextView fragpaymentCardNumberTextView;
    @BindView(R.id.fragpayment_type_card_bank_text_view) TextView fragpaymentTypeCardBankTextView;
    @BindView(R.id.fragpayment_credit_account_name_text_view) TextView fragpaymentCreditAccountNameTextView;
    @BindView(R.id.fragpayment_amount_transfer_text_view) TextView fragpaymentAmountTransferTextView;
    @BindView(R.id.fragpayment_credit_from_bank_text_view) TextView fragpaymentCreditFromBankTextView;
    @BindView(R.id.fragpayment_credit_from_account_number_text_view) TextView fragpaymentCreditFromAccountNumberTextView;
    @BindView(R.id.fragpayment_credit_from_account_name_text_view) TextView fragpaymentCreditFromAccountNameTextView;
    @BindView(R.id.fragpayment_credit_data_matches_check_box) CheckBox fragpaymentCreditDataMatchesCheckBox;
    @BindView(R.id.fragpayment_credit_send_button) Button fragpaymentCreditSendButton;
    @BindView(R.id.fragpayment_credit_card_detail_linear_layout) LinearLayout fragpaymentCreditCardDetailLinearLayout;
    @BindView(R.id.fragpayment_credit_card_providers_text_input_layout) TextInputLayout fragpaymentCreditCardProvidersTextInputLayout;
    @BindView(R.id.fragpayment_credit_card_customer_number_text_input_layout) TextInputLayout fragpaymentCreditCardCustomerNumberTextInputLayout;
    private int whichItemBanks = 0;
    @BindView(fragpayment_credit_card_providers_edit_text) EditText fragpaymentCreditCardProvidersEditText;
    private boolean checkflag;

    public PaymentCreditCardFragment() {
        // Required empty public constructor
    }

    public static PaymentCreditCardFragment newIstance() {
        return new PaymentCreditCardFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_credit_card, container, false);
        ButterKnife.bind(this, view);
        fragpaymentCreditCardProvidersEditText.setInputType(InputType.TYPE_NULL);
        fragpaymentCreditCardDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentCreditDataMatchesCheckBox.setOnCheckedChangeListener(this);
        return view;
    }

    @OnClick({fragpayment_credit_card_providers_edit_text, R.id.fragpayment_credit_card_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case fragpayment_credit_card_providers_edit_text:
                showProviders();
                break;
            case R.id.fragpayment_credit_card_process_button:
                showDetails();
                break;
        }
    }

    private void showDetails() {
        if (fragpaymentCreditCardProvidersEditText.getText().toString().isEmpty()) {
            fragpaymentCreditCardProvidersTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragpaymentCreditCardProvidersTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentCreditCardCustomerNumberEditText.getText().toString().isEmpty()) {
            fragpaymentCreditCardCustomerNumberTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragpaymentCreditCardCustomerNumberTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentCreditCardProvidersEditText.getText().toString().length()>0
                && fragpaymentCreditCardCustomerNumberEditText.getText().toString().length()>0
                ){
            fragpaymentCreditSendButton.setEnabled(false);
            fragpaymentCreditCardDetailLinearLayout.setVisibility(View.VISIBLE);
            fragpaymentCreditCardBankTextView.setText(fragpaymentCreditCardProvidersEditText.getText().toString());
            fragpaymentCardNumberTextView.setText(fragpaymentCreditCardCustomerNumberEditText.getText().toString());
            fragpaymentTypeCardBankTextView.setText("MASTER CARD");
            fragpaymentCreditAccountNameTextView.setText("Dani Ramdan");
            fragpaymentAmountTransferTextView.setText("1,000,000");
            fragpaymentCreditFromBankTextView.setText("Mandiri");
            fragpaymentCreditFromAccountNumberTextView.setText("1234-5678-90");
            fragpaymentCreditFromAccountNameTextView.setText("Maulana Yusuf");
            fragpaymentCreditDataMatchesCheckBox.setChecked(true);
            disabledData();
        }
    }

    private void enabledData() {
        fragpaymentCreditCardProvidersEditText.setEnabled(true);
        fragpaymentCreditCardCustomerNumberEditText.setEnabled(true);
        fragpaymentCreditCardProcessButton.setEnabled(true);
    }

    private void disabledData() {
        fragpaymentCreditCardProvidersEditText.setEnabled(false);
        fragpaymentCreditCardCustomerNumberEditText.setEnabled(false);
        fragpaymentCreditCardProcessButton.setEnabled(false);
    }


    private void showProviders() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.fellowbankList));
        builder.setSingleChoiceItems(itemsBank, whichItemBanks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                whichItemBanks = items;
                fragpaymentCreditCardProvidersEditText.setText(itemsBank[items]);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @OnClick(R.id.fragpayment_credit_send_button)
    public void onClick() {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            fragpaymentCreditDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentCreditCardProcessButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            fragpaymentCreditSendButton.setEnabled(true);
            fragpaymentCreditSendButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentCreditSendButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentCreditCardProcessButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentCreditDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentCreditSendButton.setEnabled(false);
            fragpaymentCreditSendButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentCreditSendButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            fragpaymentCreditCardDetailLinearLayout.setVisibility(View.INVISIBLE);
            enabledData();
        }
    }
}
