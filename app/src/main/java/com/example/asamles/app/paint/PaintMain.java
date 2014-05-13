package com.example.asamles.app.paint;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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

import com.example.asamles.app.R;
import com.example.asamles.app.actionprovider.SeekbarActionProvider;
import com.example.asamles.app.actionprovider.SizeAdapter;
import com.example.asamles.app.constants.Constants;
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.dialog.BlurredColorPickerDialog;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class PaintMain extends Fragment implements SizeAdapter.SizeListener {

    private DrawingView drawView;
    private float smallBrush = 10;
    private int oldColor = Color.BLACK;
    private IconDrawable pencilIcon;
    private IconDrawable eraserIcon;
    private IconDrawable pencilIconSelected;
    private IconDrawable eraserIconSelected;
    private SeekbarActionProvider seekbarActionProvider;
    private boolean erase = false;
    private String imageFromGallery = null;

    public static PaintMain newInstance() {
        return new PaintMain();
    }

    public PaintMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View rootView = inflater.inflate(R.layout.fragment_paint, container, false);

        if (rootView != null) {
            drawView = (DrawingView) rootView.findViewById(R.id.drawing);
        }
        drawView.setColor(oldColor);
        drawView.setBrushSize(smallBrush);
        BitmapDrawable bitmapDrawable;
        if (imageFromGallery != null) {
            bitmapDrawable = new BitmapDrawable(getActivity().getResources(), SaveLoadFile.loadImageFromPublicStorage(Constants.PAINT_GALLERY, imageFromGallery));
            if(bitmapDrawable.getBitmap() != null){
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    drawView.setBackground(bitmapDrawable);
                } else {
                    drawView.setBackgroundDrawable(bitmapDrawable);
                }
            } else {
                imageFromGallery = null;
            }
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.paint_menu, menu);
        pencilIcon = new IconDrawable(getActivity(), Iconify.IconValue.icon_pencil)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        eraserIcon = new IconDrawable(getActivity(), Iconify.IconValue.icon_eraser)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        pencilIconSelected = new IconDrawable(getActivity(), Iconify.IconValue.icon_pencil)
                .colorRes(R.color.green)
                .actionBarSize();
        eraserIconSelected = new IconDrawable(getActivity(), Iconify.IconValue.icon_eraser)
                .colorRes(R.color.green)
                .actionBarSize();
        IconDrawable saveIcon = new IconDrawable(getActivity(), Iconify.IconValue.icon_save)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        IconDrawable loadIcon = new IconDrawable(getActivity(), Iconify.IconValue.icon_down_o)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        IconDrawable clearIcon = new IconDrawable(getActivity(), Iconify.IconValue.icon_trash)
                .colorRes(R.color.grey_light)
                .actionBarSize();
        menu.findItem(R.id.action_save).setIcon(saveIcon);
        menu.findItem(R.id.action_load).setIcon(loadIcon);
        menu.findItem(R.id.action_clear).setIcon(clearIcon);
        GradientDrawable drawable = (GradientDrawable) getActivity().getResources().getDrawable(R.drawable.action_colorpicker);
        if (drawable != null) {
            drawable.setColor(oldColor);
        }
        menu.findItem(R.id.action_color).setIcon(drawable);
        MenuItem seekbarItem = menu.findItem(R.id.action_size);
        seekbarActionProvider = (SeekbarActionProvider) MenuItemCompat.getActionProvider(seekbarItem);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        seekbarActionProvider.setSeekbarActionProvider(this, (int) smallBrush);
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
        drawView.setBrushSize((float) smallBrush);
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
                drawView.setErase(true);
                getActivity().supportInvalidateOptionsMenu();
                break;
            case R.id.action_save:
                if (imageFromGallery == null) {
                    checkDialog(this.getString(R.string.save_title), this.getString(R.string.save_message), saveAsNew);
                } else {
                    saveDialog(this.getString(R.string.save_title), this.getString(R.string.save_new_message), this.getString(R.string.save_new_button), this.getString(R.string.save_ok_button));
                }
                break;
            case R.id.action_pencil:
                erase = false;
                drawView.setErase(false);
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
    Runnable saveAsNew = new Runnable() {
        @Override
        public void run() {
            drawView.setDrawingCacheEnabled(true);
            drawView.buildDrawingCache(true);
            Bitmap bitmap = drawView.getDrawingCache();
            SaveLoadFile.saveToPublicGallery(getActivity(), bitmap, Constants.PAINT_GALLERY, null, false);
            drawView.setDrawingCacheEnabled(false);
        }
    };
    Runnable saveCurrent = new Runnable() {
        @Override
        public void run() {
            drawView.setDrawingCacheEnabled(true);
            drawView.buildDrawingCache(true);
            Bitmap bitmap = drawView.getDrawingCache();
            SaveLoadFile.saveToPublicGallery(getActivity(), bitmap, Constants.PAINT_GALLERY, imageFromGallery, false);
            drawView.setDrawingCacheEnabled(false);
        }
    };
    Runnable load = new Runnable() {
        @Override
        public void run() {
            loadFromGallery(SaveLoadFile.loadAllPublicFiles(Constants.PAINT_GALLERY));
        }
    };

    private void loadFromGallery(String[] image) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        PaintGallery paintGallery = PaintGallery.newInstance(image);
        paintGallery.setOkFragmentListener(new PaintGallery.DoneFragmentListener() {
            @Override
            public void onDone(String tag, String image) {
                if (image != null) {
                    imageFromGallery = image;
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
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
                drawView.setColor(oldColor);
                GradientDrawable drawable = (GradientDrawable) getActivity().getResources().getDrawable(R.drawable.action_colorpicker);
                if (drawable != null) {
                    drawable.setColor(oldColor);
                }
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
	
    public void saveDialog(String title, String message, String okButton, String cancelButton) {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredAlertDialog newFragment = BlurredAlertDialog.newInstance(title, message, okButton, cancelButton);
        newFragment.setBlurredAlertDialogListener(new BlurredAlertDialog.BlurredAlertDialogListener() {
            @Override
            public void onBlurredAlertDialogPositiveClick(DialogFragment dialog) {
                saveAsNew.run();
                dialog.dismiss();
            }

            @Override
            public void onBlurredAlertDialogNegativeClick(DialogFragment dialog) {
				saveCurrent.run();
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

//    public void showProgressDialogFragment() {
//        View v = getActivity().getWindow().getDecorView();
//        v.setId(1);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance(this.getString(R.string.progress_save_message), false);
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(1, newFragment).commit();
//    }
}