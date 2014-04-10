package com.example.asamles.app.paint;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.dialog.BlurredColorPickerDialog;
import com.example.asamles.app.dialog.BlurredProgressDialog;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.UUID;

public class PaintMain extends Fragment implements SizeAdapter.SizeListener {

    private DrawingView drawView;
    private float smallBrush = 10;
    private static final int SELECT_PICTURE = 1;
    private int oldColor = -16777216;
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
        View rootView = inflater.inflate(R.layout.fragment_paint, container, false);

        drawView = (DrawingView) rootView.findViewById(R.id.drawing);
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
                checkDialog("Save", "Are you sure you want to clear your drawing?", save);
                break;
            case R.id.action_pencil:
                erase = false;
                drawView.setErase(erase);
                getActivity().supportInvalidateOptionsMenu();
                break;
            case R.id.action_clear:
//                clearDialog();
                checkDialog("clear", "Are you sure you want to clear your drawing?", clear);
                break;
            case R.id.action_color:
                showColorPicker(item);
                break;
            case R.id.action_load:
                checkDialog("Load from Gallery", "Are you sure you want to clear your drawing and load from Galary?", load);
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
        }
    };
    Runnable save = new Runnable() {
        @Override
        public void run() {
            saveToGallery();
        }
    };

    private void saveToGallery() {
        // showProgressDialogFragment();
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

    Runnable load = new Runnable() {
        @Override
        public void run() {
            loadFromGallery();
        }
    };

    private void loadFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private String picturePath;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            picturePath = getPath(selectedImageUri);
            // System.out.println("Image Path : " + selectedImagePath);
            drawView.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeFile(picturePath)));

//			drawView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//			int widht = drawView.getRight();
//			int height = drawView.getBottom();
//
//			drawView.setBackground(new BitmapDrawable(getActivity().getResources(), lessResolution(picturePath, widht, height)));
//                img.setImageURI(selectedImageUri);
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap lessResolution(String filePath, int width, int height) {
        int reqHeight = width;
        int reqWidth = height;
        BitmapFactory.Options options = new BitmapFactory.Options();

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
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

    public void showProgressDialogFragment() {
        View v = getActivity().getWindow().getDecorView();
        v.setId(1);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BlurredProgressDialog newFragment = BlurredProgressDialog.newInstance("Saving...", false);
        newFragment.setBlurredProgressDialogListener(new BlurredProgressDialog.BlurredProgressDialogListener() {
            @Override
            public void onBlurredProgressDialogCancel(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(1, newFragment).commit();
    }
}