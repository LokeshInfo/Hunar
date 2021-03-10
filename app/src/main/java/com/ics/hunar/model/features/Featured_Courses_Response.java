package com.ics.hunar.model.features;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Featured_Courses_Response {

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("featured_hunar_courses")
    @Expose
    public List<Featured_Courses_Data> data;

}
