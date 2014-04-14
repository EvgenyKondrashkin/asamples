package com.example.asamles.app.db;

import android.os.Parcel;
import android.os.Parcelable;


public class Animals implements Parcelable {
    private int _id;
    private String name;
    private String content;
    private int type;
    private String img;

    public Animals() {
    }

    public Animals(int _id, String name, String content, int type, String img) {
        this._id = _id;
        this.name = name;
        this.content = content;
        this.type = type;
        this.img = img;
    }

    public Animals(Parcel source) {

        _id = source.readInt();
        name = source.readString();
        content = source.readString();
        type = source.readInt();
        img = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
        parcel.writeString(name);
        parcel.writeString(content);
        parcel.writeInt(type);
        parcel.writeString(img);
    }

    public static final Parcelable.Creator<Animals> CREATOR = new Parcelable.Creator<Animals>() {
        @Override
        public Animals createFromParcel(Parcel source) {
            return new Animals(source);
        }

        @Override
        public Animals[] newArray(int size) {
            return new Animals[size];
        }
    };

    public void setId(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getImg() {
        return img;
    }


}
