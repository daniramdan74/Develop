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
    private YoucubeService youcubeService;
    private static final String TAG = "WaterFragment";
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
        youcubeService = new YoucubeService(getActivity());
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
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
                youcubeService.setAmount(Double.parseDouble(fragpaymentWaterAmountTextView.getText().toString()));
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
                printer.printText("\n"+getString(R.string.paymentwater));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.waterTerritory));
                printer.printText("\n"+fragpaymentWaterTerritoryTextView.getText().toString());
                printer.printText("\n"+getString(R.string.puurchaseelectrictokenIdCust));
                printer.printText("\n"+fragpaymentWaterCustomerIdTextView.getText().toString());
                printer.printText("\n"+getString(R.string.name));
                printer.printText("\n"+fragpaymentWaterCustomerNameTextView.getText().toString());
                printer.printText("\n"+getString(R.string.address));
                printer.printText("\n"+fragpaymentWaterAddressTextView.getText().toString());
                printer.printText("\n"+getString(R.string.period));
                printer.printText("\n"+fragpaymentWaterPeriodTextView.getText().toString());
                printer.printText("\n"+getString(R.string.group));
                printer.printText("\n"+fragpaymentWaterGroupTextView.getText().toString());
                printer.printText("\n"+getString(R.string.usage));
                printer.printText("\n"+fragpaymentWaterUsageTextView.getText().toString());
                printer.printText("\n"+getString(R.string.phonecreditAmount));
                printer.printText("\n"+fragpaymentWaterAmountTextView.getText().toString());
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });

        fragpaymentWaterDetailLinearLayout.setVisibility(View.INVISIBLE);
        fragpaymentWaterTerritoryEditText.setText(null);
        fragpaymentWaterCustomerIdEditText.setText(null);
        fragpaymentWaterProcessButton.setEnabled(true);
        Toast.makeText(getActivity(), ""+getString(R.string.transactionsuccess), Toast.LENGTH_SHORT).show();
        fragpaymentWaterDataMatchesCheckBox.setChecked(false);
        showAlert();

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
            fragpaymentWaterTerritoryTextInputLayout.setError(getString(R.string.canNotEmpty));
        }else {
            fragpaymentWaterTerritoryTextInputLayout.setErrorEnabled(false);
        }
        if (fragpaymentWaterCustomerIdEditText.getText().toString().isEmpty()){
            fragpaymentWaterCustomerIdTextInputLayout.setError(getString(R.string.canNotEmpty));
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
            fragpaymentWaterAmountTextView.setText("240000");
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
