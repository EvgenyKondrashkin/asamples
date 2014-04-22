package com.example.asamles.app.imageedit.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;

import com.example.asamles.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageEditor {

    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    private static Bitmap result;
    private static Bitmap bitmap;
    private static Bitmap frame;
    private static Canvas canvas;
    private static Paint paint = new Paint();
    private static Bitmap oldFrame;
    private static Bitmap bokehFrame;

    public ImageEditor(Bitmap bmp){
        result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        canvas = new Canvas(result);
    }
    private static Bitmap changeBitmapSeekbar(Bitmap bmp, ColorMatrix cm) {

        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return result;
    }
    private static Bitmap changeBitmap(Bitmap bmp, ColorMatrix cm) {
        result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        canvas = new Canvas(result);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return result;
    }
    public static Bitmap onBrightness(Bitmap bmp, float brightness) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        1, 0, 0, 0, brightness,
                        0, 1, 0, 0, brightness,
                        0, 0, 1, 0, brightness,
                        0, 0, 0, 1, 0
                });

        return changeBitmapSeekbar(bmp, cm);
    }

    public static Bitmap onContrast(Bitmap bmp, float contrast) {
        float scale = contrast;
        float translate = (-.5f * scale + .5f) * 255.f;
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        scale, 0, 0, 0, translate,
                        0, scale, 0, 0, translate,
                        0, 0, scale, 0, translate,
                        0, 0, 0, 1, 0
                });

        return changeBitmapSeekbar(bmp, cm);
    }

    public static Bitmap onSaturation(Bitmap bmp, float settingSat) {
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(settingSat);

        return changeBitmapSeekbar(bmp, cm);
    }
    public static Bitmap doAlpha(Bitmap bmp, float alpha) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, alpha, 0
                });

        return changeBitmap(bmp, cm);
    }

    public static Bitmap doRotate(Bitmap bmp, int direction) {
        int angle = direction * 90;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }
    public static Bitmap doFlip(Bitmap bmp, int type) {
        Matrix matrix = new Matrix();
        if (type == FLIP_VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        } else if (type == FLIP_HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return null;
        }
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public static Bitmap doGrayscale(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0.5f, 0.5f, 0.5f, 0, 0,
                        0.5f, 0.5f, 0.5f, 0, 0,
                        0.5f, 0.5f, 0.5f, 0, 0,
                        0, 0, 0, 1, 0
                });

        return changeBitmap(bmp, cm);
    }

    public static Bitmap doInvert(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        -1, 0, 0, 1, 1,
                        0, -1, 0, 1, 1,
                        0, 0, -1, 1, 1,
                        0, 0, 0, 1, 0,
                });

        return changeBitmap(bmp, cm);
    }

    public static Bitmap doSepia(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0.3588f, 0.7044f, 0.1368f, 0, 0,
                        0.2990f, 0.5870f, 0.1140f, 0, 0,
                        0.2392f, 0.4696f, 0.0912f, 0, 0,
                        0, 0, 0, 1, 0
                });

        return changeBitmap(bmp, cm);
    }

    public static Bitmap doSwap(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0, 0, 1, 0, 0,
                        0, 1, 0, 0, 0,
                        1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0,
                });

        return changeBitmap(bmp, cm);
    }

    public static Bitmap doPolaroid(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0, 1, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0,
                });

        return changeBitmap(bmp, cm);
    }

    public static Bitmap doBlackWhite(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        1.5f, 1.5f, 1.5f, 0, 0,
                        1.5f, 1.5f, 1.5f, 0, 0,
                        1.5f, 1.5f, 1.5f, 0, 0,
                        0, 0, 0, 1, 0,
                });

        return changeBitmap(bmp, cm);
    }
    public static void loadFrames(Context context){
        oldFrame = BitmapFactory.decodeResource(context.getResources(), R.drawable.old_frame);
        bokehFrame = BitmapFactory.decodeResource(context.getResources(), R.drawable.bokeh);
    }
    public static Bitmap doOldPhoto(Bitmap bmp, Context context) {
        bitmap = doSepia(bmp);

        frame = Bitmap.createScaledBitmap(oldFrame, bmp.getWidth(), bmp.getHeight(), true);
        frame = doAlpha(frame, 0.5f);

        Canvas canvasFrame = new Canvas(bitmap);
        canvasFrame.drawBitmap(bitmap, new Matrix(), null);
        canvasFrame.drawBitmap(frame, new Matrix(), null);
        return bitmap;
    }

    public static Bitmap doBokehPhoto(Bitmap bmp, Context context) {
        bitmap = bmp.copy(bmp.getConfig(), true);
        frame = Bitmap.createScaledBitmap(bokehFrame, bmp.getWidth(), bmp.getHeight(), true);
//        frame =  decodeSampledBitmapFromResource(context.getResources(), R.drawable.bokeh, bmp.getWidth(), bmp.getHeight());
        Canvas canvasFrame = new Canvas(bitmap);
        canvasFrame.drawBitmap(bitmap, new Matrix(), null);
        canvasFrame.drawBitmap(frame, new Matrix(), null);
        return bitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromUri(String uri,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(uri, options);
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }
}
