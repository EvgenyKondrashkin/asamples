package com.example.asamles.app.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.asamles.app.R;
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
            ADialogs alertDialog = new ADialogs(activity);
            alertDialog.alert(true, activity.getString(R.string.error), activity.getString(R.string.blur_background_error), activity.getString(R.string.ok), null);
        }
    }


}