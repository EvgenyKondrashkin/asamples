package com.example.asamles.app.charts.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Coord implements Parcelable {
    public float lon;
    public float lat;

    protected Coord(Parcel in) {
        lon = in.readFloat();
        lat = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lon);
        dest.writeFloat(lat);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel in) {
            return new Coord(in);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };
}