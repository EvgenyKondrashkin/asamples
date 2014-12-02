package com.example.asamles.app.charts.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Temperature implements Parcelable {
    public float day;
    public float min;
    public float max;
    public float night;
    public float eve;
    public float morn;

    protected Temperature(Parcel in) {
        day = in.readFloat();
        min = in.readFloat();
        max = in.readFloat();
        night = in.readFloat();
        eve = in.readFloat();
        morn = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(day);
        dest.writeFloat(min);
        dest.writeFloat(max);
        dest.writeFloat(night);
        dest.writeFloat(eve);
        dest.writeFloat(morn);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Temperature> CREATOR = new Parcelable.Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel in) {
            return new Temperature(in);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };
}