package com.example.students.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.students.entities.Student;
import com.example.students.models.StudentModel;
import com.example.students.models.StudentRequestModel;
import com.example.students.models.StudentResponseModel;

@Component
public interface StudentService extends UserDetailsService {
	/**
	 * method to create and save one single student at a time
	 * @param student - Student type instance
	 * @return StudentModel instance
	 */
	StudentModel saveStudent(StudentRequestModel student);
	
	/**
	 * method to save a list of students into DB
	 * @param students
	 * @return A string value indicating the response
	 */
	String saveAllStudents(List<StudentRequestModel> students);
	
	/**
	 * method to update data of a particular student
	 * @param student
	 * @return StudentModel instance
	 */
	StudentModel updateStudent(StudentRequestModel student);
	
	/**
	 * method to delete data of a particular student
	 * @param userName - the user-name of the student to be removed 
	 * @return A string value indicating the response
	 */
	String deleteStudent(String userName);
	
	/**
	 * method to return a particular student corresponding to the user-name
	 * @param userName
	 * @return A Student Instance
	 */
	Student getStudent(String userName);
	
	/*
	 * method to get all students saved on DB
	 * @return List of all the students in the form of StudentModel
	 */
	List<Student> getAllStudents();
	
	/**
	 * method - to get the Student corresponding to the user-name
	 * @param userName - the string value representing 
	 * @return
	 */
	Student getStudentByUserName(String userName);
	
	/**
	 * method - to get the latest result for the leaderboard
	 * @return - List of student representing the top rank holders 
	 */
	List<StudentResponseModel> getLeaderBoard();
	
	/**
	 * method - to get the size of the table with respect to number of rows 
	 * @return int - integer type value denoting as the size of the table
	 */
	int getSizeOfTheTable();
}




