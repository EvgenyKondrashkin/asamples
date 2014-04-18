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
import android.graphics.drawable.Drawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.asamles.app.MainActivity;
import com.example.asamles.app.R;
import com.example.asamles.app.dialog.ADialogs;
import com.example.asamles.app.dialog.BlurredAlertDialog;
import com.example.asamles.app.imageedit.blur.BlurTask;
import com.example.asamles.app.imageedit.utils.SeekbarFragment;
import com.example.asamles.app.saveload.SaveLoadFile;
import com.example.asamles.app.upNavigation.UpFragmentMain;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageEditMain extends Fragment  {
    private static final int SELECT_PICTURE = 1;
	static final int REQUEST_IMAGE_CAPTURE = 2;
    public static ImageView imageView;
    public static PhotoViewAttacher mAttacher;
    public Bitmap bitmap;

    public static ImageEditMain newInstance() {
        ImageEditMain fragment = new ImageEditMain();
        return fragment;
    }

    public ImageEditMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imageedit, container, false);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.bottomLayout, HorizontalBar.newInstance());
        ft.commit();
        setHasOptionsMenu(true);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.raw.photo);
        imageView.setImageBitmap(bitmap);
        mAttacher = new PhotoViewAttacher(imageView);
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.image_edit_menu, menu);
       menu.findItem(R.id.action_photo).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_camera)
               .colorRes(R.color.grey_light)
               .actionBarSize());
       menu.findItem(R.id.action_load).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_th)
               .colorRes(R.color.grey_light)
               .actionBarSize());
		menu.findItem(R.id.action_save).setIcon(new IconDrawable(getActivity(), Iconify.IconValue.fa_floppy_o)
               .colorRes(R.color.grey_light)
               .actionBarSize());
        menu.setGroupVisible(R.id.menu_group_imageedit, true);
        super.onCreateOptionsMenu(menu, inflater);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_photo:
               checkDialog(this.getString(R.string.photo_title), this.getString(R.string.photo_message), photo);
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

    Runnable save = new Runnable() {
        @Override
        public void run() {
            Bitmap saveBitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            SaveLoadFile.saveToGallery(getActivity(), saveBitmap, null);
        }
    };

    Runnable load = new Runnable() {
        @Override
        public void run() {
            loadFromGallery();
        }
    };
	
	Runnable photo = new Runnable() {
        @Override
        public void run() {
            makePhoto();
        }
    };
	
    public void checkDialog(String title, String message, final Runnable type) {
        ADialogs alertDialog = new ADialogs(getActivity());
        alertDialog.alert(true, title, message, getActivity().getString(R.string.ok), getActivity().getString(R.string.cancel));
        alertDialog.setADialogsListener(new ADialogs.ADialogsListener() {
            @Override
            public void onADialogsPositiveClick(DialogInterface dialog) {
                type.run();
                dialog.dismiss();
            }

            @Override
            public void onADialogsNegativeClick(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void onADialogsCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }
	private void makePhoto() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
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
        if(bitmap != null) {
            bitmap.recycle();
            bitmap = null;}
        if (requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
//            String picturePath = SaveLoadFile.loadFromGallery(getActivity(), data);
            bitmap = BitmapFactory.decodeResource(getResources(), R.raw.photo);
            imageView.setImageURI(selectedImageUri);
            mAttacher = new PhotoViewAttacher(imageView);
        }
		if (requestCode == REQUEST_IMAGE_CAPTURE) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = imageBitmap;
			imageView.setImageBitmap(imageBitmap);
			mAttacher = new PhotoViewAttacher(imageView);
    }

    }
}