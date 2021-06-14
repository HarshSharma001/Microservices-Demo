package com.example.examsmicroservice.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.examsmicroservice.model.StudentResponseModel;

@Service
public interface LeaderboardService {
	
	/**
	 * method - to fetch top 10 score holders in the exams
	 * @return List of StudentResponseModel
	 */
	List<StudentResponseModel> fetchLeaderboard();
}
