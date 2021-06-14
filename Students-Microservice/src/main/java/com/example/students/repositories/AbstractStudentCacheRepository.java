package com.example.students.repositories;

import java.util.List;

import com.example.students.models.StudentResponseModel;

public interface AbstractStudentCacheRepository {
	
	/**
	 * method - to fetch the latest result of the exams and update the same on the LeaderBoard
	 * @return 
	 */
	List<StudentResponseModel> fetchLeaderBoard();
}
