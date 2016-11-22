package com.dipesan.miniatm.miniatm.view.payment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentPostPaidPhoneFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private final CharSequence[] itemProviders = {"Telkomsel", "Indosat", "XL/Axis", "3 (TRI)", "Flexi", "Esia", "Smartfren"};
    private int whichItemProviders = 0;
    @BindView(R.id.fragpayment_post_paid_phone_number_edit_text) EditText fragpaymentPostPaidPhoneNumberEditText;
    @BindView(R.id.fragpayment_post_paid_phone_process_button) Button fragpaymentPostPaidPhoneProcessButton;
    @BindView(R.id.fragpayment_post_paid_phone_provider_text_view) TextView fragpaymentPostPaidPhoneProviderTextView;
    @BindView(R.id.fragpayment_post_paid_phone_number_text_view) TextView fragpaymentPostPaidPhoneNumberTextView;
    @BindView(R.id.fragpayment_post_paid_phone_customer_name_text_view) TextView fragpaymentPostPaidPhoneCustomerNameTextView;
    @BindView(R.id.fragpayment_post_paid_phone_amount_text_view) TextView fragpaymentPostPaidPhoneAmountTextView;
    @BindView(R.id.fragpayment_post_paid_phone_data_matches_check_box) CheckBox fragpaymentPostPaidPhoneDataMatchesCheckBox;
    @BindView(R.id.fragpayment_post_paid_phone_pay_button) Button fragpaymentPostPaidPhonePayButton;
    @BindView(R.id.fragpayment_post_paid_phone_detail_linear_layout) LinearLayout fragpaymentPostPaidPhoneDetailLinearLayout;

    @BindView(R.id.fragpayment_post_paid_phone_provider_eidt_text) EditText fragpaymentPostPaidPhoneProviderEidtText;
    private boolean checkflag;

    public PaymentPostPaidPhoneFragment() {
        // Required empty public constructor
    }

    public static PaymentPostPaidPhoneFragment newIstance() {
        return new PaymentPostPaidPhoneFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_post_paid_phone, container, false);
        ButterKnife.bind(this, view);
        fragpaymentPostPaidPhoneProviderEidtText.setInputType(InputType.TYPE_NULL);
        fragpaymentPostPaidPhoneDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentPostPaidPhoneDataMatchesCheckBox.setOnCheckedChangeListener(this);
        return view;
    }

    @OnClick({R.id.fragpayment_post_paid_phone_provider_eidt_text, R.id.fragpayment_post_paid_phone_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_post_paid_phone_provider_eidt_text:
                showProviders();
                break;
            case R.id.fragpayment_post_paid_phone_process_button:
                showDetails();
                break;
        }
    }

    private void showDetails() {
        fragpaymentPostPaidPhoneDetailLinearLayout.setVisibility(View.VISIBLE);
        fragpaymentPostPaidPhoneProviderTextView.setText(fragpaymentPostPaidPhoneProviderEidtText.getText().toString());
        fragpaymentPostPaidPhoneNumberTextView.setText(fragpaymentPostPaidPhoneNumberEditText.getText().toString());
        fragpaymentPostPaidPhoneCustomerNameTextView.setText("Dani Ramdan");
        fragpaymentPostPaidPhoneAmountTextView.setText("850,000");
        fragpaymentPostPaidPhoneDataMatchesCheckBox.setChecked(true);
        disabledData();
    }

    private void showProviders() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.postpaidphoneProvider));
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.setSingleChoiceItems(itemProviders, whichItemProviders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                whichItemProviders = items;
                fragpaymentPostPaidPhoneProviderEidtText.setText(itemProviders[items]);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @OnClick(R.id.fragpayment_post_paid_phone_pay_button)
    public void onClickPay() {

    }
    private void endabledData(){
        fragpaymentPostPaidPhoneNumberEditText.setEnabled(true);
        fragpaymentPostPaidPhoneProviderEidtText.setEnabled(true);
        fragpaymentPostPaidPhoneProcessButton.setEnabled(true);

    }
    private void disabledData(){
        fragpaymentPostPaidPhoneNumberEditText.setEnabled(false);
        fragpaymentPostPaidPhoneProviderEidtText.setEnabled(false);
        fragpaymentPostPaidPhoneProcessButton.setEnabled(false);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            fragpaymentPostPaidPhoneDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentPostPaidPhonePayButton.setEnabled(true);
            fragpaymentPostPaidPhonePayButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentPostPaidPhonePayButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentPostPaidPhoneDataMatchesCheckBox.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentPostPaidPhoneDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentPostPaidPhonePayButton.setEnabled(false);
            fragpaymentPostPaidPhoneDetailLinearLayout.setVisibility(View.INVISIBLE);
            endabledData();
        }
    }
}
