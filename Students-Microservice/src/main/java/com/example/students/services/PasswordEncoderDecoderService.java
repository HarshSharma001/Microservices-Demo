package com.example.students.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderDecoderService {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * method - to encrypt the provided password and return it
	 * @param password - the password provided to be encrypted
	 * @return String - a String type encrypted password 
	 */
	public String getEncryptedPassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}
	
	
}
