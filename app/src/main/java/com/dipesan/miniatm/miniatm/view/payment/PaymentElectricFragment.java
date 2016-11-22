package com.dipesan.miniatm.miniatm.view.payment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
public class PaymentElectricFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.fragpayment_electric_idcustomer_text_input_layout) TextInputLayout fragpaymentElectricIdcustomerTextInputLayout;
    @BindView(R.id.fragpayment_electric_idcustomer_edit_text) EditText fragpaymentElectricIdcustomerEditText;
    @BindView(R.id.fragpayment_electric_proses_button) Button fragpaymentElectricProsesButton;
    @BindView(R.id.fragpayment_electric_account_number_text_view) TextView fragpaymentElectricAccountNumberTextView;
    @BindView(R.id.fragpayment_electric_account_name_text_view) TextView fragpaymentElectricAccountNameTextView;
    @BindView(R.id.fragpayment_electric_account_power_text_view) TextView fragpaymentElectricAccountPowerTextView;
    @BindView(R.id.fragpayment_electric_month_text_view) TextView fragpaymentElectricMonthTextView;
    @BindView(R.id.fragpayment_electric_amount_text_view) TextView fragpaymentElectricAmountTextView;
    @BindView(R.id.fragpayment_electric_data_matches_check_button) CheckBox fragpaymentElectricDataMatchesCheckButton;
    @BindView(R.id.fragpayment_electric_pay_button) Button fragpaymentElectricPayButton;
    @BindView(R.id.fragpayment_electric_detail_linear_layout) LinearLayout fragpaymentElectricDetailLinearLayout;
    private boolean checkFlag;
    public PaymentElectricFragment() {
        // Required empty public constructor
    }

    public static PaymentElectricFragment newIstance() {
        return new PaymentElectricFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_electric, container, false);
        ButterKnife.bind(this, view);
        fragpaymentElectricDataMatchesCheckButton.setOnCheckedChangeListener(this);
        fragpaymentElectricDetailLinearLayout.setVisibility(View.INVISIBLE);
        return view;
    }

    @OnClick(R.id.fragpayment_electric_proses_button)
    public void onClick() {
        showDetails();
    }


    @OnClick(R.id.fragpayment_electric_pay_button)
    public void onClickPay() {

    }
    private void showDetails() {
        fragpaymentElectricDetailLinearLayout.setVisibility(View.VISIBLE);
        disabledData();
        fragpaymentElectricDataMatchesCheckButton.setChecked(true);
        fragpaymentElectricAccountNumberTextView.setText(fragpaymentElectricIdcustomerEditText.getText().toString());
        fragpaymentElectricAccountNameTextView.setText("Riani");
        fragpaymentElectricAccountPowerTextView.setText("R1/900");
        fragpaymentElectricMonthTextView.setText("Nov 2016");
        fragpaymentElectricAmountTextView.setText("1,200,000");
    }
    private void enabledData(){
        fragpaymentElectricIdcustomerEditText.setEnabled(true);
        fragpaymentElectricProsesButton.setEnabled(true);
    }
    private void disabledData(){
        fragpaymentElectricIdcustomerEditText.setEnabled(false);
        fragpaymentElectricProsesButton.setEnabled(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkFlag = isChecked) {
            fragpaymentElectricDataMatchesCheckButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentElectricPayButton.setEnabled(true);
            fragpaymentElectricPayButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentElectricPayButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentElectricDataMatchesCheckButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentElectricDataMatchesCheckButton.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentElectricPayButton.setEnabled(false);
            fragpaymentElectricDetailLinearLayout.setVisibility(View.INVISIBLE);
            enabledData();
        }

    }
}
