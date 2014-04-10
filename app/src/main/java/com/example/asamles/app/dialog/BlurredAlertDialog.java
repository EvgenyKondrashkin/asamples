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

public class BlurredAlertDialog extends DialogFragment {
    private String title;
    private String message;
    private BlurredAlertDialogListener listener;
    private ImageView background;
    private Bitmap map;

    public interface BlurredAlertDialogListener {
        public void onBlurredAlertDialogPositiveClick(DialogFragment dialog);

        public void onBlurredAlertDialogNegativeClick(DialogFragment dialog);

        public void onBlurredAlertDialogCancel(DialogFragment dialog);
    }

    public static BlurredAlertDialog newInstance(String title, String message) {
        BlurredAlertDialog fragment = new BlurredAlertDialog();
        Bundle args = new Bundle();
        args.putString(Constants.TITLE, title);
        args.putString(Constants.MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        title = getArguments() != null ? getArguments().getString(Constants.TITLE) : null;
        message = getArguments() != null ? getArguments().getString(Constants.MESSAGE) : null;
        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
        background = (ImageView) rootView.findViewById(R.id.image);

        BlurBackground blurred = new BlurBackground(getActivity(), background);
        blurred.setBlurredBackground();

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onBlurredAlertDialogPositiveClick(BlurredAlertDialog.this);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onBlurredAlertDialogNegativeClick(BlurredAlertDialog.this);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        listener.onBlurredAlertDialogCancel(BlurredAlertDialog.this);
                    }
                });
        Dialog dialog = adb.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        dialog.show();
        return rootView;
    }

    public void setBlurredAlertDialogListener(BlurredAlertDialogListener listener) {
        this.listener = listener;
    }
}