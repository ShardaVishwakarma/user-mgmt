package com.rani.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rani.binding.UnlockAccForm;
import com.rani.service.UserMgmtService;

@RestController
public class UnlockAccRestController {

	@Autowired
	private UserMgmtService service;
	
	@PostMapping("/unlock")
	public String unlockAcc(@RequestBody UnlockAccForm unlockAccForm ) {
		return service.unlockAccount(unlockAccForm);
		
	}
}
