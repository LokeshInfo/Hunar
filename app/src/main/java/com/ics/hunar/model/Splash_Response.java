package com.ics.hunar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Splash_Response {

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("data")
    @Expose
    public List<Splash_data> data;

}
