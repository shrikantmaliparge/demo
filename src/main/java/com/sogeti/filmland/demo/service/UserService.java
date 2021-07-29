package com.sogeti.filmland.demo.service;

import com.sogeti.filmland.demo.Constant.ConstantValue;
import com.sogeti.filmland.demo.controller.AvailableServiceController;
import com.sogeti.filmland.demo.controller.UserController;
import com.sogeti.filmland.demo.dao.LoginDatabase;
import com.sogeti.filmland.demo.model.LoginDatabaseRequest;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class UserService {
	
	private static final Logger FILMLAND_LOG = LoggerFactory.getLogger("FILMLAND_LOG");

	@Autowired
	private UserController userController;

	@Autowired
	private AvailableServiceController resultController;

	public Iterable<LoginDatabase> getUserDetails() {
		return userController.findAll();
	}
	

	public boolean saveUser(LoginDatabaseRequest login) throws ServiceException {

		try {
			LoginDatabase registeringDetails = new LoginDatabase();
			registeringDetails.setEmail_id(login.getEmail_id());
			registeringDetails.setName(login.getName());
			registeringDetails.setPassword(login.getPassword());
			registeringDetails.setAutoizationKey(String.format(ConstantValue.APIKEY_FORMAT, login.getEmail_id(), login.getName()));
			userController.save(registeringDetails);
		} catch (Exception e) {
			FILMLAND_LOG.error("Specific Email ID: {} Exists", login.getEmail_id());
			return false;
		}
		FILMLAND_LOG.info("User Email ID: {}  Successfully registered in the database", login.getEmail_id());
		return true;

	}
	public String getUserByAPIKey(String apiKey) {
		return userController.findNameByApiKey(apiKey);
	}

	public LoginDatabase getDetailsByName(String username) {
		return userController.findDetailsByName(username);
	}

	public boolean validateUser(LoginDatabaseRequest user) throws ServiceException {
		LoginDatabase registeringDetails = new LoginDatabase();
		registeringDetails.setEmail_id(user.getEmail_id());
		registeringDetails.setPassword(user.getPassword());
		Iterable<LoginDatabase> userList = userController.findAll();
		for (LoginDatabase userLogin : userList) {
			if (user.getEmail_id().equals(userLogin.getEmail_id()) == true)
					 {
				FILMLAND_LOG.info("User: {} Successfully logged in", user.getEmail_id());
				return true;
			}
		}
		FILMLAND_LOG.info("User: {} Failed logged in", user.getEmail_id());
		return false;
	}

	}

