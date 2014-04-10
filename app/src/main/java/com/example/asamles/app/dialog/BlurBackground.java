package com.example.asamles.app.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.asamles.app.imageedit.blur.BlurTask;

public class BlurBackground implements BlurTask.BlurTaskListener {

    private ImageView background;
    private Activity activity;
    private Bitmap map;

    public BlurBackground(Activity activity, ImageView background) {
        this.activity = activity;
        this.background = background;
    }

    public void setBlurredBackground() {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, statusBarHeight, 0, 0);
        background.setLayoutParams(params);

        map = takeScreenShot(activity);
        blur(map, background);
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

    private void blur(Bitmap bkg, ImageView view) {
        BlurTask task = new BlurTask(bkg, view, this);
        task.execute();
    }

    @Override
    public void onBlurTaskComplete(Bitmap result) {
        if (result != null) {
            background.setImageDrawable(new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(result, map.getWidth(), map.getHeight(), true)));
        } else {
            ADialogs.alert(activity, true, "Error", "Error while blurring background", "Ok", null);
        }
    }

    public Bitmap doBrightness(int value) {
        int width = map.getWidth();
        int height = map.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, map.getConfig());
        int A, R, G, B;
        int pixel;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = map.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                R += value;
                if (R > 255) {
                    R = 255;
                } else if (R < 0) {
                    R = 0;
                }

                G += value;
                if (G > 255) {
                    G = 255;
                } else if (G < 0) {
                    G = 0;
                }

                B += value;
                if (B > 255) {
                    B = 255;
                } else if (B < 0) {
                    B = 0;
                }

                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
}