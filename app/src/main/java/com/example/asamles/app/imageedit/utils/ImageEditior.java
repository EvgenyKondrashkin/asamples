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

import com.example.asamles.app.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageEditior {

    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    public static final double PI = 3.14159d;
    public static final double HALF_CIRCLE_DEGREE = 180d;
    public static final double RANGE = 256d;
    private static Bitmap result;
    private static Bitmap bitmap;
    private static Bitmap frame;
    private static Canvas canvas;
    private static Paint paint = new Paint();

    public ImageEditior(Bitmap bmp){
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

    public static Bitmap doAlpha(Bitmap bmp, int alpha) {
        result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint transparentpaint = new Paint();
        transparentpaint.setAlpha(alpha); // 0 - 255
        canvas.drawBitmap(bmp, 0, 0, transparentpaint);

        return result;
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

    public static Bitmap doOldPhoto(Bitmap bmp, Context context) {
        bitmap = doSepia(bmp);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        frame =  decodeSampledBitmapFromResource(context.getResources(), R.drawable.old_frame, w, h);
//        frame = imageLoader.loadImageSync("drawable://"+R.drawable.old_frame);
//        frame = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.old_frame), bmp.getWidth(), bmp.getHeight(), true);
        frame = doAlpha(frame, 127);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(frame, new Matrix(), null);
        return bitmap;
    }

//    static ImageLoaderConfiguration config;
//    static ImageLoader imageLoader;
    public static Bitmap doBokehPhoto(Bitmap bmp, Context context) {
        bitmap = onSaturation(bmp, 2);
//         config = new ImageLoaderConfiguration.Builder(context)
//                .memoryCacheExtraOptions(bmp.getWidth(), bmp.getHeight())
//                .build();
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(config);
//        frame = imageLoader.loadImageSync("drawable://"+R.drawable.bokeh);
//        int w = bmp.getWidth();
//        int h = bmp.getHeight();
//        frame = getResizedBitmap(bmp, )
//        frame = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bokeh), bmp.getWidth(), bmp.getHeight(), true);
        frame =  decodeSampledBitmapFromResource(context.getResources(), R.drawable.bokeh, bmp.getWidth(), bmp.getHeight());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(frame, new Matrix(), null);
        return bitmap;
    }

    public static Bitmap doTint(Bitmap src, int degree) {

        int width = src.getWidth();
        int height = src.getHeight();

        int[] pix = new int[width * height];
        src.getPixels(pix, 0, width, 0, 0, width, height);

        int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
        double angle = (PI * (double) degree) / HALF_CIRCLE_DEGREE;

        int S = (int) (RANGE * Math.sin(angle));
        int C = (int) (RANGE * Math.cos(angle));

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;
                RY = (70 * r - 59 * g - 11 * b) / 100;
                GY = (-30 * r + 41 * g - 11 * b) / 100;
                BY = (-30 * r - 59 * g + 89 * b) / 100;
                Y = (30 * r + 59 * g + 11 * b) / 100;
                RYY = (S * BY + C * RY) / 256;
                BYY = (C * BY - S * RY) / 256;
                GYY = (-51 * RYY - 19 * BYY) / 100;
                R = Y + RYY;
                R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
                G = Y + GYY;
                G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
                B = Y + BYY;
                B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
                pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
            }

        result = Bitmap.createBitmap(width, height, src.getConfig());
        result.setPixels(pix, 0, width, 0, 0, width, height);
        pix = null;
        return result;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }
}
