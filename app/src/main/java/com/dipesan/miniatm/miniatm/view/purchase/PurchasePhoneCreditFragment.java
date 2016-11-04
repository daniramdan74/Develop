package com.dipesan.miniatm.miniatm.view.purchase;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.SQLiteDatabase.DatabaseHelper;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.utils.AppUtil.showDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasePhoneCreditFragment extends Fragment {
    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

    private final CharSequence[] itemProviders = {"Telkomsel", "Indosat", "XL/Axis", "3 (TRI)", "Flexi", "Esia", "Smartfren"};
    private final CharSequence[] itemsNominal = {"30.000", "50.000", "100.000", "150.000", "200.000", "300.000", "500.000"};
    private final int[] itemPrices = {30000, 50000, 100000, 150000, 200000, 300000, 500000};
    @BindView(R.id.fragment_phone_credit_provider_edit_text) EditText fragmentPhoneCreditProviderEditText;
    @BindView(R.id.fragment_phone_credit_process_button) Button fragmentPhoneCreditProcessButton;
    @BindView(R.id.fragment_phone_credit_nominal_edit_text) EditText fragmentPhoneCreditNominalEditText;
    @BindView(R.id.fragment_phone_number_edit_text) EditText fragmentPhoneNumberEditText;
    @BindView(R.id.fragphone_text_input_layout_credit_provider) TextInputLayout fragphoneTextInputLayoutCreditProvider;
    @BindView(R.id.fragphone_text_input_layout_nominal) TextInputLayout fragphoneTextInputLayoutNominal;
    @BindView(R.id.fragphone_text_input_layout_phone_number) TextInputLayout fragphoneTextInputLayoutPhoneNumber;

    private int whichItemProvider = 0;
    private int whichItemNominal = 0;


    public PurchasePhoneCreditFragment() {
        // Required empty public constructor
    }

    public static PurchasePhoneCreditFragment newIstance() {
        return new PurchasePhoneCreditFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_phone_credit, container, false);
        ButterKnife.bind(this, view);

        fragmentPhoneCreditProviderEditText.setInputType(InputType.TYPE_NULL);
        fragmentPhoneCreditNominalEditText.setInputType(InputType.TYPE_NULL);
        hideKeyboard(view);


        return view;
    }

    @OnClick({R.id.fragment_phone_credit_provider_edit_text, R.id.fragment_phone_credit_nominal_edit_text, R.id.fragment_phone_credit_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_phone_credit_provider_edit_text:
                hideKeyboard(view);
                showProvider();
                break;
            case R.id.fragment_phone_credit_nominal_edit_text:
                hideKeyboard(view);
                showNominal();
                break;
            case R.id.fragment_phone_credit_process_button:
                showProcess();
                break;
        }
    }

    private void showProcess() {
        if (fragmentPhoneCreditProviderEditText.getText().toString().isEmpty()) {
            fragphoneTextInputLayoutCreditProvider.setError("Provider Masih Kosong");
            return;
        }
        else {
            fragphoneTextInputLayoutCreditProvider.setErrorEnabled(false);
        }
        if (fragmentPhoneCreditNominalEditText.getText().toString().isEmpty()){
            fragphoneTextInputLayoutNominal.setError("Nominal Masih Kosong");
            return;
        }else {
            fragphoneTextInputLayoutNominal.setErrorEnabled(false);
        }
        if (fragmentPhoneNumberEditText.getText().toString().isEmpty()){
            fragphoneTextInputLayoutPhoneNumber.setError("No Telepon Kosong");
            return;
        }else if (fragmentPhoneNumberEditText.getText().toString().length()<9){
            fragphoneTextInputLayoutPhoneNumber.setError("No Telepon Anda Kurang");
        }else {
            fragphoneTextInputLayoutPhoneNumber.setErrorEnabled(false);
        }
        if (fragmentPhoneCreditProviderEditText.getText().toString().length() > 0 &&
                fragmentPhoneCreditNominalEditText.getText().toString().length() >0 &&
                fragmentPhoneNumberEditText.getText().toString().length()>=9){
            String message = String.format("Pembelian pulsa %s ke %s sebesar %s telah berhasil",
                    itemProviders[whichItemProvider],
                    fragmentPhoneNumberEditText.getText().toString(),
                    itemsNominal[whichItemNominal]);
            showDialog(getActivity(), message);
            //showAlert();
            fragmentPhoneCreditProviderEditText.setText(null);
                    fragmentPhoneCreditNominalEditText.setText(null);
            fragmentPhoneNumberEditText.setText(null);
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pulsa");
        String message = String.format("Pembelian pulsa %s \nke \n%s \nsebesar Rp.%s",
                    itemProviders[whichItemProvider],
                    fragmentPhoneNumberEditText.getText().toString(),
                    itemsNominal[whichItemNominal]);
        builder.setMessage(message);
        builder.setNegativeButton("BATAL",null);
        builder.setPositiveButton("BELI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addData();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void addData() {
        HashMap<String, String>queryValues = new HashMap<String, String>();

        queryValues.put("providerName", fragmentPhoneCreditProviderEditText.getText().toString());
        queryValues.put("nominal", fragmentPhoneCreditProcessButton.getText().toString());
        queryValues.put("telephoneNumber", fragmentPhoneNumberEditText.getText().toString());
        databaseHelper.addPhoneCredit(queryValues);

    }

    private void showNominal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                //               .setTitle("Pilih Provider")
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Nominal Pulsa</font>"));
        builder.setSingleChoiceItems(itemsNominal, whichItemNominal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemNominal = which;
                fragmentPhoneCreditNominalEditText.setText(itemsNominal[which]);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    private void showProvider() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                //               .setTitle("Pilih Provider")
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Pilih Provider</font>"));
        builder.setSingleChoiceItems(itemProviders, whichItemProvider, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemProvider = which;
                fragmentPhoneCreditProviderEditText.setText(itemProviders[which]);
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
