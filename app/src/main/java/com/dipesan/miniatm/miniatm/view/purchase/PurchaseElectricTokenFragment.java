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

import static com.dipesan.miniatm.miniatm.utils.AppUtil.showDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseElectricTokenFragment extends Fragment {
    private final CharSequence[] itemsNominal = {"30.000", "50.000", "100.000", "150.000", "200.000", "300.000", "500.000"};
    private int whichItemNominal = 0;

    @BindView(R.id.fragment_electric_nominal_edit_text) EditText fragmentElectricNominalEditText;
    @BindView(R.id.fragelectric_text_input_layout_nominal) TextInputLayout fragelectricTextInputLayoutNominal;
    @BindView(R.id.fragment_electric_meter_number_edit_text) EditText fragmentElectricMeterNumberEditText;
    @BindView(R.id.fragpelectric_text_input_layout_meter_number) TextInputLayout fragpelectricTextInputLayoutMeterNumber;
    @BindView(R.id.fragment_electric_process_button) Button fragmentElectricProcessButton;

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
        String message = String.format("Pembelian token listrik sebesar %s ke %s berhasil dilakukan",
                itemsNominal[whichItemNominal],
                fragmentElectricMeterNumberEditText.getText().toString(),
                itemsNominal[whichItemNominal]);
        showDialog(getActivity(), message);
        fragmentElectricMeterNumberEditText.setText(null);
        fragmentElectricNominalEditText.setText(null);

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
