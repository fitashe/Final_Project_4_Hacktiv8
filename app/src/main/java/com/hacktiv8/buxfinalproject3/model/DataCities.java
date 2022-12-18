package com.hacktiv8.buxfinalproject3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataCities implements Parcelable {
    private String id;
    private String city;
    private String terminal;

    public DataCities() {
    }

    public DataCities(String id, String city, String terminal) {
        this.id = id;
        this.city = city;
        this.terminal = terminal;
    }

    protected DataCities(Parcel in) {
        id = in.readString();
        city = in.readString();
        terminal = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(city);
        dest.writeString(terminal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataCities> CREATOR = new Creator<DataCities>() {
        @Override
        public DataCities createFromParcel(Parcel in) {
            return new DataCities(in);
        }

        @Override
        public DataCities[] newArray(int size) {
            return new DataCities[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
