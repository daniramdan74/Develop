package com.dipesan.miniatm.miniatm.view.withdraw;


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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.Activity.LoginActivity;
import com.dipesan.miniatm.miniatm.Activity.MainActivity;
import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;
import com.youTransactor.uCube.payment.PaymentContext;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFragment extends Fragment {

    private static final String TAG = "WithdrawFragment";
    @BindView(R.id.withdraw_fragment_amount_edit_text) EditText withdrawFragmentAmountEditText;
    @BindView(R.id.withdraw_fragment_amount_others_edit_text) EditText withdrawFragmentAmountOthersEditText;
    @BindView(R.id.withdraw_fragment_process_button) Button withdrawFragmentProcessButton;
    @BindView(R.id.withdraw_fragment_amount_others_text_input_layout) TextInputLayout withdrawFragmentAmountOthersTextInputLayout;
    @BindView(R.id.withdraw_fragment_amount_others_text_relative_layout) RelativeLayout withdrawFragmentAmountOthersTextRelativeLayout;
    @BindView(R.id.withdraw_fragment_amount_text_input_layout) TextInputLayout withdrawFragmentAmountTextInputLayout;
    private int whichItemsNominal = 0;
    private YoucubeService youcubeService;
    private V1Printer printer;
    private double amount1 = 9.0;
    private String amount;
    private String text;
    final PaymentContext paymentContext = new PaymentContext();
    private String msg;

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

    public WithdrawFragment() {
        // Required empty public constructor
    }

    public static WithdrawFragment newInstance() {
        WithdrawFragment fragment = new WithdrawFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        ButterKnife.bind(this, view);
        withdrawFragmentAmountOthersTextInputLayout.setVisibility(View.GONE);
        withdrawFragmentAmountEditText.setInputType(InputType.TYPE_NULL);
//        withdrawFragmentAmountOthersEditText.addTextChangedListener(new MoneyTextWatcher(withdrawFragmentAmountOthersEditText));
        visibleKeyboardAmount();
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        youcubeService = new YoucubeService(getActivity());
        return view;
    }

    private void visibleKeyboardAmount() {
        withdrawFragmentAmountEditText.setCursorVisible(false);
        withdrawFragmentAmountEditText.setFocusableInTouchMode(false);
        withdrawFragmentAmountEditText.setFocusable(false);
    }

    @OnClick({R.id.withdraw_fragment_amount_edit_text, R.id.withdraw_fragment_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_fragment_amount_edit_text:
                hideKeyboard(getView());
                showNominal();
                break;
            case R.id.withdraw_fragment_process_button:
                showProcess();
                break;
        }
    }

    private void showProcess() {
        text = withdrawFragmentAmountEditText.getText().toString();
        if (withdrawFragmentAmountEditText.getText().toString().isEmpty()) {
            withdrawFragmentAmountTextInputLayout.setError("Tidak Boleh Kosong");
        }
        else {
            withdrawFragmentAmountTextInputLayout.setErrorEnabled(false);
        }
        if (withdrawFragmentAmountEditText.getText().toString().equals("Lainnya") &&
                withdrawFragmentAmountOthersEditText.getText().toString().isEmpty()) {
            withdrawFragmentAmountOthersTextInputLayout.setError("Tidak Boleh Kosong");
        }
        else {
            withdrawFragmentAmountOthersTextInputLayout.setErrorEnabled(false);
        }
        if (withdrawFragmentAmountEditText.getText().toString().equals("Lainnya") &&
                withdrawFragmentAmountOthersEditText.getText().toString().length() > 0) {
            withdrawFragmentAmountEditText.setEnabled(false);
            withdrawFragmentAmountOthersEditText.setEnabled(false);
            amount = withdrawFragmentAmountOthersEditText.getText().toString();
//            Toast.makeText(getActivity(), "Cash"+amount, Toast.LENGTH_SHORT).show();
            insertCard();
        }else if (!text.matches("Lainnya")&&withdrawFragmentAmountEditText.getText().toString().length() > 0) {
            withdrawFragmentAmountEditText.setEnabled(false);
            withdrawFragmentAmountOthersEditText.setEnabled(false);
            amount = withdrawFragmentAmountEditText.getText().toString();
//            Toast.makeText(getActivity(), "Cash"+amount, Toast.LENGTH_SHORT).show();
            insertCard();
        }
        else {
            withdrawFragmentAmountEditText.setEnabled(true);
            withdrawFragmentAmountOthersEditText.setEnabled(true);
        }


//        if (withdrawFragmentAmountEditText.getText().toString().equals("Lainnya")&&
//                withdrawFragmentAmountOthersEditText.getText().toString().length()>0) {
//            withdrawFragmentAmountEditText.setEnabled(false);
//            withdrawFragmentAmountOthersEditText.setEnabled(false);
//        }else {
//            withdrawFragmentAmountEditText.setEnabled(true);
//            withdrawFragmentAmountOthersEditText.setEnabled(true);
//        }
//        if (withdrawFragmentAmountEditText.getText().toString().length()>0){
//            withdrawFragmentAmountEditText.setEnabled(false);
//        }else {
//            withdrawFragmentAmountEditText.setEnabled(true);
//        }
    }
    private void showNominal() {
        List<String> listItems = new ArrayList<String>();
        listItems.add("50000");
        listItems.add("100000");
        listItems.add("200000");
        listItems.add("300000");
        listItems.add("500000");
        listItems.add("1000000");
        listItems.add("Lainnya");
        final CharSequence[] itemsNominal = listItems.toArray(new CharSequence[listItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(itemsNominal, whichItemsNominal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemsNominal = which;
                if (itemsNominal[which] == "Lainnya") {
                    withdrawFragmentAmountOthersTextInputLayout.setVisibility(View.VISIBLE);
                }
                else {
                    withdrawFragmentAmountOthersTextInputLayout.setVisibility(View.GONE);
                    withdrawFragmentAmountOthersEditText.setText(null);
                }
                withdrawFragmentAmountEditText.setText(itemsNominal[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.phonecreditAmount);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void insertCard() {
        youcubeService.setAmount(Double.parseDouble(amount));
        youcubeService.setIsMessage(true);
        youcubeService.setMessage(getString(R.string.insertCard));
        youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
            @Override
            public void onApproved() {
                print();
                Toast.makeText(getActivity(), "Print", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void print() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\n"+getString(R.string.cash));
                printer.printText("\n===============================");
                printer.printText("\n"+getString(R.string.phonecreditAmount));
                printer.setFontSize(30);
                printer.printText("\n"+amount);
                printer.printText("\n");
                printer.setFontSize(24);
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
        Toast.makeText(getActivity(), ""+getString(R.string.transactionsuccess), Toast.LENGTH_SHORT).show();
        withdrawFragmentAmountEditText.setText(null);
        withdrawFragmentAmountOthersEditText.setText(null);
        withdrawFragmentAmountEditText.setEnabled(true);
        withdrawFragmentAmountOthersEditText.setEnabled(true);
        showAlert();
    }



    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
