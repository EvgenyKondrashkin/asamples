package com.example.asamles.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.asamles.app.R;

public class BlurredCustomViewDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.blurred_dialog_fragment, container, false);
        ImageView background = (ImageView) rootView.findViewById(R.id.image);
        BlurBackground blurred = new BlurBackground(getActivity(), background);
        blurred.setBlurredBackground();
        return rootView;
    }
}