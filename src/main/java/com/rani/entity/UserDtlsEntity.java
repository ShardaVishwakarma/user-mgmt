package com.rani.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name="USER_ACCOUNT")
public class UserDtlsEntity {

	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="FIRST_NAME")
	private String fName;
	
	@Column(name="LAST_NAME")
	private String lName;
	
	@Column(name="USER_EMAIL", unique=true)
	private String email;
	
	@Column(name="USER_PWD")
	private String pwd;
	
	@Column(name="USER_MOBILE")
	private long mobileno;
	
	@Column(name="DOB")
	private LocalDate dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="CITY_ID")
	private Integer cityId;
	
	@Column(name="STATE_ID")
	private Integer stateId;
	
	@Column(name="COUNTRY_ID")
	private Integer countryId;
	
	@Column(name="ACC_STATUS")
	private String accStatus;
	
	@Column(name="CREATED_DATE", updatable=false)
	//@CreationTimestamp
	private String createdDate;
	
	@Column(name="UPDATED_DATE", insertable=false)
	//@UpdateTimestamp
	private String updatedDate;
	
	
	
	
	
}
