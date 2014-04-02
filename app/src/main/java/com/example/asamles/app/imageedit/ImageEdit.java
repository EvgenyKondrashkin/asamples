package com.example.asamles.app.imageedit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.imageedit.blur.BlurTask;

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
        frameLayout = (FrameLayout) rootView.findViewById(R.id.frameLayout);
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

            case R.id.action_seek:
                ADialogs.seekBar(getActivity(), imageView, container);
//                container.setDrawingCacheEnabled(true);
//                container.buildDrawingCache(true);
//                Bitmap cs = Bitmap.createBitmap(container.getDrawingCache());
//                imageView.setImageBitmap(cs);
//                container.setDrawingCacheEnabled(false);
                return true;
            case R.id.action_blur:
                Toast.makeText(getActivity(), "Blur", Toast.LENGTH_LONG).show();
                blur(bitmap, imageView);
                return true;
            case R.id.action_rotate:
                angle = -90;
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                bitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                mAttacher.update();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blur(Bitmap bkg, ImageView view) {
        BlurTask task = new BlurTask(bkg, view, this);
        task.execute();
    }

    public void onBlurTaskComplete(Bitmap result) {
        if (result != null) {
            // ImageView bluredImageView = new ImageView(getActivity());
            // bluredImageView.setLayoutParams(imageView.getLayoutParams());
            // bluredImageView.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, bitmap.getWidth(), bitmap.getHeight(), false)));
            // bluredImageView.setTag("Blured");
            // frameLayout.addView(bluredImageView);
            imageView.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, bitmap.getWidth(), bitmap.getHeight(), false)));
        } else {
            ADialogs.alert(getActivity(), getActivity().getString(R.string.error));
        }
    }
}