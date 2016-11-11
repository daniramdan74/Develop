package com.dipesan.miniatm.miniatm.view.Account;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "AccountFragment";
    private final CharSequence[] itemTypeCustomers = {"Perorangan", "Perusahaan"};
    private final CharSequence[] itemProductsType = {"Biasa", "Berjangka", "Junior", "Deposito"};
    private final CharSequence[] itemTypeAccount = {"Rupiah", "Dollar"};
    private final CharSequence[] itemTypeSourceFunds = {"Pribadi", "Gaji", "Hasil Usaha"};
    private final CharSequence[] itemTypeOpenAccount = {"Simpanan", "Transit Gaji", "Transaksi"};
    private final CharSequence[] itemType = {"KTP", "Pasport"};

    private boolean checkflag;

    @BindView(R.id.fragaccount_type_customers_edit_text) EditText fragaccountTypeCustomersEditText;
    @BindView(R.id.fragaccount_product_type_edit_text) EditText fragaccountProductTypeEditText;
    @BindView(R.id.fragaccount_type_account_edit_text) EditText fragaccountTypeAccountEditText;
    @BindView(R.id.fragaccount_name_customers_edit_text) EditText fragaccountNameCustomersEditText;
    @BindView(R.id.fragaccount_type_identity_edit_text) EditText fragaccountTypeIdentityEditText;
    @BindView(R.id.fragaccount_identity_number_edit_text) EditText fragaccountIdentityNumberEditText;
    @BindView(R.id.fragaccount_source_funds_edit_text) EditText fragaccountSourceFundsEditText;
    @BindView(R.id.fragaccount_open_account_edit_text) EditText fragaccountOpenAccountEditText;
    @BindView(R.id.fragaccount_type_initial_deposit_edit_text) EditText fragaccountTypeInitialDepositEditText;
    @BindView(R.id.fragaccount_data_matches_check_box) CheckBox fragaccountDataMatchesCheckBox;
    @BindView(R.id.fragaccount_process_button) Button fragaccountProcessButton;
    private int whichTypeCustomers, whichProductsType, whichTypeAccount, whichTypeSourceFunds, whichTypeOpenAccount, whichType = 0;

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

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        fragaccountTypeCustomersEditText.setInputType(InputType.TYPE_NULL);
        fragaccountProductTypeEditText.setInputType(InputType.TYPE_NULL);
        fragaccountTypeAccountEditText.setInputType(InputType.TYPE_NULL);

        fragaccountTypeIdentityEditText.setInputType(InputType.TYPE_NULL);
        fragaccountSourceFundsEditText.setInputType(InputType.TYPE_NULL);
        fragaccountSourceFundsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hideKeyboard(view);
            }
        });
        fragaccountOpenAccountEditText.setInputType(InputType.TYPE_NULL);
        fragaccountTypeIdentityEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hideKeyboard(view);
            }
        });
        fragaccountDataMatchesCheckBox.setOnCheckedChangeListener(this);
        fragaccountProcessButton.setEnabled(false);
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);

        return view;
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick(R.id.fragaccount_process_button)
    public void onClick() {
        showProcess();
    }

    private void showProcess() {
        print();
    }

    @OnClick({R.id.fragaccount_type_customers_edit_text, R.id.fragaccount_product_type_edit_text, R.id.fragaccount_type_account_edit_text, R.id.fragaccount_name_customers_edit_text, R.id.fragaccount_type_identity_edit_text, R.id.fragaccount_identity_number_edit_text, R.id.fragaccount_source_funds_edit_text, R.id.fragaccount_open_account_edit_text, R.id.fragaccount_type_initial_deposit_edit_text})
    public void onClickEditText(View view) {
        switch (view.getId()) {
            case R.id.fragaccount_type_customers_edit_text:
                showTypeCustomers();
                break;
            case R.id.fragaccount_product_type_edit_text:
                showProductType();
                break;
            case R.id.fragaccount_type_account_edit_text:
                showTypeAccount();
                break;
            case R.id.fragaccount_name_customers_edit_text:
                break;
            case R.id.fragaccount_type_identity_edit_text:
                showTypeIdentity();
                break;
            case R.id.fragaccount_identity_number_edit_text:
                break;
            case R.id.fragaccount_source_funds_edit_text:
                showSourceFunds();
                break;
            case R.id.fragaccount_open_account_edit_text:
                showOpenAccount();
                break;
            case R.id.fragaccount_type_initial_deposit_edit_text:
                break;
        }
    }

    private void showOpenAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeOpenAccount, whichTypeOpenAccount, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeOpenAccount = which;
                fragaccountOpenAccountEditText.setText(itemTypeOpenAccount[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle("Tujuan Pembukaan");
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();
    }

    private void showSourceFunds() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeSourceFunds, whichTypeSourceFunds, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeSourceFunds = which;
                fragaccountSourceFundsEditText.setText(itemTypeSourceFunds[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle("Sumber Dana");
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();
    }

    private void showTypeIdentity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemType, whichType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichType = which;
                fragaccountTypeIdentityEditText.setText(itemType[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle("Tipe Identitas");
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();
    }

    private void showTypeAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeAccount, whichTypeAccount, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeAccount = which;
                fragaccountTypeAccountEditText.setText(itemTypeAccount[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle("Jenis Rekening");
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();
    }

    private void showProductType() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemProductsType, whichProductsType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichProductsType = which;
                fragaccountProductTypeEditText.setText(itemProductsType[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle("Jenis Produk");
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();

    }

    private void showTypeCustomers() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeCustomers, whichTypeCustomers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeCustomers = which;
                fragaccountTypeCustomersEditText.setText(itemTypeCustomers[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle("Tipe Nasabah");
        builder.setCancelable(false);
        builder.setNegativeButton("Batal", null);
        builder.create().show();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (checkflag = isChecked) {
                fragaccountDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
                fragaccountProcessButton.setEnabled(true);
                fragaccountProcessButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            else {
                fragaccountDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
                fragaccountProcessButton.setEnabled(false);
                fragaccountProcessButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            }

    }
    private void print() {
        com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nPembukaan Rekening");
                printer.printText("\n===============================");
                printer.printText("\nNo Referensi : \n" + AppConstant.NO_REFRENCE);
                printer.printText("\nMerchant :");
                printer.printText("\n"+ AppConstant.NAME_MERCHANT);
                printer.printText("\n"+ AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
    }
}
