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

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentInsuranceFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    final CharSequence[] itemsInsurance = {
            "PT. Prudential Life Assurance", "PT. Asuransi Allianz Life Indonesia", "PT. AIA Financial"
            , "PT. Asuransi Jiwa SInarmas", "PT. Asuransi Jiwa Manulife Indonesia", "PT Avrist Assurance"
    };
    @BindView(R.id.fragpayment_insurance_customer_number_edit_text) EditText fragpaymentInsuranceCustomerNumberEditText;
    @BindView(R.id.fragpayment_insurance_providers_text_view) TextView fragpaymentInsuranceProvidersTextView;
    @BindView(R.id.fragpayment_insurance_customer_name_text_view) TextView fragpaymentInsuranceCustomerNameTextView;
    @BindView(R.id.fragpayment_insurance_customer_number_text_view) TextView fragpaymentInsuranceCustomerNumberTextView;
    @BindView(R.id.fragpayment_insurance_amount_text_view) TextView fragpaymentInsuranceAmountTextView;
    @BindView(R.id.fragpayment_insurance_data_matches_check_box) CheckBox fragpaymentInsuranceDataMatchesCheckBox;
    @BindView(R.id.fragpayment_insurance_pay_button) Button fragpaymentInsurancePayButton;
    @BindView(R.id.fragpayment_insurance_details_linear_layout) LinearLayout fragpaymentInsuranceDetailsLinearLayout;
    @BindView(R.id.fragpayment_insurance_provider_text_input_layout) TextInputLayout fragpaymentInsuranceProviderTextInputLayout;
    @BindView(R.id.fragpayment_insurance_customer_number_text_input_layout) TextInputLayout fragpaymentInsuranceCustomerNumberTextInputLayout;
    private int whichitemsInsurance = 0;
    @BindView(R.id.fragpayment_insurance_provider_edit_text) EditText fragpaymentInsuranceProviderEditText;
    @BindView(R.id.fragpayment_insurance_process_button) Button fragpaymentInsuranceProcessButton;
    private boolean checkFlag;


    public PaymentInsuranceFragment() {
        // Required empty public constructor
    }

    public static PaymentInsuranceFragment newIstance() {
        return new PaymentInsuranceFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_insurance, container, false);
        ButterKnife.bind(this, view);
        fragpaymentInsuranceDataMatchesCheckBox.setOnCheckedChangeListener(this);
        fragpaymentInsuranceDetailsLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentInsuranceProviderEditText.setInputType(InputType.TYPE_NULL);
        return view;
    }

    @OnClick({R.id.fragpayment_insurance_provider_edit_text, R.id.fragpayment_insurance_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_insurance_provider_edit_text:
                showProvider();
                break;
            case R.id.fragpayment_insurance_process_button:
                showDetails();
                break;
        }
    }

    private void showDetails() {
        if (fragpaymentInsuranceProviderEditText.getText().toString().isEmpty()){
            fragpaymentInsuranceProviderTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragpaymentInsuranceProviderTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentInsuranceCustomerNumberEditText.getText().toString().isEmpty()){
            fragpaymentInsuranceCustomerNumberTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragpaymentInsuranceCustomerNumberTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentInsuranceProviderEditText.getText().toString().length()>0&&
                fragpaymentInsuranceCustomerNumberEditText.getText().toString().length()>0){
            fragpaymentInsuranceDataMatchesCheckBox.setChecked(true);
            fragpaymentInsuranceDetailsLinearLayout.setVisibility(View.VISIBLE);
            disabledData();
            fragpaymentInsuranceProvidersTextView.setText(fragpaymentInsuranceProviderEditText.getText().toString());
            fragpaymentInsuranceCustomerNumberTextView.setText(fragpaymentInsuranceCustomerNumberEditText.getText().toString());
            fragpaymentInsuranceCustomerNameTextView.setText("Rendy Maulana");
            fragpaymentInsuranceAmountTextView.setText("500,000");

        } else{
            enabledData();
        }
    }

    private void showProvider() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.phonecreditProvider));
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.setSingleChoiceItems(itemsInsurance, whichitemsInsurance, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                whichitemsInsurance = items;
                fragpaymentInsuranceProviderEditText.setText(itemsInsurance[items]);
                dialogInterface.dismiss();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @OnClick(R.id.fragpayment_insurance_pay_button)
    public void onClickPay() {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkFlag = isChecked) {
            fragpaymentInsuranceDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentInsurancePayButton.setEnabled(true);
            fragpaymentInsurancePayButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentInsurancePayButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentInsuranceDataMatchesCheckBox.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentInsuranceDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentInsurancePayButton.setEnabled(false);
            fragpaymentInsuranceDetailsLinearLayout.setVisibility(View.INVISIBLE);
            enabledData();
        }
    }

    private void enabledData() {
        fragpaymentInsuranceProviderEditText.setEnabled(true);
        fragpaymentInsuranceCustomerNumberEditText.setEnabled(true);
        fragpaymentInsuranceProcessButton.setEnabled(true);
    }

    private void disabledData() {
        fragpaymentInsuranceProviderEditText.setEnabled(false);
        fragpaymentInsuranceCustomerNumberEditText.setEnabled(false);
        fragpaymentInsuranceProcessButton.setEnabled(false);
    }

}

