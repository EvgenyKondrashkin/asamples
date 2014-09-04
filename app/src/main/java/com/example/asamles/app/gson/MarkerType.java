package com.example.asamles.app.gson;


import android.os.Parcel;
import android.os.Parcelable;

public class MarkerType implements Parcelable {
    private int type;
    private String tag;
    private String image;

    public static final Parcelable.Creator<MarkerType> CREATOR = new Parcelable.Creator<MarkerType>() {
        @Override
        public MarkerType createFromParcel(Parcel source) {
            return new MarkerType(source);
        }

        @Override
        public MarkerType[] newArray(int size) {
            return new MarkerType[size];
        }
    };

    public MarkerType() {
    }

    public MarkerType(Parcel source) {
        type = source.readInt();
        tag = source.readString();
        image = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(tag);
        parcel.writeString(image);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MarkerType{" +
                "type=" + type +
                ", tag='" + tag + '\'' +
                ", resource=" + image +
                '}';
    }
}
