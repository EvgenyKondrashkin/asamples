package com.example.asamles.app.paint;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.example.asamles.app.actionprovider.SeekbarActionProvider;
import com.example.asamles.app.actionprovider.SizeAdapter;
import com.example.asamles.app.dialog.BlurredColorPickerDialog;

import java.util.Random;
import java.util.UUID;

public class PaintMain extends Fragment implements SizeAdapter.SizeListener{

    private DrawingView drawView;
    private float smallBrush = 10;
    private int oldColor = -16777216;

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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View rootView = inflater.inflate(R.layout.paint_main, container, false);
		
        drawView = (DrawingView) rootView.findViewById(R.id.drawing);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.paint_menu, menu);

		super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_brush);
        MenuItem seekbarItem = menu.findItem(R.id.action_size);
        SeekbarActionProvider seekbarActionProvider = (SeekbarActionProvider) MenuItemCompat.getActionProvider(seekbarItem);
        seekbarActionProvider.setSeekbarActionProvider(getActivity(), this, icon, (int) smallBrush);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

			case R.id.action_erase:
                drawView.setErase(true);
                return true;
            case R.id.action_save:
                saveToGalery();
                return true;
            case R.id.action_pencil:
                drawView.setErase(false);
                return true;
            case R.id.action_clear:
                drawView.clear();
                return true;
			case R.id.action_color:
                showColorPicker(item);
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

	@Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser, int positionInList) {
        smallBrush = progress;
		drawView.setBrushSize((float) progress);
//        getActivity().supportInvalidateOptionsMenu();
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar, int positionInList) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar, int positionInList) {
        getActivity().supportInvalidateOptionsMenu();
    }

    private void saveToGalery() {
        drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache(true);

        String imgSaved = MediaStore.Images.Media.insertImage(
                getActivity().getContentResolver(), drawView.getDrawingCache(),
                UUID.randomUUID().toString() + ".png", "drawing");
        drawView.setDrawingCacheEnabled(false);
        if (imgSaved != null) {
            Toast.makeText(getActivity().getApplicationContext(), "Drawing saved to Gallery!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Oops! Image could not be saved.", Toast.LENGTH_SHORT).show();
        }
    }
	
	public void showColorPicker(final MenuItem item) {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredColorPickerDialog newFragment = BlurredColorPickerDialog.newInstance("Choose color", oldColor);
        newFragment.setBlurredColorPickerDialogListener(new BlurredColorPickerDialog.BlurredColorPickerDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog, int color) {
                oldColor = color;
//                String hexColor = String.format("#%08X", (0xFFFFFFFF & color));
//                item.setBackgroundColor(color);
				drawView.setColor(color);
                GradientDrawable drawable = (GradientDrawable)getActivity().getResources().getDrawable(R.drawable.action_colorpicker);
                drawable.setColor(color);
                item.setIcon(drawable);
                dialog.dismiss();
            }
            @Override
            public void onBlurredAlertDialogNegativeClick(DialogFragment dialog) {
                dialog.dismiss();
            }
            @Override
            public void onBlurredAlertDialogCancel(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
	public void setMenuItemColor() {
//		for(int i = 0; i < flagedMenuItem.length; i++) {
//			if(flagedMenuItem){
//
//			}
//		}
	}
}