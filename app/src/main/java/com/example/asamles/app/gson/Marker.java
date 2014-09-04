package com.example.asamles.app.gson;

import android.os.Parcel;
import android.os.Parcelable;


public class Marker implements Parcelable {
    private int x;
    private int y;
    private int type;

    public static final Parcelable.Creator<Marker> CREATOR = new Parcelable.Creator<Marker>() {
        @Override
        public Marker createFromParcel(Parcel source) {
            return new Marker(source);
        }

        @Override
        public Marker[] newArray(int size) {
            return new Marker[size];
        }
    };

    public Marker() {
    }

    public Marker(Parcel source) {
        x = source.readInt();
        y = source.readInt();
        type = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(x);
        parcel.writeInt(y);
        parcel.writeInt(type);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
