package com.example.asamles.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.example.asamles.app.imageedit.blur.FastBlur;

public class CustomAlertDialog extends DialogFragment implements BlurTask.BlurTaskListener, DialogInterface.OnClickListener {
    /** The system calls this to get the DialogFragment's layout, regardless
        of whether it's being displayed as a dialog or an embedded fragment. */
    ImageView background;
    Bitmap cs;
//    private ViewGroup container;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        // Inflate the layout to use as dialog or embedded fragment
//		this.container = container;
//        View rootView = inflater.inflate(R.layout.alert_custom_dialog, container, false);
//
//		background = (ImageView) rootView.findViewById(R.id.image);
//
//		container.setDrawingCacheEnabled(true);
//        container.buildDrawingCache(true);
//        cs = Bitmap.createBitmap(container.getDrawingCache());
//        blur(cs, background);
//		background.setImageBitmap(cs);
//        container.setDrawingCacheEnabled(false);
//
//        return rootView;
//    }
    private void blur(Bitmap bkg, ImageView view) {
        BlurTask task = new BlurTask(bkg, view, this);
        task.execute();
    }
    public void onBlurTaskComplete(Bitmap result) {
        if (result != null) {
            // ImageView bluredImageView = new ImageView(getActivity());
            // bluredImageView.setLayoutParams(imageView.getLayoutParams());
            // bluredImageView.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, bitmap.getWidth(), bitmap.getHeight(), false)));
            // bluredImageView.setTag("Blured");
            // frameLayout.addView(bluredImageView);
            background.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, cs.getWidth(), cs.getHeight(), false)));
        } else {
            ADialogs.alert(getActivity(), getActivity().getString(R.string.error));
        }
    }
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
//                .setTitle("Title!")
//                .setPositiveButton("yes", this)
//                .setNegativeButton("no", this)
//                .setNeutralButton("maybe", this)
//                .setMessage("wat");
//        return adb.create();
//    }
    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setTitle("Custom Dialog");
//        return dialog;
        final SeekBar seekBar;

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View seekLayout = inflater.inflate(R.layout.slider_item, null);


        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.slider_item);
        // Set dialog title
        dialog.setTitle("Custom Dialog");
        Bitmap map=takeScreenShot(getActivity());

        Bitmap fast= FastBlur.doBlur(map, 10, true);
        final Drawable draw=new BitmapDrawable(getActivity().getResources(),fast);
        dialog.getWindow().setBackgroundDrawable(draw);


        // set values for custom dialog components - text, image and button
        seekBar = (SeekBar) seekLayout.findViewById(R.id.seekBar);
        seekBar.setProgress(100);

        dialog.setCancelable(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });

        return dialog;
    }
    private static Bitmap takeScreenShot(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
        view.setDrawingCacheEnabled(false);
        return b;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}