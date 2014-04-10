package com.example.asamles.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.asamles.app.R;

public class BlurredCustomAlertDialog extends DialogFragment {
    private BlurredCustomAlertDialogListener listener;
    public boolean set;

    public interface BlurredCustomAlertDialogListener {
        public void onBlurredCustomAlertDialogPositiveClick(DialogFragment dialog, boolean set);

        public void onBlurredCustomAlertDialogNegativeClick(DialogFragment dialog);

        public void onBlurredCustomAlertDialogCancel(DialogFragment dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
        ImageView background = (ImageView) rootView.findViewById(R.id.image);
        BlurBackground blurred = new BlurBackground(getActivity(), background);
        blurred.setBlurredBackground();

        return rootView;
    }

    Context context;

    public AlertDialog.Builder build(Context context, boolean cancelable, String title, String message, String positiveButton, String negativeButton) {
        this.context = context;
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        if (title != null) {
            ad.setTitle(title);
        }
        if (message != null) {
            ad.setMessage(message);
        }
        if (cancelable) {
            ad.setCancelable(cancelable);
        }
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    set = true;
                    listener.onBlurredCustomAlertDialogPositiveClick(BlurredCustomAlertDialog.this, set);
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    listener.onBlurredCustomAlertDialogNegativeClick(BlurredCustomAlertDialog.this);
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    listener.onBlurredCustomAlertDialogCancel(BlurredCustomAlertDialog.this);
                }
            });
        }
        return ad;
    }

    public View setCustomView(AlertDialog.Builder ad, int resId) {
        View customLayout = null;
        if ((ad != null) && (resId != 0)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customLayout = inflater.inflate(resId, null);
            ad.setView(customLayout);
        } else {
            ADialogs alertDialog = new ADialogs(context);
            alertDialog.alert(true, context.getString(R.string.error), context.getString(R.string.custom_view_dialog_error), context.getString(R.string.ok), null);
        }
        return customLayout;
    }

    public void setBlurredCustomAlertDialogListener(BlurredCustomAlertDialogListener listener) {
        this.listener = listener;
    }

    public void customShow(AlertDialog.Builder ad) {
        // set = false;
        set = true;
        if (ad != null) {
            Dialog dialog = ad.create();
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();
        } else {
            ADialogs alertDialog = new ADialogs(context);
            alertDialog.alert(true, context.getString(R.string.error), context.getString(R.string.custom_view_dialog_error), context.getString(R.string.ok), null);
        }
    }
}