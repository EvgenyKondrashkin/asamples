package com.example.asamles.app.dialog;

import android.app.ProgressDialog;
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

public class BlurredProgressDialog extends DialogFragment {
    private BlurredProgressDialogListener listener;
    private String message;
    private ImageView background;
    private Bitmap map;

    public interface BlurredProgressDialogListener {
        public void onBlurredProgressDialogCancel(DialogFragment dialog);
    }

    public static BlurredProgressDialog newInstance(String message) {
        BlurredProgressDialog fragment = new BlurredProgressDialog();
        Bundle args = new Bundle();
        args.putString(Constants.MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        message = getArguments() != null ? getArguments().getString(Constants.MESSAGE) : null;
        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
        background = (ImageView) rootView.findViewById(R.id.image);

        BlurBackground blurred = new BlurBackground(getActivity(), background);
        blurred.setBlurredBackground();

        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(message);
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(true);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                listener.onBlurredProgressDialogCancel(BlurredProgressDialog.this);
            }
        });
        pd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pd.show();
        return rootView;
    }

    public void setBlurredProgressDialogListener(BlurredProgressDialogListener listener) {
        this.listener = listener;
    }
}