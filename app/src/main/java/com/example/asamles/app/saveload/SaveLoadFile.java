package com.example.asamles.app.saveload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.asamles.app.R;

import java.io.File;
import java.util.UUID;

public class SaveLoadFile {

    public static String setName() {
        return UUID.randomUUID().toString() + ".png";
    }
	public static void saveToGallery(Context context, View drawView) {
        // showProgressDialogFragment();
        drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache(true);
        String fileName = setName();
        String imgSaved = MediaStore.Images.Media.insertImage(
                context.getContentResolver(), drawView.getDrawingCache(),
                fileName, context.getString(R.string.save_tag));
        drawView.setDrawingCacheEnabled(false);
        if (imgSaved != null) {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_positive_result), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.save_negative_result), Toast.LENGTH_SHORT).show();
        }
    }
    public static void saveToPaintGallery(Context context, View drawView) {
        String fileName = setName();
        File file = new File(context.getFilesDir(), fileName);
    }


    private String picturePath;

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null) {
//            return;
//        }
//        if (requestCode == SELECT_PICTURE) {
//            Uri selectedImageUri = data.getData();
//            picturePath = getPath(selectedImageUri);
//            // System.out.println("Image Path : " + selectedImagePath);
//            drawView.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeFile(picturePath)));

//			drawView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//			int widht = drawView.getRight();
//			int height = drawView.getBottom();
//
//			drawView.setBackground(new BitmapDrawable(getActivity().getResources(), lessResolution(picturePath, widht, height)));
//                img.setImageURI(selectedImageUri);
//        }

//    }

//    public String getPath(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

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
}