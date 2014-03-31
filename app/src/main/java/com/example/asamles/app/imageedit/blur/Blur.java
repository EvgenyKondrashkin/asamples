package com.example.asamles.app.imageedit.blur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.asamles.app.R;

public class Blur extends Fragment {
    private ImageView imageView;
    private Bitmap bitmap;

    public static Blur newInstance() {
        Blur fragment = new Blur();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_edit_main, container, false);

		imageView = (ImageView) view.findViewById(R.id.image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.raw.photo);
        imageView.setImageBitmap(bitmap);

        applyBlur();
        return view;
    }

    private void applyBlur() {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                imageView.buildDrawingCache();

                Bitmap bmp = imageView.getDrawingCache();
                blur(bitmap, imageView);
                return true;
            }
        });
    }

    private void blur(Bitmap bkg, ImageView view) {
        float scaleFactor = 1;
        float radius = 20;

        scaleFactor = 8;
        radius = 2;

        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth() / scaleFactor),
                (int) (bkg.getHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setImageDrawable(new BitmapDrawable(getResources(), overlay));
    }

}