package com.rani.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.rani.binding.LoginForm;
import com.rani.binding.UnlockAccForm;
import com.rani.binding.UserRegForm;

@Service
public interface UserMgmtService {

	// Login Functionality
	public String signIn(LoginForm loginForm);

	// Registering functionality

	public String emailCheck(String email);

	public Map<Integer, String> getCountry();

	public Map<Integer, String> getState(int countryId);

	public Map<Integer, String> getCity(int stateId);

	public String registerUser(UserRegForm userForm);

	// Unlock account functionality method

	public String unlockAccount(UnlockAccForm unlockAccForm);

	// forgot password

	public String forgotPwd(String email);

}
