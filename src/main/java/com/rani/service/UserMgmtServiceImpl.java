package com.rani.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rani.binding.LoginForm;
import com.rani.binding.UnlockAccForm;
import com.rani.binding.UserRegForm;
import com.rani.entity.CityMasterEntity;
import com.rani.entity.CountryMasterEntity;
import com.rani.entity.StateMasterEntity;
import com.rani.entity.UserDtlsEntity;
import com.rani.repository.CityMasterRepo;
import com.rani.repository.CountryMasterRepo;
import com.rani.repository.StateMasterRepo;
import com.rani.repository.UserDtlsRepo;
import com.rani.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private CountryMasterRepo countryMasterRepo;

	@Autowired
	private StateMasterRepo stateMasterRepo;

	@Autowired
	private CityMasterRepo cityMasterRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String signIn(LoginForm loginForm) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());
		if (entity == null) {
			return "Invalid Credential";
		}
		if (entity != null && entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		return "Success";
	}

	@Override
	public String emailCheck(String email) {
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		if (entity == null) {
			return "Unique Email";
		}
		return "Duplicate Email";
	}

	@Override
	public Map<Integer, String> getCountry() {
		List<CountryMasterEntity> countries = countryMasterRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();

		for (CountryMasterEntity entity : countries) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}
		return countryMap;
	}

	@Override
	public Map<Integer, String> getState(int countryId) {
		List<StateMasterEntity> findById = stateMasterRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();

		for (StateMasterEntity entity : findById) {
			stateMap.put(entity.getStateId(), entity.getStateName());
		}
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCity(int stateId) {

		List<CityMasterEntity> findById = cityMasterRepo.findByStateId(stateId);
		Map<Integer, String> cityMap = new HashMap<>();

		for (CityMasterEntity entity : findById) {
			cityMap.put(entity.getCityId(), entity.getCityName());
		}
		return cityMap;
	}

	@Override
	public String registerUser(UserRegForm userForm) {
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(userForm, entity);
		entity.setAccStatus("LOCKED");

		entity.setPwd(generateRandomPwd());

		UserDtlsEntity saved = userDtlsRepo.save(entity);

		String email = userForm.getEmail();
		String subject = "User Registration....AshokIT";
		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";

		String body = readMailBodyContent(fileName, entity);

		boolean isSent = emailUtils.sendEmail(email, subject, body);

		if (saved.getUserId() != null && isSent) { // saved.getUserId() != null

			return "SUCCESS";

		}
		return "FAIL";

	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {

		if (!(unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd()))) {
			return "Password and New Passwrd should be same";
		}
		UserDtlsEntity emailAndPwd = userDtlsRepo.findByEmailAndPwd(unlockAccForm.getEmail(),
				unlockAccForm.getTempPwd());

		if (emailAndPwd == null) {
			return "Incorrect Temporary Password";
		}

		emailAndPwd.setPwd(unlockAccForm.getNewPwd());
		emailAndPwd.setAccStatus("UNLOCKED");
		userDtlsRepo.save(emailAndPwd);
		return "Account Unlocked";
	}

	@Override
	public String forgotPwd(String email) {
		UserDtlsEntity entity = new UserDtlsEntity();
		if (entity == null) {

			return "Invalid Email Id";
		}

		String fileName = "RECOVERED-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String body = readMailBodyContent(fileName, entity);
		String subject = "Recover....AshokIT";

		boolean isSent = emailUtils.sendEmail(email, subject, body);

		if (isSent) {

			return "Password Sent To registered EmailId";
		}
		return "Error";
	}

	public String generateRandomPwd() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public String readMailBodyContent(String fileName, UserDtlsEntity entity) {

		String mailBody = null;
		try {
			StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // reading first line data

			while (line != null) {
				sb.append(line); // appending line data to buffer object
				line = br.readLine(); // reading next line data
			}

			mailBody = sb.toString();
			mailBody = mailBody.replace("{FNAME}", entity.getFName());
			mailBody = mailBody.replace("{LNAME}", entity.getLName());
			mailBody = mailBody.replace("{TEMP-PWD}", entity.getPwd());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmail());

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return mailBody;
	}

}
