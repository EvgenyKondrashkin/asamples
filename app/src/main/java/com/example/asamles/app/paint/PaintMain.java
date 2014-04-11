package com.example.asamles.app.paint;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.dialog.BlurredColorPickerDialog;
import com.example.asamles.app.dialog.BlurredProgressDialog;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.UUID;

public class PaintMain extends Fragment implements SizeAdapter.SizeListener {

    private DrawingView drawView;
    private float smallBrush;
    private int oldColor;
    private IconDrawable pencilIcon;
    private IconDrawable eraserIcon;
    private IconDrawable pencilIconSelected;
    private IconDrawable eraserIconSelected;
    private IconDrawable saveIcon;
    private IconDrawable loadIcon;
    private IconDrawable clearIcon;
    private Bitmap brushIcon;
    private MenuItem seekbarItem;
    private SeekbarActionProvider seekbarActionProvider;
    private boolean erase = false;
	private String image = null;
    public static final String BITMAP = "BITMAP";

    public static PaintMain newInstance() {
        PaintMain fragment = new PaintMain();
        return fragment;
    }
	public static PaintMain newInstance(String image) {
        PaintMain fragment = new PaintMain();
		Bundle args = new Bundle();
        args.putString(BITMAP, image);
        fragment.setArguments(args);
        return fragment;
    }

    public PaintMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        image = getArguments() != null ? getArguments().getString(BITMAP) : null;
		oldColor = Color.BLACK;
		smallBrush = 10;
		setHasOptionsMenu(true);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View rootView = inflater.inflate(R.layout.fragment_paint, container, false);

        drawView = (DrawingView) rootView.findViewById(R.id.drawing);
		if(image != null) {
			drawView.setBackgroundDrawable(new BitmapDrawable(SaveLoadFile.loadImageFromStorage(getActivity(), image)));
		}
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.paint_menu, menu);
        pencilIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_pencil)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        eraserIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_eraser)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        pencilIconSelected = new IconDrawable(getActivity(), Iconify.IconValue.fa_pencil)
                .colorRes(R.color.green)
                .actionBarSize();
        eraserIconSelected = new IconDrawable(getActivity(), Iconify.IconValue.fa_eraser)
                .colorRes(R.color.green)
                .actionBarSize();
        saveIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_floppy_o)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        loadIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_arrow_circle_o_down)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        clearIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_trash_o)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        brushIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_brush);
        menu.findItem(R.id.action_save).setIcon(saveIcon);
        menu.findItem(R.id.action_clear).setIcon(clearIcon);
        seekbarItem = menu.findItem(R.id.action_size);
        seekbarActionProvider = (SeekbarActionProvider) MenuItemCompat.getActionProvider(seekbarItem);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_brush);
        seekbarActionProvider.setSeekbarActionProvider(getActivity(), this, icon, (int) smallBrush);
        if (erase) {
            menu.findItem(R.id.action_erase).setIcon(eraserIconSelected);
            menu.findItem(R.id.action_pencil).setIcon(pencilIcon);
        } else {
            menu.findItem(R.id.action_erase).setIcon(eraserIcon);
            menu.findItem(R.id.action_pencil).setIcon(pencilIconSelected);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser, int positionInList) {
        smallBrush = progress;
        drawView.setBrushSize((float) progress);
//        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar, int positionInList) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar, int positionInList) {
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_erase:
                erase = true;
                drawView.setErase(erase);
                getActivity().supportInvalidateOptionsMenu();
                break;
            case R.id.action_save:
                checkDialog(this.getString(R.string.save_title), this.getString(R.string.save_message), save);
                break;
            case R.id.action_pencil:
                erase = false;
                drawView.setErase(erase);
                getActivity().supportInvalidateOptionsMenu();
                break;
            case R.id.action_clear:
                checkDialog(this.getString(R.string.clear_title), this.getString(R.string.clear_paint_message), clear);
                break;
            case R.id.action_color:
                showColorPicker(item);
                break;
            case R.id.action_load:
                checkDialog(this.getString(R.string.load_paint_title), this.getString(R.string.load_paint_message), load);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    Runnable clear = new Runnable() {
        @Override
        public void run() {
            drawView.clear();
			drawView.setBackgroundColor(Color.WHITE);
        }
    };
    Runnable save = new Runnable() {
        @Override
        public void run() {
            drawView.setDrawingCacheEnabled(true);
            drawView.buildDrawingCache(true);
            Bitmap bitmap = drawView.getDrawingCache();
            SaveLoadFile.saveToGallaryAndApp(getActivity(), bitmap);
            drawView.setDrawingCacheEnabled(false);
        }
    };

    Runnable load = new Runnable() {
        @Override
        public void run() {
            loadFromGallery(SaveLoadFile.loadAllFiles(getActivity()));
            // String[] wat = SaveLoadFile.loadAllFiles(getActivity());
			// loadFromGallery
            // drawView.setBackgroundDrawable(new BitmapDrawable(SaveLoadFile.loadImageFromStorage(getActivity(), wat[0])));
        }
    };
    private void loadFromGallery(String[] image) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        PaintGalery paintGallery = PaintGalery.newInstance(image);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, paintGallery).addToBackStack("Paint").commit();
    }
    public void showColorPicker(final MenuItem item) {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredColorPickerDialog newFragment = BlurredColorPickerDialog.newInstance(this.getString(R.string.colorpicker_title), oldColor);
        newFragment.setBlurredColorPickerDialogListener(new BlurredColorPickerDialog.BlurredColorPickerDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog, int color) {
                oldColor = color;
                drawView.setColor(color);
                GradientDrawable drawable = (GradientDrawable) getActivity().getResources().getDrawable(R.drawable.action_colorpicker);
                drawable.setColor(color);
                item.setIcon(drawable);
                dialog.dismiss();
                getActivity().supportInvalidateOptionsMenu();
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

    public void checkDialog(String title, String message, final Runnable type) {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredAlertDialog newFragment = BlurredAlertDialog.newInstance(title, message);
        newFragment.setBlurredAlertDialogListener(new BlurredAlertDialog.BlurredAlertDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog) {
                type.run();
                dialog.dismiss();
			}
            @Override
            public void onBlurredAlertDialogNegativeClick(DialogFragment dialog) { dialog.dismiss();}
            @Override
            public void onBlurredAlertDialogCancel(DialogFragment dialog) { dialog.dismiss();}
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }

    public void showProgressDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance(this.getString(R.string.progress_save_message), false);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
}