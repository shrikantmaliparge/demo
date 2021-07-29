package com.sogeti.filmland.demo.model;

import com.google.gson.Gson;
import com.sogeti.filmland.demo.Constant.ConstantValue;
import com.sogeti.filmland.demo.Constant.Path;
import com.sogeti.filmland.demo.dao.LoginDatabase;
import com.sogeti.filmland.demo.dao.ServiceDetails;
import com.sogeti.filmland.demo.dao.SubscribedServices;
import com.sogeti.filmland.demo.service.RestService;
import com.sogeti.filmland.demo.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(ConstantValue.MAPPING_URI)
public class ServiceController {

	private static final Logger FILMLAND_LOG = LoggerFactory.getLogger("FILMLAND_LOG");


	@Autowired(required=true)
	private UserService userService;

	@Autowired
	private RestService restService;

	//This rest method is used to add user to the database

	@PostMapping(path = Path.SIGNUP, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addUser(@RequestBody LoginDatabaseRequest login) {
		boolean result = true;
		ResponseModel messageResponse = null;
		try {

			result = userService.saveUser(login);
			if (result == true) {
				messageResponse = new ResponseModel(HttpStatus.OK.toString(), ConstantValue.SUCCESSFULLY_CREATED);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
			} else {
				messageResponse = new ResponseModel(HttpStatus.CONFLICT.toString(), ConstantValue.EXIST);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.CONFLICT);
			}
		} catch (ServiceException e) {
			FILMLAND_LOG.error(e.getMessage());
		}
		return new ResponseEntity<String>(new Gson().toJson(ConstantValue.BANDWIDTH), HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
	}

	//Fetch all the ServiceDetail list available and subscribed
		@GetMapping(path = Path.DETAILS)
		public ResponseEntity<ServiceDetails> fetchUserDetails(@RequestParam(value = "name") String name,
				@RequestHeader(value = "apiKey") String apiKey) {
			String userName = userService.getUserByAPIKey(apiKey);
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(userName)) {
				if (null != name) {
					FILMLAND_LOG.info("Services: {} Successfully fetched all Services",
							restService.fetchAvailableServices(userName));
					return new ResponseEntity<ServiceDetails>(restService.fetchAvailableServices(userName), HttpStatus.OK);
				} else {
					FILMLAND_LOG.info("Services: {} No Services to be fetched");
					return new ResponseEntity<ServiceDetails>(HttpStatus.OK);
				}
			} else {
				FILMLAND_LOG.error("Services: {} Not Authorized ", ConstantValue.UNAUTHORIZED_MESSAGE);
				return new ResponseEntity<ServiceDetails>(HttpStatus.UNAUTHORIZED);
			}
		}

	@PostMapping(path = Path.SUBSCRIBE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> subscribe(@RequestBody NewSusbscriptionRequest subscribption,
											@RequestHeader(value = "apiKey") String apiKey) {
		String userName = userService.getUserByAPIKey(apiKey);
		ResponseModel messageResponse = null;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(userName)) {
			boolean isAlreadyExists = Boolean.FALSE;

			List<SubscribedServicesResponse> subscribedServiceByName = restService.getSubscribedServiceByName(userName);
			for (SubscribedServicesResponse subscribedService : subscribedServiceByName) {
				if (subscribption.getName().equals(subscribedService.getName())) {
					isAlreadyExists = true;
				}
			}
			if (!isAlreadyExists) {
				SubscribedServices subscribedService = new SubscribedServices(userName, subscribption.getName(), "1",
						"10", new Date(), new Date());
				restService.saveSubscribedService(subscribedService);
				messageResponse = new ResponseModel("Success" + HttpStatus.OK,
						" Subscription item " + subscribedService.getName() + " has been added to the bucket list of "
								+ subscribedService.getUser_name());
				FILMLAND_LOG.info("Services: {} Successfully subscribed services ", subscribedService.getUser_name());
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
			} else {
				messageResponse = new ResponseModel("Failed" + HttpStatus.CONFLICT,
						"Requested Subscrition is already subscribed");
				FILMLAND_LOG.warn("Services: {} Requested Subscrition is already subscribed ");
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.CONFLICT);
			}
		} else {
			messageResponse = new ResponseModel("Failed" + HttpStatus.UNAUTHORIZED, ConstantValue.UNAUTHORIZED_MESSAGE);
			FILMLAND_LOG.error("Services: {} Not Authorized ", ConstantValue.UNAUTHORIZED_MESSAGE);
			return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.UNAUTHORIZED);
		}
	}
	@PostMapping(path = Path.SHARESUBSCRIBE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> ShareSubscription(@RequestBody ShareSubscriptionRequest shareSubscriptionRequest,
													@RequestHeader(value = "apiKey") String apiKey) {
		String userName = userService.getUserByAPIKey(apiKey);
		LoginDatabase registeredDetails = userService.getDetailsByName(shareSubscriptionRequest.getFriend());
		ResponseModel messageResponse = null;
		if (null != registeredDetails) {
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(userName)) {
				boolean isExists = Boolean.FALSE;
				List<SubscribedServicesResponse> subscribedServiceByName = restService
						.getSubscribedServiceByName(userName);
				for (SubscribedServicesResponse subscribedService : subscribedServiceByName) {
					if (shareSubscriptionRequest.getServiceName().equals(subscribedService.getName())) {
						isExists = true;
					}
				}
				if (isExists) {
					restService.updateSubscribedService(shareSubscriptionRequest.getFriend(),
							shareSubscriptionRequest.getServiceName(), userName);
					messageResponse = new ResponseModel("Success" + HttpStatus.OK,
							" Successfully shared the item to " + shareSubscriptionRequest.getFriend());
					FILMLAND_LOG.info("Services: {} Successfully shared the services ",
							shareSubscriptionRequest.getFriend());
					return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
				} else {
					messageResponse = new ResponseModel("Not Available" + HttpStatus.BAD_REQUEST,
							"Requested Subscrition is not available with the existing subscriber :" + userName);
					FILMLAND_LOG
							.warn("Services: {} Requested Subscrition is not available with the existing subscriber");
					return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.BAD_REQUEST);
				}
			} else {
				messageResponse = new ResponseModel("Failed" + HttpStatus.UNAUTHORIZED,
						ConstantValue.UNAUTHORIZED_MESSAGE);
				FILMLAND_LOG.error("Services: {} Not Authorized ", ConstantValue.UNAUTHORIZED_MESSAGE);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.UNAUTHORIZED);
			}
		} else {
			messageResponse = new ResponseModel("Failed" + HttpStatus.BAD_REQUEST,
					"Requested shared friend not available");
			FILMLAND_LOG.error("Services: {} Requested shared friend not available ",
					shareSubscriptionRequest.getFriend());
			return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = Path.LOGIN, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> validateUser(@RequestBody LoginDatabaseRequest user)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		boolean result = true;
		ResponseModel messageResponse = null;
		try {
			result = userService.validateUser(user);
			if(result==true)
			{
				messageResponse = new ResponseModel(HttpStatus.OK.toString(), ConstantValue.SUCCESSFUL_LOGIN);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.OK);
			}
			else
			{
				messageResponse = new ResponseModel(HttpStatus.CONFLICT.toString(), ConstantValue.UNSUCCESSFUL_LOGIN);
				return new ResponseEntity<String>(new Gson().toJson(messageResponse), HttpStatus.CONFLICT);
			}
		} catch (ServiceException e) {
			FILMLAND_LOG.error(e.getMessage());
		}
		return new ResponseEntity<String>(new Gson().toJson(ConstantValue.BANDWIDTH), HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
	}



}
