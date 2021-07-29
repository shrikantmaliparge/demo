package com.sogeti.filmland.demo.service;

import com.sogeti.filmland.demo.controller.AvailableServiceController;
import com.sogeti.filmland.demo.controller.SubscribedServiceController;
import com.sogeti.filmland.demo.dao.AvailableServices;
import com.sogeti.filmland.demo.dao.ServiceDetails;
import com.sogeti.filmland.demo.dao.SubscribedServices;
import com.sogeti.filmland.demo.model.SubscribedServicesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestService {

	@Autowired
	private AvailableServiceController availableServiceController;
	
	@Autowired
	private SubscribedServiceController subscribedServiceController;
	
	public ServiceDetails fetchAvailableServices(String name) {
		List<AvailableServices> availableService = (List<AvailableServices>) availableServiceController.findAvailableServiceByName(name);
		List<SubscribedServices> subscribedService = (List<SubscribedServices>) subscribedServiceController.findSubScriberByName(name);
		return new ServiceDetails(availableService, subscribedService);
	}
	
	public List<SubscribedServices> getSubscribedService() {
		return (List<SubscribedServices>) subscribedServiceController.findAll();
	}
	
	public List<SubscribedServicesResponse> getSubscribedServiceByName(String userName) {
		return (List<SubscribedServicesResponse>) subscribedServiceController.findSubScriberByName(userName);
	}
	
	public void saveSubscribedService(SubscribedServices subscribedServices) {
		subscribedServiceController.save(subscribedServices);
	}
	public void updateSubscribedService(String newSubscriber, String subscribedName, String userName) {
		subscribedServiceController.updateNewSubscriber(newSubscriber, subscribedName, userName);
	}
}
