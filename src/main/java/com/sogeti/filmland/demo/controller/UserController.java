package com.sogeti.filmland.demo.controller;

import com.sogeti.filmland.demo.dao.LoginDatabase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserController extends CrudRepository<LoginDatabase, Integer>{

	@Query("SELECT name FROM LoginDatabase where autoizationKey =:apiKey")
    public String findNameByApiKey(@Param("apiKey") String apiKey);
	
	@Query("SELECT authorizedDetails FROM LoginDatabase authorizedDetails where name =:name")
	public LoginDatabase findDetailsByName(@Param("name") String name);
}
