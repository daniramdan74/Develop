package com.dipesan.miniatm.miniatm.view.balance;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;
import com.sunmi.util.ThreadPoolManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {
    private static final String TAG = "BalanceFragment";
    @BindView(R.id.balance_text_view) TextView balanceTextView;
    private YoucubeService youcubeService;
    private V1Printer v1Printer;
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


    public BalanceFragment() {
        // Required empty public constructor
    }

    public static BalanceFragment newInstance() {
        return new BalanceFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.layout_balance);
        linear.setVisibility(View.INVISIBLE);
        showAlert();
        v1Printer = new V1Printer(getActivity());
        v1Printer.setCallback(iCallback);
        ButterKnife.bind(this, view);
        return view;
    }

    private void showAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.enter_pin_edit_text);
        builder.setIcon(R.drawable.ic_settings)
                .setTitle("PIN")
                .setMessage("Enter Your PIN : ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LinearLayout linear = (LinearLayout) getActivity().findViewById(R.id.layout_balance);
                linear.setVisibility(View.VISIBLE);
                printBalance();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void printBalance() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {

                v1Printer.beginTransaction();
                v1Printer.printerInit();

                v1Printer.setFontSize(24);
                v1Printer.printText("Merchant Qubepay\n");
                v1Printer.printText("===============================\n");
                v1Printer.printText("Saldo anda " +balanceTextView.getText().toString() + "\n");
                v1Printer.printText("===============================\n");
                v1Printer.lineWrap(4);
                v1Printer.commitTransaction();
                Toast.makeText(getActivity(),"Saldo Anda : "+balanceTextView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
