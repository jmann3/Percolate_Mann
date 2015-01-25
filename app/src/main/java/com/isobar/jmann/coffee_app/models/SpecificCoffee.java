package com.isobar.jmann.coffee_app.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jmann on 1/22/15.
 *
 * POJO for JSON object elements.
 * implemented Parcelable interface to allow to be passed as extra
 */

public class SpecificCoffee implements Parcelable {

    private String desc;
    private String image_url;
    private String id;
    private String name;

    public SpecificCoffee() {

    }

    public SpecificCoffee(String desc, String image_url, String id, String name) {
        this.desc = desc;
        this.image_url = image_url;
        this.id = id;
        this.name = name;
    }

    public SpecificCoffee(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        this.desc = data[0];
        this.image_url = data[1];
        this.id = data[2];
        this.name = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[] {this.desc, this.image_url, this.id, this.name});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SpecificCoffee createFromParcel(Parcel in) {
            return new SpecificCoffee(in);
        }

        public SpecificCoffee[] newArray(int size) {
            return new SpecificCoffee[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
