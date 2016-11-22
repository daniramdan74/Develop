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
public class PaymentWaterFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private final CharSequence[] itemTerritory = {"Kab. Aceh Barat", "Kab. Aceh Besar", "Kab. Aceh Tamiang", "Kab. Aceh Timur", "Kab. Bandung", "Kab. Banyumas", "Kab. Banyuwangi", "Kab. Blitar",
            "Kab. Bogor", "Kab. Bondowoso", "Kab. Boyolali", "Kab. Garut"};
    @BindView(R.id.fragpayment_water_territory_edit_text) EditText fragpaymentWaterTerritoryEditText;
    @BindView(R.id.fragpayment_water_customer_id_edit_text) EditText fragpaymentWaterCustomerIdEditText;
    @BindView(R.id.fragpayment_water_process_button) Button fragpaymentWaterProcessButton;
    @BindView(R.id.fragpayment_water_territory_text_view) TextView fragpaymentWaterTerritoryTextView;
    @BindView(R.id.fragpayment_water_customer_id_text_view) TextView fragpaymentWaterCustomerIdTextView;
    @BindView(R.id.fragpayment_water_customer_name_text_view) TextView fragpaymentWaterCustomerNameTextView;
    @BindView(R.id.fragpayment_water_address_text_view) TextView fragpaymentWaterAddressTextView;
    @BindView(R.id.fragpayment_water_period_text_view) TextView fragpaymentWaterPeriodTextView;
    @BindView(R.id.fragpayment_water_group_text_view) TextView fragpaymentWaterGroupTextView;
    @BindView(R.id.fragpayment_water_usage_text_view) TextView fragpaymentWaterUsageTextView;
    @BindView(R.id.fragpayment_water_amount_text_view) TextView fragpaymentWaterAmountTextView;
    @BindView(R.id.fragpayment_water_data_matches_check_box) CheckBox fragpaymentWaterDataMatchesCheckBox;
    @BindView(R.id.fragpayment_water_pay_button) Button fragpaymentWaterPayButton;
    @BindView(R.id.fragpayment_water_detail_linear_layout) LinearLayout fragpaymentWaterDetailLinearLayout;
    @BindView(R.id.fragpayment_water_territory_text_input_layout) TextInputLayout fragpaymentWaterTerritoryTextInputLayout;
    @BindView(R.id.fragpayment_water_customer_id_text_input_layout) TextInputLayout fragpaymentWaterCustomerIdTextInputLayout;
    private int whichItemTerritory = 0;
    private boolean checkFlag;

    public PaymentWaterFragment() {
        // Required empty public constructor
    }

    public static PaymentWaterFragment newIstance() {
        return new PaymentWaterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_water, container, false);
        ButterKnife.bind(this, view);
        fragpaymentWaterTerritoryEditText.setInputType(InputType.TYPE_NULL);
        fragpaymentWaterDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentWaterDataMatchesCheckBox.setOnCheckedChangeListener(this);
        return view;
    }

    @OnClick({R.id.fragpayment_water_territory_edit_text, R.id.fragpayment_water_process_button, R.id.fragpayment_water_pay_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_water_territory_edit_text:
                showTerritory();
                break;
            case R.id.fragpayment_water_process_button:
                showDetails();
                break;
            case R.id.fragpayment_water_pay_button:
                break;
        }
    }

    private void showTerritory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wilayah");
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.setSingleChoiceItems(itemTerritory, whichItemTerritory, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                whichItemTerritory = items;
                fragpaymentWaterTerritoryEditText.setText(itemTerritory[items]);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showDetails() {
        if (fragpaymentWaterTerritoryEditText.getText().toString().isEmpty()){
            fragpaymentWaterTerritoryTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragpaymentWaterTerritoryTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentWaterCustomerIdEditText.getText().toString().isEmpty()){
            fragpaymentWaterCustomerIdTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            fragpaymentWaterCustomerIdTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentWaterTerritoryEditText.getText().toString().length()>0&&
                fragpaymentWaterCustomerIdEditText.getText().toString().length()>0){
            fragpaymentWaterDetailLinearLayout.setVisibility(View.VISIBLE);
            disabledData();
            fragpaymentWaterCustomerIdTextView.setText(fragpaymentWaterCustomerIdEditText.getText().toString());
            fragpaymentWaterTerritoryTextView.setText(fragpaymentWaterTerritoryEditText.getText().toString());
            fragpaymentWaterCustomerNameTextView.setText("Maulana");
            fragpaymentWaterAddressTextView.setText("Jl. Holis No. 25 Bandung");
            fragpaymentWaterPeriodTextView.setText("Nov 2016");
            fragpaymentWaterGroupTextView.setText("R2");
            fragpaymentWaterUsageTextView.setText("5 M3");
            fragpaymentWaterAmountTextView.setText("240,000");
            fragpaymentWaterDataMatchesCheckBox.setChecked(true);
        }else {
            enabledData();
        }
    }

    private void enabledData() {
        fragpaymentWaterTerritoryEditText.setEnabled(true);
        fragpaymentWaterCustomerIdEditText.setEnabled(true);
        fragpaymentWaterProcessButton.setEnabled(true);
    }

    private void disabledData() {
        fragpaymentWaterTerritoryEditText.setEnabled(false);
        fragpaymentWaterCustomerIdEditText.setEnabled(false);
        fragpaymentWaterProcessButton.setEnabled(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkFlag = isChecked) {
            fragpaymentWaterDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentWaterPayButton.setEnabled(true);
            fragpaymentWaterPayButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentWaterPayButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentWaterDataMatchesCheckBox.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentWaterPayButton.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentWaterPayButton.setEnabled(false);
            fragpaymentWaterDetailLinearLayout.setVisibility(View.INVISIBLE);
            enabledData();
        }
    }
}
