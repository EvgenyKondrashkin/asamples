package com.example.asamles.app.paint;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;

import java.util.Random;

public class PaintMain1 extends Fragment {
    private SmoothLine drawView;

	public static PaintMain1 newInstance() {
        PaintMain1 fragment = new PaintMain1();
        return fragment;
    }

    public PaintMain1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		drawView = new SmoothLine(getActivity(), null);
        drawView.requestFocus();
		
        return drawView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.paint_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_erase:
				drawView.setEraser();
                return true;
            case R.id.action_save:
				saveToGalery();
                return true;
            case R.id.action_pencil:
				drawView.setPencil();
                return true;
			case R.id.action_clear:
				drawView.clear();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	private void saveToGalery() {
		drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache(true);
        Bitmap image = Bitmap.createBitmap(drawView.getDrawingCache(true));
		int random = new Random().nextInt(1000);
		String imgSaved = MediaStore.Images.Media.insertImage(
			getActivity().getContentResolver(), drawView.getDrawingCache(),
			"Title"+random+".png", "drawing");
        drawView.setDrawingCacheEnabled(false);
	}
}