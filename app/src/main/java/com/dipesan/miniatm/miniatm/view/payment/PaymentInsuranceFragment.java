package com.dipesan.miniatm.miniatm.view.payment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private YoucubeService youcubeService;
    private static final String TAG = "InsuranceFragment";
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
        youcubeService = new YoucubeService(getActivity());
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
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
            fragpaymentInsuranceProviderTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fragpaymentInsuranceProviderTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentInsuranceCustomerNumberEditText.getText().toString().isEmpty()){
            fragpaymentInsuranceCustomerNumberTextInputLayout.setError(getString(R.string.canNotEmpty));
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
            fragpaymentInsuranceAmountTextView.setText("500000");

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
        youcubeService.setAmount(Double.parseDouble(fragpaymentInsuranceAmountTextView.getText().toString()));
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
                printer.printText("\n"+getString(R.string.paymentinsurance));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.phonecreditProvider));
                printer.printText("\n"+fragpaymentInsuranceProvidersTextView.getText().toString());
                printer.printText("\n"+getString(R.string.name));
                printer.printText("\n"+fragpaymentInsuranceCustomerNameTextView.getText().toString());
                printer.printText("\n"+getString(R.string.puurchaseelectrictokenIdCust));
                printer.printText("\n"+fragpaymentInsuranceCustomerNumberTextView.getText().toString());
                printer.printText("\n"+getString(R.string.phonecreditAmount));
                printer.printText("\n"+fragpaymentInsuranceAmountTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpaymentInsuranceDetailsLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentInsuranceCustomerNumberEditText.setText(null);
        fragpaymentInsuranceProviderEditText.setText(null);
        fragpaymentInsuranceProcessButton.setEnabled(true);
        Toast.makeText(getActivity(), ""+getString(R.string.transactionsuccess), Toast.LENGTH_SHORT).show();
        fragpaymentInsuranceDataMatchesCheckBox.setChecked(false);
        showAlert();

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

