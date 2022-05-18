package com.rani.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rani.binding.UserRegForm;
import com.rani.service.UserMgmtService;

@RestController
public class RegistrationRestController {

	@Autowired
	private UserMgmtService service;

	public String emailCheck(@PathVariable("emailId") String emailId) {

		return service.emailCheck(emailId);
	}

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {

		return service.getCountry();

	}

	@GetMapping("/states/{CountryId}")
	public Map<Integer, String> getStates(@PathVariable("CountryId") Integer CountryId) {

		return service.getState(CountryId);

	}

	@GetMapping("/cities/{StateId}")
	public Map<Integer, String> getCities(@PathVariable("StateId") Integer StateId) {

		return service.getCity(StateId);

	}

	@PostMapping("/user")
	public String userReg(@RequestBody UserRegForm form) {
		return service.registerUser(form);

    	
    }
}
