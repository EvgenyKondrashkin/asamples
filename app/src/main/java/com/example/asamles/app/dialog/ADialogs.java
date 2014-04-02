package com.example.asamles.app.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.asamles.app.R;

import java.util.Calendar;

public class ADialogs {

    public static void openTime(Context context, final TextView label) {
        final TimePicker tp;
        final CheckBox active;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View timeLayout = inflater.inflate(R.layout.time_dialog, null);
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setMessage("Wat");
        ad.setView(timeLayout);
        ad.setCancelable(true);
        tp = (TimePicker) timeLayout.findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        tp.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        active = (CheckBox) timeLayout.findViewById(R.id.active);
        ad.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Integer hour = tp.getCurrentHour();
                Integer minute = tp.getCurrentMinute();
                boolean activeIt = active.isChecked();
                label.setText("Active:" + activeIt + "; Time" + hour + ":" + minute);
                dialog.cancel();
            }
        }).create();
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });
        ad.show();

    }

    public static void alert(Context context, String message) {
        final Activity activity = (Activity) context;
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setMessage(message);
        ad.setCancelable(true);
        ad.setPositiveButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.finish();
                dialog.cancel();
            }
        }).create().show();
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                activity.finish();
                dialog.cancel();
            }
        });
        ad.show();
    }

    public static void seekBar2(Context context, final ImageView imageView, final ViewGroup container) {
        final SeekBar seekBar;
        container.setDrawingCacheEnabled(true);
        container.buildDrawingCache(true);
        Bitmap cs = Bitmap.createBitmap(container.getDrawingCache());
        container.setDrawingCacheEnabled(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View seekLayout = inflater.inflate(R.layout.slider_item, null);
        seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
//        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ad.setMessage("Wat");
        ad.setView(seekLayout);
        ad.setCancelable(true);
        ad.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                imageView.setAlpha(seekBar.getProgress());

                dialog.cancel();
            }
        }).create();
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });
        ad.show();
    }

    public static void seekBar(Context context, final ImageView imageView, final ViewGroup container)) {
		final SeekBar seekBar;
		
        container.setDrawingCacheEnabled(true);
        container.buildDrawingCache(true);
        Bitmap cs = Bitmap.createBitmap(container.getDrawingCache());
        container.setDrawingCacheEnabled(false);
        
		// LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View seekLayout = inflater.inflate(R.layout.slider_item, null);
        
		
		final Dialog dialog = new Dialog(context, R.style.WatDialog);
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.setContentView(R.layout.slider_item);
		// Set dialog title
		dialog.setTitle("Custom Dialog");
		
		// set values for custom dialog components - text, image and button
		seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
		seekBar.setProgress(100);
		
		dialog.setCancelable(true);
        dialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                imageView.setAlpha(seekBar.getProgress());

                dialog.cancel();
            }
        }).create();
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });
		
		dialog.show();
                 
		// Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
        // if decline button is clicked, close the custom dialog
		// declineButton.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
				// Close dialog
				// dialog.dismiss();
				// }
        // });
		
	}
}
