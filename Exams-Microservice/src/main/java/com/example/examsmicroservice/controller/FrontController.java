package com.example.examsmicroservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.examsmicroservice.model.StudentResponseModel;
import com.example.examsmicroservice.services.LeaderboardService;

@RestController
public class FrontController {
	
	@Autowired
	private LeaderboardService leaderboardService;
	
	@GetMapping("/leaderboard")
	public List<StudentResponseModel> getTopTenPositionHolders(){
		return leaderboardService.fetchLeaderboard();
	}
}
