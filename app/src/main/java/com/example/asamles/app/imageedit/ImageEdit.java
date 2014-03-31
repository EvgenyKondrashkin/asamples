package com.example.asamles.app.imageedit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.imageedit.blur.Blur;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.example.asamles.app.imageedit.blur.FastBlur;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageEdit extends Fragment implements BlurTask.BlurTaskListener {

    private ImageView imageView;
    private PhotoViewAttacher mAttacher;
    private float angle = 0;
    private Bitmap bitmap;
	private ViewGroup container;
	private FrameLayout frameLayout;

    public static ImageEdit newInstance() {
        ImageEdit fragment = new ImageEdit();
        return fragment;
    }

    public ImageEdit() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		this.container = container;
        View rootView = inflater.inflate(R.layout.image_edit_main, container, false);
		frameLayout = (FrameLayout) view.findViewById(R.id.container);
        setHasOptionsMenu(true);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.raw.photo);
        imageView.setImageBitmap(bitmap);
        mAttacher = new PhotoViewAttacher(imageView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.image_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_blur:
                Toast.makeText(getActivity(), "Blur", Toast.LENGTH_LONG).show();
                // FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                // ft.replace(R.id.container, Blur.newInstance());
                // ft.addToBackStack("firstlvl");
                // ft.commit();
				blur3(bitmap, imageView);
                return true;
            case R.id.action_rotate:
                angle = -90;
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                mAttacher.update();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	private void blur(Bitmap bkg, ImageView view) {
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
        view.setImageDrawable(new BitmapDrawable(getResources(), overlay));
    }
	
	private void blur2(Bitmap bkg, ImageView view) {
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
		
		ImageView bluredImageView = new ImageView(getActivity());
		LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		bluredImageView.setLayoutParams(view.getLayoutParams());
        // bluredImageView.setImageDrawable(new BitmapDrawable(getResources(), overlay));
		bluredImageView.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(overlay, bkg.getWidth(), bkg.getHeight(), false)));
        bluredImageView.setTag("Blured");
		
		frameLayout.addView(bluredImageView);
        
    }
	private void blur3(Bitmap bkg, ImageView view) {
		BlurTask task = new BlurTask(bkg, view, this);
		task.execute();
	}
	public void onBlurTaskComplete(Bitmap result){
		if(result != null){
			imageView.setImageDrawable(new BitmapDrawable(getResources(), result));
		} else {
            ADialogs.alert(getActivity(),getActivity().getString(R.string.error));
		}
	}
}