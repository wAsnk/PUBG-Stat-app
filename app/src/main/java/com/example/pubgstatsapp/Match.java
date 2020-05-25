package com.example.pubgstatsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {
    private String type;
    private String id;

    public Match(String type, String id) {
        this.type = type;
        this.id = id;
    }

    protected Match(Parcel in) {
        type = in.readString();
        id = in.readString();
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(id);
    }
}
