package com.example.asamles.app.charts;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.asamles.app.charts.model.City;
import com.example.asamles.app.charts.model.Coord;
import com.example.asamles.app.charts.model.Day;
import com.example.asamles.app.charts.model.Temperature;
import com.example.asamles.app.charts.model.Weather;
import com.google.gson.annotations.SerializedName;

public class OpenWeatherMapForecastResponse implements Parcelable {
    @SerializedName("cod")
    public int code;
    public float message;
    public City city;
    public int cnt;
    @SerializedName("list")
    public Day[] forecast;
    protected OpenWeatherMapForecastResponse(Parcel in) {
        code = in.readInt();
        message = in.readFloat();
        city = (City) in.readValue(City.class.getClassLoader());
        cnt = in.readInt();
        forecast = (Day[])in.readParcelableArray(Day.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeFloat(message);
        dest.writeValue(city);
        dest.writeInt(cnt);
        dest.writeParcelableArray(forecast, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OpenWeatherMapForecastResponse> CREATOR = new Parcelable.Creator<OpenWeatherMapForecastResponse>() {
        @Override
        public OpenWeatherMapForecastResponse createFromParcel(Parcel in) {
            return new OpenWeatherMapForecastResponse(in);
        }

        @Override
        public OpenWeatherMapForecastResponse[] newArray(int size) {
            return new OpenWeatherMapForecastResponse[size];
        }
    };
}
//public class OpenWeatherMapForecastResponse {
//    @SerializedName("cod")
//    public int code;
//    public float message;
//    public City city;
//    public int cnt;
//    @SerializedName("list")
//    public Day[] forecast;
//
//    private class City {
//        public int id;
//        public String name;
//        public Coord coord;
//        public String country;
//        public int population;
//
//        private class Coord {
//            public float lon;
//            public float lat;
//        }
//    }
//
//    private class Day {
//        public long dt;
//        public Temperature temp;
//        public float pressure;
//        public int humidity;
//        public Weather[] weather;
//        public float speed;
//        public float deg;
//        public float clouds;
//
//        private class Temp {
//            public float day;
//            public float min;
//            public float max;
//            public float night;
//            public float eve;
//            public float morn;
//        }
//
//        private class Weather {
//            public int id;
//            public String main;
//            public String description;
//            public String icon;
//        }
//    }
//}
