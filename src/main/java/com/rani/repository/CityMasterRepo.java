package com.rani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rani.entity.CityMasterEntity;

public interface CityMasterRepo extends JpaRepository<CityMasterEntity, Integer> {

	public List<CityMasterEntity> findByStateId(int stateId);

}
