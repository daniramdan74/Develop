package com.dipesan.miniatm.miniatm.view.payment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
    private YoucubeService youcubeService;
    private static final String TAG = "ElectricFragment";
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
        youcubeService = new YoucubeService(getActivity());
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        return view;
    }

    @OnClick(R.id.fragpayment_electric_proses_button)
    public void onClick() {
        showDetails();
    }


    @OnClick(R.id.fragpayment_electric_pay_button)
    public void onClickPay() {
        youcubeService.setAmount(Double.parseDouble(fragpaymentElectricAmountTextView.getText().toString()));
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
                printer.printText("\n"+getString(R.string.electric_token));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.puurchaseelectrictokenIdCust));
                printer.printText("\n"+fragpaymentElectricAccountNumberTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAccountName));
                printer.printText("\n"+fragpaymentElectricAccountNameTextView.getText().toString());
                printer.printText("\n"+getString(R.string.purchaseelectrictokenFarePower));
                printer.printText("\n"+fragpaymentElectricAccountPowerTextView.getText().toString());
                printer.printText("\n"+getString(R.string.month));
                printer.printText("\n"+fragpaymentElectricMonthTextView.getText().toString());
                printer.printText("\n"+getString(R.string.phonecreditAmount));
                printer.printText("\n"+ fragpaymentElectricAmountTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpaymentElectricDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentElectricIdcustomerEditText.setText(null);
        fragpaymentElectricProsesButton.setEnabled(true);
        Toast.makeText(getActivity(), ""+getString(R.string.transactionsuccess), Toast.LENGTH_SHORT).show();
        fragpaymentElectricDataMatchesCheckButton.setChecked(false);

    }

    private void showDetails() {
        if (fragpaymentElectricIdcustomerEditText.getText().toString().isEmpty()){
            fragpaymentElectricIdcustomerTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fragpaymentElectricIdcustomerTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentElectricIdcustomerEditText.getText().toString().length()>0){
            fragpaymentElectricDetailLinearLayout.setVisibility(View.VISIBLE);
            fragpaymentElectricDataMatchesCheckButton.setChecked(true);
            fragpaymentElectricAccountNumberTextView.setText(fragpaymentElectricIdcustomerEditText.getText().toString());
            fragpaymentElectricAccountNameTextView.setText("Riani");
            fragpaymentElectricAccountPowerTextView.setText("R1/900");
            fragpaymentElectricMonthTextView.setText("Jan 2017");
            fragpaymentElectricAmountTextView.setText("1200000");
            disabledData();
        }else {
            enabledData();
        }
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
