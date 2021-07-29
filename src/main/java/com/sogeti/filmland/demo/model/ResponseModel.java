package com.sogeti.filmland.demo.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {

	@SerializedName("status")
	private String status;

	@SerializedName("message")
	private String message;

	public ResponseModel(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
