package com.rani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rani.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity , Integer> {

	public UserDtlsEntity findByEmailAndPwd(String email,String pwd);
	
	public UserDtlsEntity findByEmail(String email);
}
