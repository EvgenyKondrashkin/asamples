package com.example.asamles.app.imageedit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.constants.Constants;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageEdit extends Fragment {

    private ImageView imageView;
	private	PhotoViewAttacher mAttacher;
	private float angle = 0;
    private Bitmap bitmap;

    public static ImageEdit newInstance() {
        ImageEdit fragment = new ImageEdit();
        return fragment;
    }

    public ImageEdit() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_edit_main, container, false);
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
			return true;
			case R.id.action_rotate:
				angle = -90;
                // imageView.setPivotX(imageView.getWidth()/2);
                // imageView.setPivotY(imageView.getHeight()/2);
                // imageView.setRotation(angle);
//				Matrix matrix=new Matrix();
//				imageView.setScaleType(ImageView.ScaleType.MATRIX);   //required
//				matrix.postRotate(angle, imageView.getWidth()/2, imageView.getHeight()/2);
//				imageView.setImageMatrix(matrix);

                // create new matrix object
                Matrix matrix = new Matrix();
                // setup rotation degree
                matrix.postRotate(angle);
                // return new bitmap rotated using matrix
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                mAttacher.update();
			return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}