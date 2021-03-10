package com.ics.hunar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Splash_data {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("status")
    @Expose
    public String status;

}
