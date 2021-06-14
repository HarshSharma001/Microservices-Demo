package com.example.students.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.students.businesslogic.StudentTracker;
import com.example.students.entities.Student;
import com.example.students.models.StudentModel;
import com.example.students.models.StudentRequestModel;
import com.example.students.models.StudentResponseModel;
import com.example.students.repositories.AbstractStudentCacheRepository;
import com.example.students.services.StudentService;

/**
 * A class which will be behaving as the FrontController for my application
 * @author Harsh SHarma
 *
 */
@RestController
public class FrontController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentService studentService;

	@Autowired
	private AbstractStudentCacheRepository studentCacheRepo;

	@Autowired
	StudentTracker studentTracker;

	/**
	 * method - to get student corresponding to his/her user-name
	 * 
	 * @param userName - a String value denoting the user-name of the student
	 * @return
	 */
	@GetMapping(path = "/student/{userName}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public Student getStudent(@PathVariable String userName) {
		return studentService.getStudent(userName);
	}

	
	
	/**
	 * method - to get a List<Student> of students from DB
	 * 
	 * @return List<Student> instance
	 */
	@GetMapping(path = "/students", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<Student> getStudents() {
		return studentService.getAllStudents();
	}

	
	
	/**
	 * method - to delete student corresponding to his/her user-name
	 * 
	 * @param userName - a String value denoting the user-name of the student
	 * @return
	 */
	@DeleteMapping("/student/{userName}")
	public String deleteStudent(@PathVariable String userName) {
		return studentService.deleteStudent(userName);
	}

	
	
	/**
	 * method - to save a new Student into the DB
	 * 
	 * @param student - a student type instance
	 * @return a StudentModel type instance
	 */
	@PostMapping(path = "/student", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { "application/xml", "application/json" })
	public StudentModel saveStudent(@Valid @RequestBody StudentRequestModel student) {
		return studentService.saveStudent(student);
	}

	
	
	/**
	 * method - to save a List<Student> of Student into the DB in just one go
	 * 
	 * @param List<student> student - a student type List<Student> instance
	 * @return a String message denoting the status
	 */
	@PostMapping(path = "/students", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { "application/xml", "application/json" })
	public String saveAllStudent(@Valid @RequestBody List<StudentRequestModel> students) {
		return studentService.saveAllStudents(students);
	}


	
	/**
	 * method - to update a particular student corresponding to it's user-name
	 * 
	 * @param student - a Student type instance
	 * @return a StudentModel type instance
	 */
	@PutMapping(path = "/student", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { "application/xml", "application/json" })
	public StudentModel updateStudent(@Valid @RequestBody StudentRequestModel student) {
		return studentService.updateStudent(student);
	}

	
	
	/**
	 * method to trigger and display the leaderboard details, via maybe redis-cache
	 * or DB
	 * 
	 * @return List<StudentResponseModel> - an instance of list of
	 *         StudentResponseModel
	 */
	@GetMapping("/leaderboard")
	public List<StudentResponseModel> getLeaderBoard() {
		studentTracker.setCurrentStudentsCount(studentService.getSizeOfTheTable());
		return studentCacheRepo.fetchLeaderBoard();
	}

	
	
	/**
	 * method - to make the leaderboard getting updated after every 10 seconds
	 * interval
	 */
	@Scheduled(fixedDelay = 10000)
	public void refreshLeaderBoard() {
		studentTracker.setCurrentStudentsCount(studentService.getSizeOfTheTable());
		studentCacheRepo.fetchLeaderBoard();
	}
}
