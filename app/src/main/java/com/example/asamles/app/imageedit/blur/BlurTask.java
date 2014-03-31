package com.example.asamles.app.imageedit.blur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BlurTask extends AsyncTask<Void, Bitmap, Bitmap> {

    public interface BlurTaskListener {
        public void onBlurTaskComplete(Bitmap result);
    }

    private BlurTaskListener callback;
	private Bitmap bkg;
	private ImageView view;
    public BlurTask(Bitmap bkg, ImageView view, BlurTaskListener callback) {
        this.bkg = bkg;
		this.view = view;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        float scaleFactor = 8;
        float radius = 2;

        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth() / scaleFactor),
                (int) (bkg.getHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
		return overlay;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        callback.onBlurTaskComplete(result);
    }
    
}