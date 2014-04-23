package com.example.asamles.app.paint.utils;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.asamles.app.constants.Constants;

import java.io.File;


public class PaintGalleryItem implements Parcelable {
    private String imageName;
    private String imageFullName;
    private boolean selected = false;

    public PaintGalleryItem() {
    }

    public PaintGalleryItem(String imageName) {
        this.imageName = imageName;
		File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constants.PAINT_GALLERY);
        imageFullName = "file:" + directory.getAbsolutePath() + "/" + imageName;
        selected = false;
    }

    public PaintGalleryItem(Parcel source) {

        imageName = source.readString();
        imageFullName = source.readString();
        selected = source.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageName);
        parcel.writeString(imageFullName);
        parcel.writeByte((byte) (selected ? 1 : 0));
        ;
    }

    public static final Parcelable.Creator<PaintGalleryItem> CREATOR = new Parcelable.Creator<PaintGalleryItem>() {
        @Override
        public PaintGalleryItem createFromParcel(Parcel source) {
            return new PaintGalleryItem(source);
        }

        @Override
        public PaintGalleryItem[] newArray(int size) {
            return new PaintGalleryItem[size];
        }
    };

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageFullName() {
        return imageFullName;
    }

    public boolean getSelected() {
        return selected;
    }

}
