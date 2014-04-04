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
import java.util.UUID;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;

import java.util.Random;

public class PaintMain extends Fragment {

	private DrawingView drawView;
	private float smallBrush = 10;
	public static PaintMain newInstance() {
        PaintMain fragment = new PaintMain();
        return fragment;
    }

    public PaintMain() {
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		View rootView = inflater.inflate(R.layout.paint_main, container, false);
		
		drawView = (DrawingView)rootView.findViewById(R.id.drawing);

        return rootView;
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
				drawView.setErase(true);
				drawView.setBrushSize(smallBrush);
                return true;
            case R.id.action_save:
				saveToGalery();
                return true;
            case R.id.action_pencil:
				drawView.setErase(false);
				drawView.setColor("#FF000000");
				drawView.setBrushSize(smallBrush);
				drawView.setLastBrushSize(smallBrush);
                return true;
			case R.id.action_clear:
				drawView.clear();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//		//user clicked paint
//	public void paintClicked(View view){
//		//use chosen color
//
//		//set erase false
//		drawView.setErase(false);
//		drawView.setBrushSize(drawView.getLastBrushSize());
//
//		if(view!=currPaint){
//			ImageButton imgView = (ImageButton)view;
//			String color = view.getTag().toString();
//			drawView.setColor(color);
//			//update ui
//			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
//			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
//			currPaint=(ImageButton)view;
//		}
//	}
	private void saveToGalery() {
		drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache(true);
        Bitmap image = Bitmap.createBitmap(drawView.getDrawingCache(true));
		int random = new Random().nextInt(1000);
		
		String imgSaved = MediaStore.Images.Media.insertImage(
			getActivity().getContentResolver(), drawView.getDrawingCache(),
			UUID.randomUUID().toString()+".png", "drawing");
        drawView.setDrawingCacheEnabled(false);
		if(imgSaved!=null){
			Toast.makeText(getActivity().getApplicationContext(), "Drawing saved to Gallery!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity().getApplicationContext(), "Oops! Image could not be saved.", Toast.LENGTH_SHORT).show();
		}
	}
}