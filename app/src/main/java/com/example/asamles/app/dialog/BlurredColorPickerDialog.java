package com.example.asamles.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

public class BlurredColorPickerDialog extends DialogFragment implements BlurTask.BlurTaskListener {
    private String title;
    private BlurredColorPickerDialogListener listener;
    private ImageView background;
    private Bitmap map;
    private int color;

    public static BlurredColorPickerDialog newInstance(String title,int color) {
        BlurredColorPickerDialog fragment = new BlurredColorPickerDialog();
        Bundle args = new Bundle();
        args.putInt(Constants.COLOR, color);
        args.putString(Constants.TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.setDrawingCacheEnabled(false);
        return b;
    }

    public void setBlurredColorPickerDialogListener(BlurredColorPickerDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        title = getArguments() != null ? getArguments().getString(Constants.TITLE) : null;
        color = getArguments() != null ? getArguments().getInt(Constants.COLOR) : 0;
        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
        background = (ImageView) rootView.findViewById(R.id.image);

        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, statusBarHeight, 0, 0);

        background.setLayoutParams(params);

        map = takeScreenShot(getActivity());
        blur(map, background);

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setTitle(title);
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        View colorPickerLayout = dialogInflater.inflate(R.layout.dialog_colorpicker, null);
        adb.setView(colorPickerLayout);
        final ColorPicker picker = (ColorPicker) colorPickerLayout.findViewById(R.id.picker);
        SVBar svBar = (SVBar) colorPickerLayout.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) colorPickerLayout.findViewById(R.id.opacitybar);
        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
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

    private void blur(Bitmap bkg, ImageView view) {
        BlurTask task = new BlurTask(bkg, view, this);
        task.execute();
    }

    @Override
    public void onBlurTaskComplete(Bitmap result) {
        if (result != null) {
            background.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, map.getWidth(), map.getHeight(), false)));
        } else {
            ADialogs.alert(getActivity(), getActivity().getString(R.string.error));
        }
    }

    public interface BlurredColorPickerDialogListener {
        public void onBlurredAlertDialogPositiveClick(DialogFragment dialog, int color);

        public void onBlurredAlertDialogNegativeClick(DialogFragment dialog);

        public void onBlurredAlertDialogCancel(DialogFragment dialog);
    }

}