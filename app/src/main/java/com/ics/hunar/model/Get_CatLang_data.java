package com.ics.hunar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_CatLang_data {
	private String image;
	private String featured;

	@SerializedName("category_name")
	@Expose
	private String categoryName;


	@SerializedName("trending_1")
	@Expose
	private String trending1;

	private String rowOrder;
	private String noOf;
	private String language;
	@SerializedName("id")
	@Expose
	private String id;
	private String languageId;
	private String trending2;

	public String getImage(){
		return image;
	}

	public String getFeatured(){
		return featured;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public String getTrending1(){
		return trending1;
	}


	public String getRowOrder(){
		return rowOrder;
	}

	public String getNoOf(){
		return noOf;
	}

	public String getLanguage(){
		return language;
	}

	public String getId(){
		return id;
	}

	public String getLanguageId(){
		return languageId;
	}

	public String getTrending2(){
		return trending2;
	}
}
