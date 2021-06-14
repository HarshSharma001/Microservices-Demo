package com.example.examsmicroservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.examsmicroservice.model.StudentResponseModel;
@Service
public class LeaderboardServiceImpl implements LeaderboardService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<StudentResponseModel> fetchLeaderboard() {
		String leaderboardURL = "http://STUDENTS-MS/leaderboard";
		ResponseEntity<List<StudentResponseModel>> lbResponse = restTemplate.exchange(
				leaderboardURL, HttpMethod.GET, null, 
				new ParameterizedTypeReference<List<StudentResponseModel>>() {});
		
		List<StudentResponseModel> topTenStudents = lbResponse.getBody();
		
		return topTenStudents;
	}

}
