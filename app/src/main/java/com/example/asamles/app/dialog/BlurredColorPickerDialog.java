package com.example.asamles.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

public class BlurredColorPickerDialog extends DialogFragment {
    private String title;
    private BlurredColorPickerDialogListener listener;
    private ImageView background;
    private int color;

    public interface BlurredColorPickerDialogListener {
        public void onBlurredAlertDialogPositiveClick(DialogFragment dialog, int color);
        public void onBlurredAlertDialogNegativeClick(DialogFragment dialog);
        public void onBlurredAlertDialogCancel(DialogFragment dialog);
    }

    public static BlurredColorPickerDialog newInstance(String title, int color) {
        BlurredColorPickerDialog fragment = new BlurredColorPickerDialog();
        Bundle args = new Bundle();
        args.putInt(Constants.COLOR, color);
        args.putString(Constants.TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        title = getArguments() != null ? getArguments().getString(Constants.TITLE) : null;
        color = getArguments() != null ? getArguments().getInt(Constants.COLOR) : 0;
        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
        background = (ImageView) rootView.findViewById(R.id.image);

        BlurBackground blurred = new BlurBackground(getActivity(), background);
        blurred.setBlurredBackground();

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setTitle(title);
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        View colorPickerLayout = dialogInflater.inflate(R.layout.dialog_colorpicker, null);
        adb.setView(colorPickerLayout);
        final ColorPicker picker = (ColorPicker) colorPickerLayout.findViewById(R.id.picker);
        SVBar svBar = (SVBar) colorPickerLayout.findViewById(R.id.svbar);

        picker.addSVBar(svBar);
        picker.setOldCenterColor(color);
        picker.setColor(color);
        adb.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onBlurredAlertDialogPositiveClick(BlurredColorPickerDialog.this, picker.getColor());
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onBlurredAlertDialogNegativeClick(BlurredColorPickerDialog.this);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        listener.onBlurredAlertDialogCancel(BlurredColorPickerDialog.this);
                    }
                });

        Dialog dialog = adb.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        return rootView;
    }

    public void setBlurredColorPickerDialogListener(BlurredColorPickerDialogListener listener) {
        this.listener = listener;
    }
}