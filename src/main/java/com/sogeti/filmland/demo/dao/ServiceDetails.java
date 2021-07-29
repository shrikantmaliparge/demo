package com.sogeti.filmland.demo.dao;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetails {

	private List<AvailableServices> availableServices;
	private List<com.sogeti.filmland.demo.dao.SubscribedServices> subscribedServices;

	public ServiceDetails(List<AvailableServices> availableServices, List<com.sogeti.filmland.demo.dao.SubscribedServices> subscribedServices) {
		super();
		this.availableServices = availableServices;
		this.subscribedServices = subscribedServices;
	}

	public List<AvailableServices> getAvailableServices() {
		if (this.availableServices == null) {
			this.availableServices = new ArrayList<>();
		}
		return availableServices;
	}

	public List<com.sogeti.filmland.demo.dao.SubscribedServices> getSubscribedServices() {
		if (this.subscribedServices == null) {
			this.subscribedServices = new ArrayList<>();
		}
		return subscribedServices;
	}

}
