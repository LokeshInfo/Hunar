package com.ics.hunar.model.features;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Featured_Courses_Data {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("maincat_id")
    @Expose
    public String maincat_id;

    @SerializedName("language_id")
    @Expose
    public String language_id;

    @SerializedName("section_name")
    @Expose
    public String section_name;

    @SerializedName("subcategory_name")
    @Expose
    public String subcategory_name;

    @SerializedName("image")
    @Expose
    public String image;

    @SerializedName("trending_1")
    @Expose
    public String trending_1;

    @SerializedName("trending_2")
    @Expose
    public String trending_2;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("row_order")
    @Expose
    public String row_order;

    public String getId() {
        return id;
    }

    public String getMaincat_id() {
        return maincat_id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public String getImage() {
        return image;
    }

    public String getTrending_1() {
        return trending_1;
    }

    public String getTrending_2() {
        return trending_2;
    }

    public String getStatus() {
        return status;
    }

    public String getRow_order() {
        return row_order;
    }
}
