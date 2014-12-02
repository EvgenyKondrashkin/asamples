package com.example.asamles.app.charts.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {
    public long dt;
    public Temperature temp;
    public float pressure;
    public int humidity;
    public Weather[] weather;
    public float speed;
    public float deg;
    public float clouds;

    protected Day(Parcel in) {
        dt = in.readLong();
        temp = (Temperature) in.readValue(Temperature.class.getClassLoader());
        pressure = in.readFloat();
        humidity = in.readInt();
        weather = (Weather[])in.readParcelableArray(Weather.class.getClassLoader());
        speed = in.readFloat();
        deg = in.readFloat();
        clouds = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dt);
        dest.writeValue(temp);
        dest.writeFloat(pressure);
        dest.writeInt(humidity);
        dest.writeParcelableArray(weather, flags);
        dest.writeFloat(speed);
        dest.writeFloat(deg);
        dest.writeFloat(clouds);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}