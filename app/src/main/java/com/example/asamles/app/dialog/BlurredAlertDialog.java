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

public class BlurredAlertDialog extends DialogFragment implements BlurTask.BlurTaskListener {
    private String title;
    private String message;
    private BlurredAlertDialogListener listener;
    private ImageView background;
    private Bitmap map;

    public static BlurredAlertDialog newInstance(String title, String message) {
        BlurredAlertDialog fragment = new BlurredAlertDialog();
        Bundle args = new Bundle();
        args.putString(Constants.TITLE, title);
        args.putString(Constants.MESSAGE, message);
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

    public void setBlurredAlertDialogListener(BlurredAlertDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        title = getArguments() != null ? getArguments().getString(Constants.TITLE) : null;
        message = getArguments() != null ? getArguments().getString(Constants.MESSAGE) : null;
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

    private void blur(Bitmap bkg, ImageView view) {
        BlurTask task = new BlurTask(bkg, view, this);
        task.execute();
    }

    @Override
    public void onBlurTaskComplete(Bitmap result) {
        if (result != null) {
//            result = doBrightness(result, -60);
            background.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, map.getWidth(), map.getHeight(), false)));
        } else {
            ADialogs.alert(getActivity(), getActivity().getString(R.string.error));
        }
    }

    public interface BlurredAlertDialogListener {
        public void onBlurredAlertDialogPositiveClick(DialogFragment dialog);

        public void onBlurredAlertDialogNegativeClick(DialogFragment dialog);

        public void onBlurredAlertDialogCancel(DialogFragment dialog);
    }

//    public static Bitmap doBrightness(Bitmap src, int value) {
//        // image size
//        int width = src.getWidth();
//        int height = src.getHeight();
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//        // color information
//        int A, R, G, B;
//        int pixel;
//
//        // scan through all pixels
//        for(int x = 0; x < width; ++x) {
//            for(int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                A = Color.alpha(pixel);
//                R = Color.red(pixel);
//                G = Color.green(pixel);
//                B = Color.blue(pixel);
//
//                // increase/decrease each channel
//                R += value;
//                if(R > 255) { R = 255; }
//                else if(R < 0) { R = 0; }
//
//                G += value;
//                if(G > 255) { G = 255; }
//                else if(G < 0) { G = 0; }
//
//                B += value;
//                if(B > 255) { B = 255; }
//                else if(B < 0) { B = 0; }
//
//                // apply new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
//
//        // return final image
//        return bmOut;
//    }
}