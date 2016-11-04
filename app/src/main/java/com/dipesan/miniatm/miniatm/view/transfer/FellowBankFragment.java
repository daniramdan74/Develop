package com.dipesan.miniatm.miniatm.view.transfer;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import static com.dipesan.miniatm.miniatm.utils.AppUtil.showDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FellowBankFragment extends Fragment {
//    final CharSequence[] itemsBank = {
//            "002 BANK BRI","002 BANK MANDIRI","009 BANK BNI","011 BANK DANAMON","013 PERMATA BANK"
//            ,"014 BANK BCA","016 BANK BII","019 BANK PANIN","028 BANK NISP","037 BANK ARTHA GRAHA"
//            ,"058 BANK UOB INDONESIA","076 BANK BUMI ARTA","110 BANK JABAR","111 BANK DKI","200 BANK BTN"
//            ,"426 BANK MEGA","441 BANK BUKOPIN","451 BANK SYARIAH MANDIRI"
//    };

    final CharSequence[] itemsBank = {
            "BANK BRI","BANK MANDIRI","BANK BNI","BANK DANAMON","PERMATA BANK"
            ,"BANK BCA","BANK BII","BANK PANIN","BANK NISP","BANK ARTHA GRAHA"
            ,"BANK UOB INDONESIA","BANK BUMI ARTA","BANK JABAR","BANK DKI","BANK BTN"
            ,"BANK MEGA","BANK BUKOPIN","BANK SYARIAH MANDIRI"
    };




    @BindView(R.id.fragfellow_bank_destination_bank_edit_text) EditText fragfellowBankDestinationBankEditText;
    @BindView(R.id.fragfellow_bank_destination_bank_text_input_layout) TextInputLayout fragfellowBankDestinationBankTextInputLayout;
    @BindView(R.id.fragfellow_bank_destination_account_edit_text) EditText fragfellowBankDestinationAccountEditText;
    @BindView(R.id.fragfellow_bank_destination_account_text_input_layout) TextInputLayout fragfellowBankDestinationAccountTextInputLayout;
    @BindView(R.id.fragfellow_bank_amount_transfer_edit_text) EditText fragfellowBankAmountTransferEditText;
    @BindView(R.id.fragfellow_bank_amount_transfer_text_input_layout) TextInputLayout fragfellowBankAmountTransferTextInputLayout;
    @BindView(R.id.fragfellow_bank_process_button) Button fragfellowBankProcessButton;

    public FellowBankFragment() {
        // Required empty public constructor
    }

    public static FellowBankFragment newInstance() {
        FellowBankFragment fragment = new FellowBankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fellow_bank, container, false);
        ButterKnife.bind(this, view);
        fragfellowBankDestinationBankEditText.setInputType(InputType.TYPE_NULL);
        return view;
    }

    @OnClick({R.id.fragfellow_bank_destination_bank_edit_text, R.id.fragfellow_bank_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragfellow_bank_destination_bank_edit_text:
                showDestinationBank();
                break;
            case R.id.fragfellow_bank_process_button:
                showProcess();
                break;
        }
    }

    private void showProcess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Transfer Antar Bank");
                final String message = String.format("Bank Asal            : BRI \nNama Pengirim  : Dani Ramdan" +
                "\n\nBank Tujuan        : %s \nNo Rek                 : %s \nNama Penerima : Abdul Rizal \nJumlah Transfer : %s",
                fragfellowBankDestinationBankEditText.getText().toString(), fragfellowBankDestinationAccountEditText.getText().toString(),
                fragfellowBankAmountTransferEditText.getText().toString());
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("TRANSFER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                                     showDialog(getActivity(),"Berhasil Di Transfer\n\n"+message);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDestinationBank() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Daftar Bank");
        builder.setItems(itemsBank, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                fragfellowBankDestinationBankEditText.setText(itemsBank[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
