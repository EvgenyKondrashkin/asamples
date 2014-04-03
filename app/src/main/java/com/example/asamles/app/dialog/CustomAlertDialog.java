package com.example.asamles.app.dialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.asamles.app.R;
import com.example.asamles.app.imageedit.blur.BlurTask;

public class CustomAlertDialog extends DialogFragment implements BlurTask.BlurTaskListener {
    /** The system calls this to get the DialogFragment's layout, regardless
        of whether it's being displayed as a dialog or an embedded fragment. */
    ImageView background;
    Bitmap cs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
		View rootView = inflater.inflate(R.layout.alert_custom_dialog, container, false);
		
		background = (ImageView) rootView.findViewById(R.id.image);
		
		container.setDrawingCacheEnabled(true);
        container.buildDrawingCache(true);
        cs = Bitmap.createBitmap(container.getDrawingCache());
        blur(cs, background);
		background.setImageBitmap(cs);
        container.setDrawingCacheEnabled(false);
		
        return rootView;
    }
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
    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Custom Dialog");
        return dialog;
    }
}