package com.dipesan.miniatm.miniatm.view.payment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dipesan.miniatm.miniatm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentElectricFragment extends Fragment {


    @BindView(R.id.fragpayment_electric_idcustomer_edit_text) EditText fragpaymentElectricIdcustomerEditText;
    @BindView(R.id.fragpayment_electric_idcustomer_text_input_layout) TextInputLayout fragpaymentElectricIdcustomerTextInputLayout;
    @BindView(R.id.fragpayment_electric_proses_button) Button fragpaymentElectricProsesButton;

    public PaymentElectricFragment() {
        // Required empty public constructor
    }

    public static PaymentElectricFragment newIstance() {
        return new PaymentElectricFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_electric, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragpayment_electric_proses_button)
    public void onClick() {
        showProcess();
    }

    private void showProcess() {
        String title = "Bayar Listrik";
        String message = String.format("Pembayaran Listrik, dari rekening : 1235674 penyedia jasa : Qubepay MiniATM ID PEL/No Meter: %s",
                fragpaymentElectricIdcustomerEditText.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("BAYAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();



    }
}
