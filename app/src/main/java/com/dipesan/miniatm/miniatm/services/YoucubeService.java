package com.dipesan.miniatm.miniatm.services;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.Activity.MainSubActivity;
import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.utils.ucube.CardReaderType;
import com.dipesan.miniatm.miniatm.utils.ucube.TransactionType;
import com.dipesan.miniatm.miniatm.view.payment.PaymentFragment;
import com.youTransactor.uCube.ITaskMonitor;
import com.youTransactor.uCube.LogManager;
import com.youTransactor.uCube.TaskEvent;
import com.youTransactor.uCube.payment.AbstractPaymentService;
import com.youTransactor.uCube.payment.Currency;
import com.youTransactor.uCube.payment.PaymentContext;
import com.youTransactor.uCube.payment.PaymentService;
import com.youTransactor.uCube.rpc.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_SETTINGS;

/**
 * Created by fachrifebrian on 10/18/16.
 */

public class YoucubeService {
    private Activity activity;
    private OnEnterCardListener listener;
    private String message = "";
    private double amount = 0;
    private ResourceBundle msgBundle;
    private boolean isMessage = false;

    public YoucubeService(Activity activity) {
        this.activity = activity;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setIsMessage(boolean isMessage) {
        this.isMessage = isMessage;
    }

    public void enterCard(final OnEnterCardListener onEnterCardListener) {
        this.listener = onEnterCardListener;

        Currency currency = new Currency(360, 2, "IDR");
        TransactionType trxType = TransactionType.DEBIT;

        final PaymentContext paymentContext = new PaymentContext();

        paymentContext.setMessage(isMessage ? message : String.format("%s %s %s", message, currency.getLabel(), amount));
        paymentContext.setAmount(amount);
        paymentContext.setCurrency(currency);
        paymentContext.setTransactionType(trxType.getCode());
        paymentContext.setPreferredLanguageList(Arrays.asList("en"));

        try {
            msgBundle = new PropertyResourceBundle(activity.getResources().openRawResource(R.raw.ucube_strings));
        } catch (Exception e) {
            LogManager.debug(PaymentFragment.class.getSimpleName(), "Unable to load uCube message bundle", e);
        }
        paymentContext.setMsgBundle(msgBundle);

        final List<CardReaderType> readerList = new ArrayList<>();

        readerList.add(CardReaderType.MSR);
        paymentContext.setRequestedSecuredTagList(new int[]{Constants.TAG_CARD_DATA_BLOCK});
        paymentContext.setRequestedPlainTagList(new int[]{Constants.TAG_MSR_BIN});
        paymentContext.setForceOnlinePIN(true);

        readerList.add(CardReaderType.ICC);
        List<byte[]> tagList = new ArrayList<>();
        tagList.add(new byte[]{(byte) 0x95}); /* TVR */
        tagList.add(new byte[]{(byte) 0x9B}); /* TSI */

        paymentContext.setRequestedAuthorizationTagList(tagList);

//        readerList.add(CardReaderType.NFC);

        if (readerList.isEmpty()) {
            Toast.makeText(activity, "No reader activated", Toast.LENGTH_LONG).show();
            return;
        }

        UIUtils.showProgress(activity, message);

        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] activatedReaders = new byte[readerList.size()];
                for (int i = 0; i < activatedReaders.length; i++) {
                    activatedReaders[i] = readerList.get(i).getCode();
                }

                final PaymentService svc = new PaymentService(paymentContext, activatedReaders);
                svc.setCardWaitTimeout(30); // 30 seconds timeout
                svc.setRiskManagementTask(new RiskManagementTask(activity));
                svc.setAuthorizationProcessor(new AuthorizationTask(activity));
                svc.execute(new ITaskMonitor() {
                    @Override
                    public void handleEvent(final TaskEvent event, final Object... params) {
                        final AbstractPaymentService svc = (AbstractPaymentService) params[0];

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (event) {
                                    case PROGRESS:
                                        switch (paymentContext.getPaymentStatus()) {
                                            case STARTED:
                                                UIUtils.setProgressMessage(message);
                                                break;
                                        }
                                        return;

                                    case SUCCESS:
                                        switch (paymentContext.getPaymentStatus()) {
                                            case APPROVED:
                                                listener.onApproved();
                                                break;

                                            case DECLINED:
                                                Toast.makeText(activity, "Transaction DECLINED", Toast.LENGTH_LONG).show();
                                                break;

                                            case CHIP_REQUIRED:
                                                Toast.makeText(activity, "MSR use forbidden.\nUse chip", Toast.LENGTH_LONG).show();
                                                break;

                                            case CANCELLED:
                                                Toast.makeText(activity, "Transaction Canceled", Toast.LENGTH_LONG).show();
                                                break;

                                            case UNSUPPORTED_CARD:
                                                Toast.makeText(activity, "Card Not Support", Toast.LENGTH_LONG).show();
                                                break;

                                            case REFUSED_CARD:
                                                Toast.makeText(activity, "Card Refused", Toast.LENGTH_LONG).show();
                                                break;

                                            default:
                                                Toast.makeText(activity, "Error occured", Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                        break;

                                    case FAILED:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setMessage("Make Sure the Youcube Device is Turned On and Registered");
                                        builder.setPositiveButton("Setting",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(activity, MainSubActivity.class);
                                                        intent.putExtra(MENU, MENU_SETTINGS);
                                                        activity.overridePendingTransition(0, R.anim.fade_out);
                                                        activity.startActivity(intent);
                                                    }
                                                });
                                        builder.setCancelable(false);
                                        builder.create().show();
                                        break;

                                    default:
                                        return;
                                }

                                UIUtils.hideProgressDialog();
                            }
                        });
                    }
                });
            }
        }).start();

    }


    public interface OnEnterCardListener {
        void onApproved();
    }
}
