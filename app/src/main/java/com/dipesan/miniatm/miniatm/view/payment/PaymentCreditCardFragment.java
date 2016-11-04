package com.dipesan.miniatm.miniatm.view.payment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dipesan.miniatm.miniatm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentCreditCardFragment extends Fragment {
    final CharSequence[] itemsBank = {"BNI", "ANZ Indonesia", "BRI",
            "Bukopin", "Citibank", "CIMB NIAGA", "Danamon", "HSBC",
            "Bank Mega", "Bank Permata", "Standard Chartered Bank", "Panin Bank"};
    @BindView(R.id.fragpayment_credit_card_providers_edit_text) EditText fragpaymentCreditCardProvidersEditText;

    public PaymentCreditCardFragment() {
        // Required empty public constructor
    }

    public static PaymentCreditCardFragment newIstance() {
        return new PaymentCreditCardFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_credit_card, container, false);
        ButterKnife.bind(this, view);
        fragpaymentCreditCardProvidersEditText.setInputType(InputType.TYPE_NULL);
        return view;
    }

    @OnClick({R.id.fragpayment_credit_card_providers_edit_text, R.id.fragpayment_credit_card_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_credit_card_providers_edit_text:
                showProviders();
                break;
            case R.id.fragpayment_credit_card_process_button:
                break;
        }
    }

    private void showProviders() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Penyedia Layanan");
        builder.setItems(itemsBank, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                fragpaymentCreditCardProvidersEditText.setText(itemsBank[items]);
            }
        });
        builder.setNegativeButton("Batal", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
