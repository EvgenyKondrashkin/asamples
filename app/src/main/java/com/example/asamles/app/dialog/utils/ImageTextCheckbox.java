package com.example.asamles.app.dialog.utils;

import android.os.Parcel;
import android.os.Parcelable;


public class ImageTextCheckbox implements Parcelable {
    private int image;
    private String text;
    private boolean check = false;

    public ImageTextCheckbox() {
    }

    public ImageTextCheckbox(int image, String text, boolean check) {
        this.image = image;
        this.text = text;
        this.check = check;
    }

    public ImageTextCheckbox(Parcel source) {

        image = source.readInt();
        text = source.readString();
        check = source.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(text);
        parcel.writeByte((byte) (check ? 1 : 0));
        ;
    }

    public static final Parcelable.Creator<ImageTextCheckbox> CREATOR = new Parcelable.Creator<ImageTextCheckbox>() {
        @Override
        public ImageTextCheckbox createFromParcel(Parcel source) {
            return new ImageTextCheckbox(source);
        }

        @Override
        public ImageTextCheckbox[] newArray(int size) {
            return new ImageTextCheckbox[size];
        }
    };

    public void setImage(int image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public boolean getCheck() {
        return check;
    }

}
