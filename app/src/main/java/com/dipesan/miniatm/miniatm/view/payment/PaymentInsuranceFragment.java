package com.dipesan.miniatm.miniatm.view.payment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
public class PaymentInsuranceFragment extends Fragment {
    final CharSequence[] itemsInsurance = {
            "PT. Prudential Life Assurance", "PT. Asuransi Allianz Life Indonesia", "PT. AIA Financial"
            , "PT. Asuransi Jiwa SInarmas", "PT. Asuransi Jiwa Manulife Indonesia", "PT Avrist Assurance"
    };
    @BindView(R.id.fragpayment_insurance_provider_edit_text) EditText fragpaymentInsuranceProviderEditText;
    @BindView(R.id.fragpayment_insurance_process_button) Button fragpaymentInsuranceProcessButton;


    public PaymentInsuranceFragment() {
        // Required empty public constructor
    }

    public static PaymentInsuranceFragment newIstance() {
        return new PaymentInsuranceFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_insurance, container, false);
        ButterKnife.bind(this, view);

        fragpaymentInsuranceProviderEditText.setInputType(InputType.TYPE_NULL);
        return view;
    }

    @OnClick({R.id.fragpayment_insurance_provider_edit_text, R.id.fragpayment_insurance_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragpayment_insurance_provider_edit_text:
                showProvider();
                break;
            case R.id.fragpayment_insurance_process_button:
                break;
        }
    }

    private void showProvider() {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Penyedia Layanan");
        builder.setNegativeButton("Batal", null);
        builder.setItems(itemsInsurance, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int items) {
                fragpaymentInsuranceProviderEditText.setText(itemsInsurance[items]);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
