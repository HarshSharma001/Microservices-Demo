package com.example.students.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.students.services.StudentService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private StudentService studentService;
	private Environment environment;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public SecurityConfig(StudentService studentService, Environment environment,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.studentService = studentService;
		this.environment = environment;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress("192.168.1.7").
		and().addFilter(getLoginAuthenticationFilter());
	}

	private LoginAuthenticationFilter getLoginAuthenticationFilter() throws Exception{
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter(studentService, environment,
				authenticationManager());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(studentService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	
}
