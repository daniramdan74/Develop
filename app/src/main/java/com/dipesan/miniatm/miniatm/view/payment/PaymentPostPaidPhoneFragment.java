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
public class PaymentPostPaidPhoneFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private final CharSequence[] itemProviders = {"Telkomsel", "Indosat", "XL/Axis", "3 (TRI)", "Flexi", "Esia", "Smartfren"};
    @BindView(R.id.fragpayment_post_paid_phone_provider_text_input_layout) TextInputLayout fragpaymentPostPaidPhoneProviderTextInputLayout;
    @BindView(R.id.fragpayment_post_paid_phone_number_text_input_layout) TextInputLayout fragpaymentPostPaidPhoneNumberTextInputLayout;
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
    private YoucubeService youcubeService;
    private static final String TAG = "PostPaidPhoneFragment";
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
        youcubeService = new YoucubeService(getActivity());
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
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
        if (fragpaymentPostPaidPhoneProviderEidtText.getText().toString().isEmpty()) {
            fragpaymentPostPaidPhoneProviderTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragpaymentPostPaidPhoneProviderTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentPostPaidPhoneNumberEditText.getText().toString().isEmpty()) {
            fragpaymentPostPaidPhoneNumberTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragpaymentPostPaidPhoneNumberTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentPostPaidPhoneProviderEidtText.getText().toString().length() > 0
                && fragpaymentPostPaidPhoneNumberEditText.getText().toString().length() > 0
                ) {

            fragpaymentPostPaidPhoneDetailLinearLayout.setVisibility(View.VISIBLE);
            fragpaymentPostPaidPhoneProviderTextView.setText(fragpaymentPostPaidPhoneProviderEidtText.getText().toString());
            fragpaymentPostPaidPhoneNumberTextView.setText(fragpaymentPostPaidPhoneNumberEditText.getText().toString());
            fragpaymentPostPaidPhoneCustomerNameTextView.setText("Dani Ramdan");
            fragpaymentPostPaidPhoneAmountTextView.setText("850000");
            fragpaymentPostPaidPhoneDataMatchesCheckBox.setChecked(true);
            disabledData();
        }
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
        youcubeService.setAmount(Double.parseDouble(fragpaymentPostPaidPhoneAmountTextView.getText().toString()));
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
                printer.printText("\n"+getString(R.string.post_paid_phone));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.phonecreditProvider));
                printer.printText("\n"+fragpaymentPostPaidPhoneProviderTextView.getText().toString());
                printer.printText("\n"+getString(R.string.phonecreditPhoneNumber));
                printer.printText("\n"+fragpaymentPostPaidPhoneNumberTextView.getText().toString());
                printer.printText("\n"+getString(R.string.name));
                printer.printText("\n"+fragpaymentPostPaidPhoneCustomerNameTextView.getText().toString());
                printer.printText("\n"+getString(R.string.interbankAmount));
                printer.printText("\n"+fragpaymentPostPaidPhoneAmountTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpaymentPostPaidPhoneDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentPostPaidPhoneProviderEidtText.setText(null);
        fragpaymentPostPaidPhoneNumberEditText.setText(null);
        fragpaymentPostPaidPhoneProcessButton.setEnabled(true);
        Toast.makeText(getActivity(), ""+getString(R.string.transactionsuccess), Toast.LENGTH_SHORT).show();
        fragpaymentPostPaidPhoneDataMatchesCheckBox.setChecked(false);
        showAlert();
    }

    private void endabledData() {
        fragpaymentPostPaidPhoneNumberEditText.setEnabled(true);
        fragpaymentPostPaidPhoneProviderEidtText.setEnabled(true);
        fragpaymentPostPaidPhoneProcessButton.setEnabled(true);

    }

    private void disabledData() {
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
