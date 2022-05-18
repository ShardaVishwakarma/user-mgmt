package com.rani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rani.entity.CountryMasterEntity;

public interface CountryMasterRepo extends JpaRepository<CountryMasterEntity, Integer> {

}
