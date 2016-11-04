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
public class PaymentTelevisionFragment extends Fragment {

    private final CharSequence[] itemProviders = {"Indovision", "First Media", "Telkom Vision", "Telkom Speedy"};
    @BindView(R.id.fragpayment_television_providers_edit_text) EditText fragpaymentTelevisionProvidersEditText;

    public PaymentTelevisionFragment() {
        // Required empty public constructor
    }


    public static PaymentTelevisionFragment newIstance() {
        return new PaymentTelevisionFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_television, container, false);
        ButterKnife.bind(this, view);
        fragpaymentTelevisionProvidersEditText.setInputType(InputType.TYPE_NULL);
        return view;
    }

    @OnClick({R.id.fragpayment_television_providers_edit_text, R.id.fragpayment_television_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_television_providers_edit_text:
                showProviders();
                break;
            case R.id.fragpayment_television_process_button:
                break;
        }
    }

    private void showProviders() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Penyedia Layanan");
        builder.setNegativeButton("BATAL", null);
        builder.setItems(itemProviders, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                fragpaymentTelevisionProvidersEditText.setText(itemProviders[items]);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
