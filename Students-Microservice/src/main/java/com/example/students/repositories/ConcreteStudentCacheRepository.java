package com.example.students.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.students.businesslogic.StudentTracker;
import com.example.students.models.StudentResponseModel;
import com.example.students.services.StudentService;

@Repository
public class ConcreteStudentCacheRepository implements AbstractStudentCacheRepository {

	private int oldStudentCount = 0;

	@Autowired
	private StudentTracker studentTracker;

	@Autowired
	private StudentService studentService;

	@Autowired
	private RedisTemplate<String, StudentResponseModel> redisTemplate;

	private HashOperations hashOperations;

	@Override
	public List<StudentResponseModel> fetchLeaderBoard() {
		hashOperations = redisTemplate.opsForHash();
		int currentStudentCount = studentTracker.getCurrentStudentsCount();

		if (oldStudentCount == currentStudentCount) {
			System.out.println("returned via cache.....");
			Map<String, StudentResponseModel> response = hashOperations.entries("Student");
			List<StudentResponseModel> leaderboardList = new ArrayList<>();

			for (int i = 1; i <= 10; i++) {
				leaderboardList.add(response.get(i));
			}

			return leaderboardList; // Return from cache
		}

		else {
			List<StudentResponseModel> studentsResponse = studentService.getLeaderBoard(); // DB Call
			int id = 1;
			for (StudentResponseModel student : studentsResponse) {
				hashOperations.put("Student", id, student);
				id += 1;
			}
			oldStudentCount = currentStudentCount;
			System.out.println("returned via DB Call....");
			return studentsResponse;
		}
	}
	

}
