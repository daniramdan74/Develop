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
public class InternetFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private final CharSequence[] itemProviders = {"Indovision", "Telkom", "Indosat"};
    @BindView(R.id.fragpayment_internet_providers_text_input_layout) TextInputLayout fragpaymentInternetProvidersTextInputLayout;
    @BindView(R.id.fragpayment_internet_account_number_text_input_layout) TextInputLayout fragpaymentInternetAccountNumberTextInputLayout;
    private int whichItemProviders = 0;
    @BindView(R.id.fragpayment_internet_providers_edit_text) EditText fragpaymentInternetProvidersEditText;
    @BindView(R.id.fragpayment_internet_account_number_edit_text) EditText fragpaymentInternetAccountNumberEditText;
    @BindView(R.id.fragpayment_internet_process_button) Button fragpaymentInternetProcessButton;
    @BindView(R.id.fragpayment_internet_providers_text_view) TextView fragpaymentInternetProvidersTextView;
    @BindView(R.id.fragpayment_internet_account_number_text_view) TextView fragpaymentInternetAccountNumberTextView;
    @BindView(R.id.fragpayment_internet_account_name_text_view) TextView fragpaymentInternetAccountNameTextView;
    @BindView(R.id.fragpayment_internet_amount_text_view) TextView fragpaymentInternetAmountTextView;
    @BindView(R.id.fragpayment_internet_data_matches_check_box) CheckBox fragpaymentInternetDataMatchesCheckBox;
    @BindView(R.id.fragpayment_internet_pay_button) Button fragpaymentInternetPayButton;
    @BindView(R.id.fragpayment_internet_details_linear_layout) LinearLayout fragpaymentInternetDetailsLinearLayout;
    private boolean checkflag;
    private YoucubeService youcubeService;
    private static final String TAG = "InternetFragment";
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

    public InternetFragment() {
        // Required empty public constructor
    }

    public static InternetFragment newInstance() {
        InternetFragment fragment = new InternetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internet, container, false);
        ButterKnife.bind(this, view);
        fragpaymentInternetProvidersEditText.setInputType(InputType.TYPE_NULL);
        fragpaymentInternetDetailsLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentInternetDataMatchesCheckBox.setOnCheckedChangeListener(this);
        youcubeService = new YoucubeService(getActivity());
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        return view;
    }

    @OnClick({R.id.fragpayment_internet_providers_edit_text, R.id.fragpayment_internet_process_button, R.id.fragpayment_internet_pay_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_internet_providers_edit_text:
                showProviders();
                break;
            case R.id.fragpayment_internet_process_button:
                showDetails();
                break;
            case R.id.fragpayment_internet_pay_button:
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
                printer.printText("\nPembayaran Internet");
                printer.printText("\n===============================");
                printer.printText("\nPenyedia : ");
                printer.printText("\n"+fragpaymentInternetProvidersTextView.getText().toString());
                printer.printText("\nID PEL/No Referensi : ");
                printer.printText("\n"+fragpaymentInternetAccountNumberTextView.getText().toString());
                printer.printText("\nNama : ");
                printer.printText("\n"+fragpaymentInternetAccountNameTextView.getText().toString());
                printer.printText("\nJumlah Tagihan : ");
                printer.printText("\n"+fragpaymentInternetAmountTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpaymentInternetDetailsLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentInternetAccountNumberEditText.setText(null);
        fragpaymentInternetProvidersEditText.setText(null);
        fragpaymentInternetProcessButton.setEnabled(true);
        Toast.makeText(getActivity(), "Pembayaran Telepon Prabayar\n Berhasil dilakukan", Toast.LENGTH_SHORT).show();
        fragpaymentInternetDataMatchesCheckBox.setChecked(false);
    }

    private void showDetails() {
        fragpaymentInternetDataMatchesCheckBox.setChecked(true);
        if (fragpaymentInternetProvidersEditText.getText().toString().isEmpty()) {
        fragpaymentInternetProvidersTextInputLayout.setError("Tidak Boleh Kosong");
        }
        else {
            fragpaymentInternetProvidersTextInputLayout.setErrorEnabled(false);

        }
        if (fragpaymentInternetAccountNumberEditText.getText().toString().isEmpty()) {
            fragpaymentInternetAccountNumberTextInputLayout.setError("Tidak Boleh Kosong");
        }
        else {
            fragpaymentInternetAccountNumberTextInputLayout.setErrorEnabled(false);

        }
        if (fragpaymentInternetProvidersEditText.getText().toString().length()>0&&
                fragpaymentInternetAccountNumberEditText.getText().toString().length()>0){
            fragpaymentInternetDetailsLinearLayout.setVisibility(View.VISIBLE);
            disabledData();
            fragpaymentInternetProvidersTextView.setText(fragpaymentInternetProvidersEditText.getText().toString());
            fragpaymentInternetAccountNumberTextView.setText(fragpaymentInternetAccountNumberEditText.getText().toString());
            fragpaymentInternetAccountNameTextView.setText("Dani Ramdan");
            fragpaymentInternetAmountTextView.setText("450,000");
        }else {
            enabledData();
        }
    }

    private void enabledData() {
        fragpaymentInternetProvidersEditText.setEnabled(true);
        fragpaymentInternetAccountNumberEditText.setEnabled(true);
        fragpaymentInternetProcessButton.setEnabled(true);

    }

    private void disabledData() {
        fragpaymentInternetProvidersEditText.setEnabled(false);
        fragpaymentInternetAccountNumberEditText.setEnabled(false);
        fragpaymentInternetProcessButton.setEnabled(false);

    }


    private void showProviders() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.postpaidphoneProvider));
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.setSingleChoiceItems(itemProviders, whichItemProviders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                whichItemProviders = items;
                fragpaymentInternetProvidersEditText.setText(itemProviders[items]);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            fragpaymentInternetDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragpaymentInternetPayButton.setEnabled(true);
            fragpaymentInternetPayButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentInternetPayButton.setTextColor(getResources().getColor(R.color.colorTextIcons));
            disabledData();
        }
        else {
            fragpaymentInternetDataMatchesCheckBox.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            fragpaymentInternetDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragpaymentInternetPayButton.setEnabled(false);
            fragpaymentInternetDetailsLinearLayout.setVisibility(View.INVISIBLE);
            enabledData();
        }

    }
}
