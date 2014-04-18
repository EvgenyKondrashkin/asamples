package com.example.asamles.app.imageedit.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;

import com.example.asamles.app.R;

public class ImageEditior {
    // type definition
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;
    public static final double PI = 3.14159d;
    public static final double FULL_CIRCLE_DEGREE = 360d;
    public static final double HALF_CIRCLE_DEGREE = 180d;
    public static final double RANGE = 256d;
     /**
     *
     * @param bmp input bitmap
     * @param brightness -255..255 0 is default
     * @return new bitmap
     */
    public static Bitmap changeBitmapBrightness(Bitmap bmp, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        1, 0, 0, 0, brightness,
                        0, 1, 0, 0, brightness,
                        0, 0, 1, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    //0-10
    public static Bitmap changeBitmapContrast(Bitmap bmp, float contrast)
    {
        float scale = contrast;
       float translate = (-.5f * scale + .5f) * 255.f;
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        scale, 0, 0, 0, translate,
                        0, scale, 0, 0, translate,
                        0, 0, scale, 0, translate,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    public static Bitmap changeBitmapSaturation(Bitmap src, float settingSat) {

        int w = src.getWidth();
        int h = src.getHeight();

        Bitmap bitmapResult =
                Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvasResult = new Canvas(bitmapResult);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(settingSat);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        canvasResult.drawBitmap(src, 0, 0, paint);

        return bitmapResult;
    }
    public static Bitmap doAlpha(Bitmap bmp, int alpha){
        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Paint transparentpaint = new Paint();
        transparentpaint.setAlpha(alpha); // 0 - 255
        canvas.drawBitmap(bmp, 0, 0, transparentpaint);
        return ret;
    }
    public static Bitmap doRotate(Bitmap bitmap, int direction){
        int angle = direction * 90;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }
    public static Bitmap doGrayscale(Bitmap bmp)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0.5f, 0.5f, 0.5f, 0, 0,
                        0.5f, 0.5f, 0.5f, 0, 0,
                        0.5f, 0.5f, 0.5f, 0, 0,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    public static Bitmap doInvert(Bitmap bmp)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        -1, 0, 0, 1, 1,
                        0, -1, 0, 1, 1,
                        0, 0, -1, 1, 1,
                        0, 0, 0, 1, 0,
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    public static Bitmap doSepia(Bitmap bmp) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0.3588f, 0.7044f, 0.1368f, 0, 0,
                        0.2990f, 0.5870f, 0.1140f, 0, 0,
                        0.2392f, 0.4696f, 0.0912f, 0, 0,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    public static Bitmap doSwap(Bitmap bmp)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0, 0, 1, 0, 0,
                        0, 1, 0, 0, 0,
                        1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0,
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    private static float lumR = 0.212671f;
    private static float lumG = 0.71516f;
    private static float lumB = 0.072169f;
    private float lumR2 = 0.3086f;
    private float lumG2 = 0.6094f;
    private float lumB2 = 0.0820f;
    public static Bitmap doPolaroid(Bitmap bmp)
    {
//        ColorMatrix cm = new ColorMatrix(new float[]
//                {
//                        1.438f, -0.062f, -0.062f, 0, 0,
//                        -0.122f, 1.378f, -0.122f, 0, 0,
//                        -0.016f, -0.016f, 1.483f, 0, 0,
//                        0,    0,    0, 1, 0,
//                });
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        0 , 1, 0 , 0, 0,
                        0 , 0 , 1 , 0, 0,
                        1 , 0 , 0 , 0, 0,
                        0,    0,    0, 1, 0,
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    public static Bitmap doBlackWhite(Bitmap bmp)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        1.5f, 1.5f, 1.5f, 0, 0,
                        1.5f, 1.5f, 1.5f, 0, 0,
                        1.5f, 1.5f, 1.5f, 0, 0,
                           0,    0,    0, 1, 0,
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    public static Bitmap doOldPhoto(Bitmap bmp, Context context) {
        Bitmap sepia = doSepia(bmp);
        Bitmap frameRes = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.old_frame), bmp.getWidth(), bmp.getHeight(), true);
        Bitmap frame = doAlpha(frameRes, 127);

        Bitmap bmOverlay = Bitmap.createBitmap(sepia.getWidth(), sepia.getHeight(), sepia.getConfig());
        Canvas canvas = new Canvas(bmOverlay);

        canvas.drawBitmap(sepia, new Matrix(), null);
        canvas.drawBitmap(frame, new Matrix(), null);
        return bmOverlay;
    }
    public static Bitmap doFlip(Bitmap bmp, int type) {
        Matrix matrix = new Matrix();
        if(type == FLIP_VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        }
        else if(type == FLIP_HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        } else {
            return null;
        }
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }
    public static Bitmap doBokehPhoto(Bitmap bmp, Context context) {
        Bitmap color = changeBitmapSaturation(bmp,2);
        Bitmap frameRes = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bokeh), bmp.getWidth(), bmp.getHeight(), true);
//        Bitmap frame = doAlpha(frameRes, 127);

        Bitmap bmOverlay = Bitmap.createBitmap(color.getWidth(), color.getHeight(), color.getConfig());
        Canvas canvas = new Canvas(bmOverlay);

        canvas.drawBitmap(color, new Matrix(), null);
        canvas.drawBitmap(frameRes, new Matrix(), null);
        return bmOverlay;
    }
     
    public static Bitmap doTint(Bitmap src, int degree) {
 
        int width = src.getWidth();
        int height = src.getHeight();
 
        int[] pix = new int[width * height];
        src.getPixels(pix, 0, width, 0, 0, width, height);
 
        int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
        double angle = (PI * (double)degree) / HALF_CIRCLE_DEGREE;
        
        int S = (int)(RANGE * Math.sin(angle));
        int C = (int)(RANGE * Math.cos(angle));
 
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = ( pix[index] >> 16 ) & 0xff;
                int g = ( pix[index] >> 8 ) & 0xff;
                int b = pix[index] & 0xff;
                RY = ( 70 * r - 59 * g - 11 * b ) / 100;
                GY = (-30 * r + 41 * g - 11 * b ) / 100;
                BY = (-30 * r - 59 * g + 89 * b ) / 100;
                Y  = ( 30 * r + 59 * g + 11 * b ) / 100;
                RYY = ( S * BY + C * RY ) / 256;
                BYY = ( C * BY - S * RY ) / 256;
                GYY = (-51 * RYY - 19 * BYY ) / 100;
                R = Y + RYY;
                R = ( R < 0 ) ? 0 : (( R > 255 ) ? 255 : R );
                G = Y + GYY;
                G = ( G < 0 ) ? 0 : (( G > 255 ) ? 255 : G );
                B = Y + BYY;
                B = ( B < 0 ) ? 0 : (( B > 255 ) ? 255 : B );
                pix[index] = 0xff000000 | (R << 16) | (G << 8 ) | B;
            }
         
        Bitmap outBitmap = Bitmap.createBitmap(width, height, src.getConfig());    
        outBitmap.setPixels(pix, 0, width, 0, 0, width, height);
        
        pix = null;
        
        return outBitmap;
    }
}
