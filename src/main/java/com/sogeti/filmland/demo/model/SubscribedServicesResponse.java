package com.sogeti.filmland.demo.model;

import java.util.Date;

public class SubscribedServicesResponse {

	private String name;
	private String subscribedContent;
	private String price;
	private Date subscribedDate;

	public SubscribedServicesResponse() {
		super();
	}
	public SubscribedServicesResponse(String name, String subscribedContent, String price, Date subscribedDate) {
		super();
		this.name = name;
		this.subscribedContent = subscribedContent;
		this.price = price;
		this.subscribedDate = subscribedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubscribedContent() {
		return subscribedContent;
	}

	public void setSubscribedContent(String subscribedContent) {
		this.subscribedContent = subscribedContent;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getSubscribedDate() {
		return subscribedDate;
	}

	public void setSubscribedDate(Date subscribedDate) {
		this.subscribedDate = subscribedDate;
	}


}
