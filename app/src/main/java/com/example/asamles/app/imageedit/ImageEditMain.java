package com.example.asamles.app.imageedit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageEditMain extends Fragment implements BlurTask.BlurTaskListener {
    private static final int SELECT_PICTURE = 1;
    private ImageView imageView;
    private PhotoViewAttacher mAttacher;
    private float angle = 0;
	private int opacity = 100;
	private float opacityIndex = 255/100;
    private Bitmap bitmap;
    private ViewGroup container;
    private FrameLayout frameLayout;
	private ADialogs seekbarDialog;

    public static ImageEditMain newInstance() {
        ImageEditMain fragment = new ImageEditMain();
        return fragment;
    }

    public ImageEditMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        View rootView = inflater.inflate(R.layout.fragment_imageedit, container, false);
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
        menu.findItem(R.id.action_seek).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_eye_slash)
                .colorRes(R.color.grey_light)
                .actionBarSize());
        menu.findItem(R.id.action_blur).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_tint)
                .colorRes(R.color.grey_light)
                .actionBarSize());
        menu.findItem(R.id.action_rotate).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_undo)
                .colorRes(R.color.grey_light)
                .actionBarSize());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_seek:
				seekbarDialog = new ADialogs(getActivity());
                seekbarDialog.seekbar(true, getActivity().getString(R.string.opacity), opacity, getActivity().getString(R.string.set), getActivity().getString(R.string.cancel));
				seekbarDialog.setADialogsSeekBarListener(new ADialogs.ADialogsSeekBarListener(){
                    @Override
                    public void onADialogsSeekBarPositiveClick(DialogInterface dialog, SeekBar seekbar) {
						opacity = seekbar.getProgress();
                        imageView.setAlpha((int)(opacity*opacityIndex));
                        dialog.dismiss();
                    }
                });
                return true;
            case R.id.action_blur:
                blur(bitmap, imageView);
                return true;
            case R.id.action_rotate:
                angle = -90;
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                bitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                mAttacher.update();
                return true;
            case R.id.action_save:
                checkDialog(this.getString(R.string.save_title), this.getString(R.string.save_message), save);
                return true;
            case R.id.action_load:
                checkDialog(this.getString(R.string.load_paint_title), this.getString(R.string.load_paint_message), load);
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
            imageView.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, bitmap.getWidth(), bitmap.getHeight(), false)));
        } else {
            ADialogs alertDialog = new ADialogs(getActivity());
			alertDialog.alert(true, getActivity().getString(R.string.error), getActivity().getString(R.string.blur_task_error), getActivity().getString(R.string.ok), null);
        }
    }
    Runnable save = new Runnable() {
        @Override
        public void run() {
            SaveLoadFile.saveToGallery(getActivity(), bitmap, null);
        }
    };

    Runnable load = new Runnable() {
        @Override
        public void run() {
            loadFromGallery();

        }
    };
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

    private void loadFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.load_intent_title)), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            String picturePath = SaveLoadFile.loadFromGallery(getActivity(), data);
            // System.out.println("Image Path : " + selectedImagePath);
            imageView.setImageURI(selectedImageUri);
            mAttacher = new PhotoViewAttacher(imageView);
        }

    }
//    public String getPath(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
}