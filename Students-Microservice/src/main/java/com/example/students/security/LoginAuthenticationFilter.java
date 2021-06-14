package com.example.students.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.students.entities.Student;
import com.example.students.models.StudentLoginRequestModel;
import com.example.students.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private StudentService studentService;
	private Environment environment;
	
	public LoginAuthenticationFilter(StudentService studentService, Environment environment,
			AuthenticationManager authenticationManager) {
		this.studentService = studentService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			StudentLoginRequestModel studentLogin = new ObjectMapper().readValue(request.getInputStream(),
					StudentLoginRequestModel.class);
			
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken
					(studentLogin.getUserName(), studentLogin.getPassword(), new ArrayList<>()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		String userName = ((User) authResult.getPrincipal()).getUsername();
		Student student = studentService.getStudentByUserName(userName);
		
		String token = Jwts.builder().setSubject(student.getFirstName())
				.setExpiration(new Date(System.currentTimeMillis() + 
						Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
		response.addHeader("token", token);
	}
	
	
}
