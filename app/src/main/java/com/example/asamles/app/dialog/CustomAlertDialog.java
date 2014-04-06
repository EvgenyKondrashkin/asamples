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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.example.asamles.app.imageedit.blur.FastBlur;

public class CustomAlertDialog extends DialogFragment implements  DialogInterface.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.alert_custom_dialog, container, false);
		ImageView background = (ImageView) rootView.findViewById(R.id.image);
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, statusBarHeight, 0, 0);
        background.setLayoutParams(params);
        Bitmap map=takeScreenShot(getActivity());
        Bitmap fast= FastBlur.doBlur(map, 10, true);
        final Drawable draw=new BitmapDrawable(getActivity().getResources(),fast);
        background.setImageDrawable(draw);
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setPositiveButton("yes", this)
                .setNegativeButton("no", this);
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        adb.setView(dialogInflater.inflate(R.layout.slider_item, null)).show();
        return rootView;
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
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}