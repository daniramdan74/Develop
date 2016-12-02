package com.dipesan.miniatm.miniatm.view.payment;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class PaymentTelevisionFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private final CharSequence[] itemProviders = {"Indovision", "First Media", "Telkom Vision", "Telkom Speedy"};
    @BindView(R.id.fragpayment_television_customer_number_edit_text) EditText fragpaymentTelevisionCustomerNumberEditText;
    @BindView(R.id.fragpayment_television_process_button) Button fragpaymentTelevisionProcessButton;
    @BindView(R.id.fragpayment_television_providers_text_view) TextView fragpaymentTelevisionProvidersTextView;
    @BindView(R.id.fragpayment_television_customer_id_text_view) TextView fragpaymentTelevisionCustomerIdTextView;
    @BindView(R.id.fragpayment_television_customer_name_text_view) TextView fragpaymentTelevisionCustomerNameTextView;
    @BindView(R.id.fragpayment_television_amount_text_view) TextView fragpaymentTelevisionAmountTextView;
    @BindView(R.id.fragpayment_television_data_matches_check_box) CheckBox fragpaymentTelevisionDataMatchesCheckBox;
    @BindView(R.id.fragpayment_television_pay_button) Button fragpaymentTelevisionPayButton;
    @BindView(R.id.fragpayment_television_details_linear_layout) LinearLayout fragpaymentTelevisionDetailsLinearLayout;
    @BindView(R.id.fragpayment_television_providers_text_input_layout) TextInputLayout fragpaymentTelevisionProvidersTextInputLayout;
    @BindView(R.id.fragpayment_television_customer_number_text_input_layout) TextInputLayout fragpaymentTelevisionCustomerNumberTextInputLayout;
    private int whichitemProviders = 0;
    private boolean checkFlag;
    @BindView(R.id.fragpayment_television_providers_edit_text) EditText fragpaymentTelevisionProvidersEditText;
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


    public PaymentTelevisionFragment() {
        // Required empty public constructor
    }


    public static PaymentTelevisionFragment newIstance() {
        return new PaymentTelevisionFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_television, container, false);
        ButterKnife.bind(this, view);
        fragpaymentTelevisionDetailsLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentTelevisionDataMatchesCheckBox.setOnCheckedChangeListener(this);
        fragpaymentTelevisionProvidersEditText.setInputType(InputType.TYPE_NULL);
        youcubeService = new YoucubeService(getActivity());
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        return view;
    }

    @OnClick({R.id.fragpayment_television_providers_edit_text, R.id.fragpayment_television_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_television_providers_edit_text:
                showProviders();
                break;
            case R.id.fragpayment_television_process_button:
                showDetails();
                break;
        }
    }

    private void showDetails() {
        if (fragpaymentTelevisionProvidersEditText.getText().toString().isEmpty()){
            fragpaymentTelevisionProvidersTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fragpaymentTelevisionProvidersTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentTelevisionCustomerNumberEditText.getText().toString().isEmpty()){
            fragpaymentTelevisionCustomerNumberTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fragpaymentTelevisionCustomerNumberTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentTelevisionProvidersEditText.getText().toString().length()>0&&
                fragpaymentTelevisionCustomerNumberEditText.getText().toString().length()>0){
            disabledData();
            fragpaymentTelevisionDataMatchesCheckBox.setChecked(true);
            fragpaymentTelevisionDetailsLinearLayout.setVisibility(View.VISIBLE);
            fragpaymentTelevisionProvidersTextView.setText(fragpaymentTelevisionProvidersEditText.getText().toString());
            fragpaymentTelevisionCustomerIdTextView.setText(fragpaymentTelevisionCustomerNumberEditText.getText().toString());
            fragpaymentTelevisionAmountTextView.setText("240000");
            fragpaymentTelevisionCustomerNameTextView.setText("Rina");
        }else {
            enabledData();
        }
    }

    private void showProviders() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.phonecreditProvider));
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.setSingleChoiceItems(itemProviders, whichitemProviders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                whichitemProviders = items;
                fragpaymentTelevisionProvidersEditText.setText(itemProviders[items]);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @OnClick({R.id.fragpayment_television_process_button, R.id.fragpayment_television_pay_button})
    public void onClickPay(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_television_pay_button:
                youcubeService.setAmount(Double.parseDouble(fragpaymentTelevisionAmountTextView.getText().toString()));
                youcubeService.setIsMessage(true);
                youcubeService.setMessage(getString(R.string.insertCard));
                youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
                    @Override
                    public void onApproved() {
                        Print();
                    }
                });
                break;
        }
    }

    private void Print() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\n"+getString(R.string.paymenttelevision));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.phonecreditProvider));
                printer.printText("\n"+fragpaymentTelevisionProvidersTextView.getText().toString());
                printer.printText("\n"+getString(R.string.name));
                printer.printText("\n"+fragpaymentTelevisionCustomerNameTextView.getText().toString());
                printer.printText("\n"+getString(R.string.puurchaseelectrictokenIdCust));
                printer.printText("\n"+fragpaymentTelevisionCustomerIdTextView.getText().toString());
                printer.printText("\n"+getString(R.string.phonecreditAmount));
                printer.printText("\n"+fragpaymentTelevisionAmountTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpaymentTelevisionDetailsLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentTelevisionCustomerNumberEditText.setText(null);
        fragpaymentTelevisionProvidersEditText.setText(null);
        fragpaymentTelevisionProcessButton.setEnabled(true);
        Toast.makeText(getActivity(), ""+getString(R.string.transactionsuccess), Toast.LENGTH_SHORT).show();
        fragpaymentTelevisionDataMatchesCheckBox.setChecked(false);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkFlag = isChecked) {
            fragpaymentTelevisionDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentTelevisionPayButton.setEnabled(true);
            fragpaymentTelevisionPayButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentTelevisionPayButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentTelevisionDataMatchesCheckBox.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentTelevisionDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentTelevisionPayButton.setEnabled(false);
            fragpaymentTelevisionDetailsLinearLayout.setVisibility(View.INVISIBLE);
            enabledData();
        }
    }

    private void enabledData() {
        fragpaymentTelevisionCustomerNumberEditText.setEnabled(true);
        fragpaymentTelevisionProvidersEditText.setEnabled(true);
        fragpaymentTelevisionProcessButton.setEnabled(true);
    }

    private void disabledData() {
        fragpaymentTelevisionCustomerNumberEditText.setEnabled(false);
        fragpaymentTelevisionProvidersEditText.setEnabled(false);
        fragpaymentTelevisionProcessButton.setEnabled(false);
    }
}
