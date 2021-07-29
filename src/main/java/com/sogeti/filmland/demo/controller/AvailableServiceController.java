package com.sogeti.filmland.demo.controller;


import com.sogeti.filmland.demo.dao.AvailableServices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AvailableServiceController extends CrudRepository<AvailableServices, String> {

	@Query("SELECT new com.sogeti.filmland.demo.model.AvailableServicesResponse(s.name, s.availableContent, s.price) FROM AvailableServices s where s.user_name =:userName")
	public List<?> findAvailableServiceByName(@Param("userName") String userName);

}
