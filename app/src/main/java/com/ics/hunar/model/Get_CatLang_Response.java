package com.ics.hunar.model;

import java.util.List;

public class Get_CatLang_Response {
	private List<Get_CatLang_data> data;
	private String error;

	public List<Get_CatLang_data> getData(){
		return data;
	}

	public String getError(){
		return error;
	}
}