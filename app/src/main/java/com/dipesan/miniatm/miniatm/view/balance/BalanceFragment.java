package com.dipesan.miniatm.miniatm.view.balance;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dipesan.miniatm.miniatm.Activity.LoginActivity;
import com.dipesan.miniatm.miniatm.Activity.MainActivity;
import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.dipesan.miniatm.miniatm.utils.AppConstant;
import com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {
    private static final String TAG = "BalanceFragment";
    @BindView(R.id.balance_text_view) TextView balanceTextView;
    @BindView(R.id.layout_balance) LinearLayout layoutBalance;

    private YoucubeService youcubeService;
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
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
        youcubeService = new YoucubeService(getActivity());
        ButterKnife.bind(this, view);
        layoutBalance.setVisibility(View.INVISIBLE);
        insertCard();

        return view;
    }
    private void insertCard() {
        youcubeService.setIsMessage(true);
        youcubeService.setMessage("Insert / Swipe Card");
        youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
            @Override
            public void onApproved() {
                showBalance();
            }
        });
    }

    private void showBalance() {
        layoutBalance.setVisibility(View.VISIBLE);
    }

    private void printBalance() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {

            @Override
            public void run() {
                printer.beginTransaction();
                printer.printerInit();
                printer.setFontSize(24);
                printer.printText("===============================\n");
                printer.printText("\n" + getString(R.string.balance));
                printer.printText("\n===============================\n");
                printer.printText("\n");
                printer.printText("\n" + getString(R.string.balanceYourBalance));
                printer.setFontSize(30);
                printer.printText("\n" + balanceTextView.getText().toString() + "\n");
                printer.printText("\n");
                printer.printText("\n");
                printer.printText("-------------------------\n");
                printer.printText("\nMerchant :");
                printer.printText("\n" + AppConstant.NAME_MERCHANT);
                printer.printText("\nID " + AppConstant.ID_MERCHANT);
                printer.lineWrap(6);
                printer.commitTransaction();
            }


//        ThreadPoolManager.getInstance().executeTask(new Runnable() {
//            @Override
//            public void run() {
//
//                v2Printer.beginTransaction();
//                v2Printer.printerInit();
//                v2Printer.setFontSize(24);
//                v2Printer.printText("Merchant Qubepay\n");
//                v2Printer.printText("===============================\n");
//                v2Printer.printText("Saldo anda " +balanceTextView.getText().toString() + "\n");
//                v2Printer.printText("===============================\n");
//                v2Printer.lineWrap(4);
//                v2Printer.commitTransaction();
//                Toast.makeText(getActivity(), "Saldo Anda : " +balanceTextView.getText().toString(), Toast.LENGTH_SHORT).
//                show();
//            }
        });
        showAlert();
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.continuetransaction))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().overridePendingTransition(0, R.anim.fade_out);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().overridePendingTransition(0, R.anim.fade_out);
                        getActivity().finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    @OnClick(R.id.fragment_balance_print_button)
    public void onClickPrint() {
        printBalance();
    }
}
