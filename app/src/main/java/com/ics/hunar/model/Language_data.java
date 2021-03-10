package com.ics.hunar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language_data {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("language")
    @Expose
    public String language;

    @SerializedName("status")
    @Expose
    public String status;

}
