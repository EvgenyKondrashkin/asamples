package com.example.asamles.app.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.asamles.app.R;

import java.util.Calendar;

public class ADialogs {

    private static AlertDialog.Builder build(Context context, boolean cancelable, String title, String message) {
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
        return ad;
    }
    public static void openTime(Context context, final TextView label, boolean cancelable, String title, String positiveButton, String negativeButton) {
        final TimePicker tp;
        final CheckBox active;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View timeLayout = inflater.inflate(R.layout.dialog_time, null);
        tp = (TimePicker) timeLayout.findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        tp.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        active = (CheckBox) timeLayout.findViewById(R.id.active);
        AlertDialog.Builder ad = build(context, cancelable, title, null);
        ad.setView(timeLayout);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Integer hour = tp.getCurrentHour();
                    Integer minute = tp.getCurrentMinute();
                    boolean activeIt = active.isChecked();
                    label.setText("Active:" + activeIt + "; Time" + hour + ":" + minute);
                    dialog.cancel();
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }
        ad.create().show();
    }

    //@TODO: DialogsMain, BlurBackground, ImageEditMain, PicassoMain
    public static void alert(Context context, boolean cancelable, String title, String message, String positiveButton, String negativeButton) {
        final Activity activity = (Activity) context;
        AlertDialog.Builder ad = build(context, cancelable, title, message);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }
        ad.create().show();
    }

    public static void progress(Context context, boolean cancelable, String message) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(message);
        pd.setCancelable(cancelable);
        pd.setCanceledOnTouchOutside(cancelable);
        if (cancelable) {
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }
        pd.show();
    }

    //@TODO: DialogsMain, ImageEditMain
    public static void seekbar(Context context, boolean cancelable, String title, String positiveButton, String negativeButton) {
        final SeekBar seekBar;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View seekLayout = inflater.inflate(R.layout.dialog_seekbar, null);
        seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
        AlertDialog.Builder ad = build(context, cancelable, title, null);
        ad.setView(seekLayout);
        if (positiveButton != null) {
            ad.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // seekBar.getProgress()
                    dialog.cancel();
                }
            });
        }
        if (negativeButton != null) {
            ad.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        if (cancelable) {
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }
        ad.create().show();
    }
}
