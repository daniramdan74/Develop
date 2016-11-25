package com.dipesan.miniatm.miniatm.view.Account;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.dipesan.miniatm.miniatm.utils.MoneyTextWatcher;
import com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.R.id.fragaccount_name_customers_edit_text;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "AccountFragment";
//    private final CharSequence[] itemTypeCustomers = {"Perorangan", "Perusahaan"};
//    private final CharSequence[] itemProductsType = {"Biasa", "Berjangka", "Junior", "Deposito"};
//    private final CharSequence[] itemTypeAccount = {"Rupiah", "Dollar"};
//    private final CharSequence[] itemTypeSourceFunds = {"Pribadi", "Gaji", "Hasil Usaha"};
//    private final CharSequence[] itemTypeOpenAccount = {"Simpanan", "Transit Gaji", "Transaksi"};
//    private final CharSequence[] itemType = {"KTP", "Pasport"};
    @BindView(R.id.fragaccount_type_customers_text_input_layout) TextInputLayout fragaccountTypeCustomersTextInputLayout;
    @BindView(R.id.fragaccount_product_type_text_input_layout) TextInputLayout fragaccountProductTypeTextInputLayout;
    @BindView(R.id.fragaccount_type_account_text_input_layout) TextInputLayout fragaccountTypeAccountTextInputLayout;
    @BindView(R.id.fragaccount_name_customers_text_input_layout) TextInputLayout fragaccountNameCustomersTextInputLayout;
    @BindView(R.id.fragaccount_type_identity_text_input_layout) TextInputLayout fragaccountTypeIdentityTextInputLayout;
    @BindView(R.id.fragaccount_identity_number_text_input_layout) TextInputLayout fragaccountIdentityNumberTextInputLayout;
    @BindView(R.id.fragaccount_source_funds_text_input_layout) TextInputLayout fragaccountSourceFundsTextInputLayout;
    @BindView(R.id.fragaccount_open_account_text_input_layout) TextInputLayout fragaccountOpenAccountTextInputLayout;
    @BindView(R.id.fragaccount_type_initial_deposit_text_input_layout) TextInputLayout fragaccountTypeInitialDepositTextInputLayout;
    @BindView(R.id.fragaccount_type_customers_edit_text) EditText fragaccountTypeCustomersEditText;
    @BindView(R.id.fragaccount_product_type_edit_text) EditText fragaccountProductTypeEditText;
    @BindView(R.id.fragaccount_type_account_edit_text) EditText fragaccountTypeAccountEditText;
    @BindView(fragaccount_name_customers_edit_text) EditText fragaccountNameCustomersEditText;
    @BindView(R.id.fragaccount_type_identity_edit_text) EditText fragaccountTypeIdentityEditText;
    @BindView(R.id.fragaccount_identity_number_edit_text) EditText fragaccountIdentityNumberEditText;
    @BindView(R.id.fragaccount_source_funds_edit_text) EditText fragaccountSourceFundsEditText;
    @BindView(R.id.fragaccount_open_account_edit_text) EditText fragaccountOpenAccountEditText;
    @BindView(R.id.fragaccount_type_initial_deposit_edit_text) EditText fragaccountTypeInitialDepositEditText;
    @BindView(R.id.fragaccount_data_matches_check_box) CheckBox fragaccountDataMatchesCheckBox;
    @BindView(R.id.fragaccount_process_button) Button fragaccountProcessButton;
    private YoucubeService youcubeService;
    private boolean checkflag;
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
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
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
        youcubeService = new YoucubeService(getActivity());
        fragaccountNameCustomersEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(view);
                    handled = true;
                }
                return handled;
            }
        });
        fragaccountTypeIdentityEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(view);
                    handled = true;
                }
                return handled;
            }
        });
        fragaccountTypeInitialDepositEditText.addTextChangedListener(new MoneyTextWatcher(fragaccountTypeInitialDepositEditText));
        fragaccountTypeInitialDepositEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard(view);
                    handled = true;
                }
                return handled;
            }
        });

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
        if (fragaccountTypeCustomersEditText.getText().toString().isEmpty()) {
            fragaccountTypeCustomersTextInputLayout.setError(getString(R.string.canNotEmpty));
            return;
        }
        else {
            fragaccountTypeCustomersTextInputLayout.setErrorEnabled(false);
        }
        if (fragaccountProductTypeEditText.getText().toString().isEmpty()) {
            fragaccountProductTypeTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountProductTypeTextInputLayout.setErrorEnabled(false);
        }
        if (fragaccountTypeAccountEditText.getText().toString().isEmpty()) {
            fragaccountTypeAccountTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountTypeAccountTextInputLayout.setErrorEnabled(false);
        }
        if (fragaccountNameCustomersEditText.getText().toString().isEmpty()) {
            fragaccountNameCustomersTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountNameCustomersTextInputLayout.setErrorEnabled(false);
        }
        if (fragaccountTypeIdentityEditText.getText().toString().isEmpty()) {
            fragaccountTypeIdentityTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountTypeIdentityTextInputLayout.setErrorEnabled(false);
        }

        if (fragaccountIdentityNumberEditText.getText().toString().isEmpty()) {
            fragaccountIdentityNumberTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountIdentityNumberTextInputLayout.setErrorEnabled(false);
        }

        if (fragaccountSourceFundsEditText.getText().toString().isEmpty()) {
            fragaccountSourceFundsTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountSourceFundsTextInputLayout.setErrorEnabled(false);
        }
        if (fragaccountOpenAccountEditText.getText().toString().isEmpty()) {
            fragaccountOpenAccountTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountOpenAccountTextInputLayout.setErrorEnabled(false);
        }
        if (fragaccountTypeInitialDepositEditText.getText().toString().isEmpty()) {
            fragaccountTypeInitialDepositTextInputLayout.setError(getString(R.string.canNotEmpty));
        }
        else {
            fragaccountTypeInitialDepositTextInputLayout.setErrorEnabled(false);
        }

        if (fragaccountTypeCustomersEditText.getText().toString().length() > 0 &&
                fragaccountProductTypeEditText.getText().toString().length() > 0 &&
                fragaccountTypeAccountEditText.getText().toString().length() > 0 &&
                fragaccountNameCustomersEditText.getText().toString().length() > 0 &&
                fragaccountTypeIdentityEditText.getText().toString().length() > 0 &&
                fragaccountIdentityNumberEditText.getText().toString().length() > 0 &&
                fragaccountSourceFundsEditText.getText().toString().length() > 0 &&
                fragaccountOpenAccountEditText.getText().toString().length() > 0 &&
                fragaccountTypeInitialDepositEditText.getText().toString().length() > 0) {
            youcubeService.setIsMessage(true);
            youcubeService.setMessage("Login Merchant");
            youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
                @Override
                public void onApproved() {
                    print();
                    Toast.makeText(getActivity(), "Data Pembukaan Rekening" + "\n       Berhasil Dilakukan !", Toast.LENGTH_SHORT).show();
                    clearEditText();
                }
            });

        }
    }

    @OnClick({R.id.fragaccount_type_customers_edit_text, R.id.fragaccount_product_type_edit_text, R.id.fragaccount_type_account_edit_text, fragaccount_name_customers_edit_text, R.id.fragaccount_type_identity_edit_text, R.id.fragaccount_identity_number_edit_text, R.id.fragaccount_source_funds_edit_text, R.id.fragaccount_open_account_edit_text, R.id.fragaccount_type_initial_deposit_edit_text})
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
            case fragaccount_name_customers_edit_text:
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
        List<String> listItems = new ArrayList<String>();
        listItems.add(getResources().getString(R.string.itemtypeopenaccountdeposit));
        listItems.add(getResources().getString(R.string.itemtypeopenaccountSalary));
        listItems.add(getResources().getString(R.string.itemtypeopenaccountTransaction));
        final CharSequence[] itemTypeOpenAccount = listItems.toArray(new CharSequence[listItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(itemTypeOpenAccount, whichTypeOpenAccount, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeOpenAccount = which;
                fragaccountOpenAccountEditText.setText(itemTypeOpenAccount[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.purpose_of_account_opening);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void showSourceFunds() {
        List<String> listItems = new ArrayList<String>();
        listItems.add(getResources().getString(R.string.itemtypesourcefundsPersonal));
        listItems.add(getResources().getString(R.string.itemtypesourcefundsSalary));
        listItems.add(getResources().getString(R.string.itemtypesourcefundsCompanyResults));
        final CharSequence[] itemTypeSourceFunds = listItems.toArray(new CharSequence[listItems.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeSourceFunds, whichTypeSourceFunds, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeSourceFunds = which;
                fragaccountSourceFundsEditText.setText(itemTypeSourceFunds[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.source_of_funds);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void showTypeIdentity() {
        List<String> listItems = new ArrayList<String>();
        listItems.add(getResources().getString(R.string.itemtypeKTP));
        listItems.add(getResources().getString(R.string.itemtypePassport));
        final CharSequence[] itemType = listItems.toArray(new CharSequence[listItems.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemType, whichType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichType = which;
                fragaccountTypeIdentityEditText.setText(itemType[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.type);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void showTypeAccount() {
        List<String> listItems = new ArrayList<String>();
        listItems.add(getResources().getString(R.string.itemtypeAccountRupiah));
        listItems.add(getResources().getString(R.string.itemTypeAccountDollar));
        final CharSequence[] itemTypeAccount = listItems.toArray(new CharSequence[listItems.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeAccount, whichTypeAccount, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeAccount = which;
                fragaccountTypeAccountEditText.setText(itemTypeAccount[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.type_of_account);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    private void showProductType() {
        List<String> listItems = new ArrayList<String>();
        listItems.add(getResources().getString(R.string.itemproductsTypeOrdinary));
        listItems.add(getResources().getString(R.string.itemproductsTypePeriod));
        listItems.add(getResources().getString(R.string.itemproductsTypeJunior));
        listItems.add(getResources().getString(R.string.itemproductsTypeDeposit));

        final CharSequence[] itemProductsType = listItems.toArray(new CharSequence[listItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemProductsType, whichProductsType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichProductsType = which;
                fragaccountProductTypeEditText.setText(itemProductsType[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.type_of_products);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();

    }

    private void showTypeCustomers() {
        List<String> listItems = new ArrayList<String>();
        listItems.add(getResources().getString(R.string.individual));
        listItems.add(getResources().getString(R.string.corporate));
        final CharSequence[] itemTypeCustomers = listItems.toArray(new CharSequence[listItems.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(itemTypeCustomers, whichTypeCustomers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichTypeCustomers = which;
                fragaccountTypeCustomersEditText.setText(itemTypeCustomers[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.type_of_customers);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (checkflag = isChecked) {
            fragaccountDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorPrimary));
            fragaccountProcessButton.setEnabled(true);
            fragaccountProcessButton.setHighlightColor(getResources().getColor(R.color.colorPrimaryDark));
            lockEditText();
        }
        else {
            fragaccountDataMatchesCheckBox.setTextColor(getResources().getColor(R.color.colorDivide));
            fragaccountProcessButton.setEnabled(false);
            fragaccountProcessButton.setHighlightColor(getResources().getColor(R.color.colorDivide));
            openEditText();
        }

    }

    private void lockEditText() {
        fragaccountTypeCustomersEditText.setEnabled(false);
        fragaccountProductTypeEditText.setEnabled(false);
        fragaccountTypeAccountEditText.setEnabled(false);
        fragaccountNameCustomersEditText.setEnabled(false);
        fragaccountTypeIdentityEditText.setEnabled(false);
        fragaccountIdentityNumberEditText.setEnabled(false);
        fragaccountSourceFundsEditText.setEnabled(false);
        fragaccountOpenAccountEditText.setEnabled(false);
        fragaccountTypeInitialDepositEditText.setEnabled(false);

    }

    private void openEditText() {
        fragaccountTypeCustomersEditText.setEnabled(true);
        fragaccountProductTypeEditText.setEnabled(true);
        fragaccountTypeAccountEditText.setEnabled(true);
        fragaccountNameCustomersEditText.setEnabled(true);
        fragaccountTypeIdentityEditText.setEnabled(true);
        fragaccountIdentityNumberEditText.setEnabled(true);
        fragaccountSourceFundsEditText.setEnabled(true);
        fragaccountOpenAccountEditText.setEnabled(true);
        fragaccountTypeInitialDepositEditText.setEnabled(true);
    }


    private void print() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================");
                printer.printText("\nPembukaan Rekening");
                printer.printText("\n===============================");
                printer.printText("\nNo Referensi : \n" + AppConstant.NO_REFRENCE);
                printer.printText("\nNama Nasabah : \n" + fragaccountNameCustomersEditText.getText().toString());
                printer.printText("\nSetoran Awal : \n" + fragaccountTypeInitialDepositEditText.getText().toString());
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(4);
                printer.commitTransaction();
            }
        });
    }

    private void clearEditText() {
        fragaccountTypeCustomersEditText.setText(null);
        fragaccountProductTypeEditText.setText(null);
        fragaccountTypeAccountEditText.setText(null);
        fragaccountNameCustomersEditText.setText(null);
        fragaccountTypeIdentityEditText.setText(null);
        fragaccountIdentityNumberEditText.setText(null);
        fragaccountSourceFundsEditText.setText(null);
        fragaccountOpenAccountEditText.setText(null);
        fragaccountTypeInitialDepositEditText.setText(null);
        fragaccountProcessButton.setEnabled(false);
        fragaccountDataMatchesCheckBox.setChecked(false);
    }
}
