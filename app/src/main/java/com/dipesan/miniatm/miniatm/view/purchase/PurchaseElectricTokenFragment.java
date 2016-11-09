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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseElectricTokenFragment extends Fragment {
    private final CharSequence[] itemsNominal = {"30.000", "50.000", "100.000", "150.000", "200.000", "300.000", "500.000"};
    @BindView(R.id.fragment_electric_nominal_edit_text) EditText fragmentElectricNominalEditText;
    @BindView(R.id.fragelectric_text_input_layout_nominal) TextInputLayout fragelectricTextInputLayoutNominal;
    @BindView(R.id.fragment_electric_meter_number_edit_text) EditText fragmentElectricMeterNumberEditText;
    @BindView(R.id.fragpelectric_text_input_layout_meter_number) TextInputLayout fragpelectricTextInputLayoutMeterNumber;
    @BindView(R.id.fragment_electric_process_button) Button fragmentElectricProcessButton;
    private int whichItemNominal = 0;

    public PurchaseElectricTokenFragment() {
        // Required empty public constructor
    }

    public static PurchaseElectricTokenFragment newIstance() {
        return new PurchaseElectricTokenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_electric_token, container, false);
        ButterKnife.bind(this, view);

        fragmentElectricNominalEditText.setInputType(InputType.TYPE_NULL);

        return view;
    }

    private void showNominal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                //               .setTitle("Pilih Provider")
                .setTitle(Html.fromHtml("<font color='#FF7F27'>Nominal</font>"));
        builder.setSingleChoiceItems(itemsNominal, whichItemNominal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemNominal = which;
                fragmentElectricNominalEditText.setText(itemsNominal[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    @OnClick({R.id.fragment_electric_nominal_edit_text, R.id.fragment_electric_meter_number_edit_text, R.id.fragment_electric_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_electric_nominal_edit_text:
                showNominal();
                break;
            case R.id.fragment_electric_meter_number_edit_text:
                break;
            case R.id.fragment_electric_process_button:
                showProcess();
                break;
        }
    }

    private void showProcess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Pembelian</font>"));
        String message = String.format("Dari Rekening : 1234 " +
                "\nPenyedia Jasa : PLN Prabayar" +
                "\nID PEL/No Meter : %s " +
                "\nNominal : %s " +
                "\nNo Meter : 12345678" +
                "\nNama : Ujang Saepuloh" +
                "\nTarif/Daya : R1/900" +
                "\nJumlah KWH : 30,20",
                fragmentElectricMeterNumberEditText.getText().toString(),
                itemsNominal[whichItemNominal],
                itemsNominal[whichItemNominal]);
        builder.setMessage(message);
        builder.setIcon(R.mipmap.ic_launcher);
        fragmentElectricMeterNumberEditText.setText(null);
        fragmentElectricNominalEditText.setText(null);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
