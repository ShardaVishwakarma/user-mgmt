package com.rani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rani.entity.StateMasterEntity;

public interface StateMasterRepo extends JpaRepository<StateMasterEntity, Integer> {

	public List<StateMasterEntity> findByCountryId(int countryId);
	
	
}
