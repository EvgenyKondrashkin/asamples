package com.example.asamles.app.charts.model;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    public int id;
    public String name;
    public Coord coord;
    public String country;
    public int population;
    protected City(Parcel in) {
        id = in.readInt();
        name = in.readString();
        coord = (Coord) in.readValue(Coord.class.getClassLoader());
        country = in.readString();
        population = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeValue(coord);
        dest.writeString(country);
        dest.writeInt(population);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}