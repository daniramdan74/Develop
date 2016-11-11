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
import com.dipesan.miniatm.miniatm.utils.print.ThreadPoolManager;
import com.sunmi.controller.ICallback;
import com.sunmi.impl.V1Printer;
import com.youTransactor.uCube.ITaskMonitor;
import com.youTransactor.uCube.TaskEvent;
import com.youTransactor.uCube.rpc.command.SimplifiedOnlinePINCommand;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {
    private static final String TAG = "BalanceFragment";
    @BindView(R.id.balance_text_view) TextView balanceTextView;

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
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.layout_balance);
        linear.setVisibility(View.INVISIBLE);
        showAlert();
        printer = new V1Printer(getActivity());
        printer.setCallback(iCallback);
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

                printer.beginTransaction();

                printer.printerInit();

                printer.setFontSize(24);
                printer.printText("支付宝\n");
                printer.printText("Alipay\n");
                printer.printText("===============================\n");
                printer.printText("订单金额                 0.01元\n");
                printer.printText("    参与优惠金额        0.01元\n");
                printer.printText("商家实收\n");
                printer.printText("------------------------------- \n");
                printer.printText("开票金额(用户实付)   0.01元\n");
                printer.printText("--------------------------------\n");
                printer.printText("交易号:\n");
                printer.printText("2016040621001004150224503623\n\n");
                printer.printText("门店名称  正新鸡排(欢乐谷二店)\n");
                printer.printText("买家帐号         1id***@21cn.com\n");
                printer.printText("--------------------------------\n");
                printer.printText("日期           2016-04-06 11:29\n");
                printer.printText("--------------------------------\n");
                printer.printText("此小票不需要用户签字\n");
                printer.setFontSize(32);
                printer.printText("http://www.sunmi.com\n");
                printer.printOriginalText("http://www.sunmi.com\n");
                printer.setFontSize(24);
                printer.printText("http://www.sunmi.com\n");
                printer.printOriginalText("http://www.sunmi.com\n");
                printer.lineWrap(6);

                printer.commitTransaction();

                //重复打印
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
    }

    private void enterPin() {
        final SimplifiedOnlinePINCommand cmd = new SimplifiedOnlinePINCommand();
        cmd.setPINRequestLabel("ENTER YOUR PIN :");
        cmd.setWaitLabel("Mohon Tunggu...");
        cmd.execute(new ITaskMonitor() {
            @Override
            public void handleEvent(TaskEvent event, Object... params) {
                switch (event) {
                    case FAILED:
                        Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
//                        printBalance();
                        break;
                }
            }
        });
    }


}
