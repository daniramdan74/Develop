package com.dipesan.miniatm.miniatm.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dipesan.miniatm.miniatm.utils.AppConstant;

/**
 * Created by Dani Ramdan on 26/10/2016.
 */

public class UIUtils {
    private static ProgressDialog progressDialog;

    private UIUtils() {
    }

    public static AlertDialog showOptionDialog(Context context, String text, String yesLabel, String noLabel, final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        dialogBuilder.setMessage(text);
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton(yesLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (listener != null) {
                    listener.onClick(dialog, which);
                }
            }
        });

        dialogBuilder.setNegativeButton(noLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (listener != null) {
                    listener.onClick(dialog, which);
                }
            }
        });

        AlertDialog dialog = dialogBuilder.create();

        dialog.show();

        return dialog;
    }

    public static ProgressDialog showProgressDialog(Context context) {
        return showProgress(context, null);
    }

    public static ProgressDialog showProgress(Context context, String message) {
        return showProgress(context, message, false);
    }

    public static ProgressDialog showProgress(Context context, String message, boolean cancellable) {
        return showProgress(context, message, cancellable, null);
    }

    public static ProgressDialog showProgress(Context context, String message, boolean cancellable, DialogInterface.OnCancelListener onCancel) {
        ProgressDialog dlg = new ProgressDialog(context);

        dlg.setMessage(message);
        dlg.setCancelable(cancellable);

        if (onCancel != null) {
            dlg.setOnCancelListener(onCancel);
        }

        hideProgressDialog();

        progressDialog = dlg;

        dlg.show();

        return dlg;
    }

    public static void setProgressMessage(String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
        }
    }

    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static AlertDialog showMessageDialog(final Context context, String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        dialogBuilder.setMessage(msg);
        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = dialogBuilder.create();

        dialog.show();

        return dialog;
    }

    public static void showUserInputDialog(final Context context, String prompt, final DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        final EditText userInput = new EditText(context);
        userInput.setId(AppConstant.USER_INPUT_FIELD_ID);

        dialogBuilder.setView(userInput);

        dialogBuilder.setTitle(prompt);

        dialogBuilder.setCancelable(false);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);

                if (okListener != null) {
                    okListener.onClick(dialog, which);
                }

                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        userInput.requestFocus();

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userInput, InputMethodManager.SHOW_IMPLICIT);
    }
}