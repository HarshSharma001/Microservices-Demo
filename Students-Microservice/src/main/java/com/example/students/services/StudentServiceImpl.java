package com.example.students.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.students.businesslogic.StudentTracker;
import com.example.students.entities.Student;
import com.example.students.models.StudentModel;
import com.example.students.models.StudentRequestModel;
import com.example.students.models.StudentResponseModel;
import com.example.students.repositories.StudentRepository;

@Component
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository studentRepositiry;
	@Autowired
	private PasswordEncoderDecoderService passwordEncoderDecoderService;
	
	@Autowired
	private StudentTracker studentTracker;
	
	@Override
	public StudentModel saveStudent(StudentRequestModel requestedStudent) {
		requestedStudent.setPassword(passwordEncoderDecoderService.
				getEncryptedPassword(requestedStudent.getPassword()));
		Student student = new ModelMapper().map(requestedStudent, Student.class);
		student.setResult(requestedStudent.getTotalMarks());
		studentRepositiry.save(student);
		studentTracker.setCurrentStudentsCount(studentTracker.getCurrentStudentsCount()+1);
		return new ModelMapper().map(student, StudentModel.class);
	}

	@Override
	public String saveAllStudents(List<StudentRequestModel> requestedStudents) {
		ArrayList<Student> students = new ArrayList<>();
		for(StudentRequestModel s: requestedStudents) {
			s.setPassword(passwordEncoderDecoderService.
					getEncryptedPassword(s.getPassword()));
			Student student = new ModelMapper().map(s, Student.class);
			student.setResult(s.getTotalMarks());
			students.add(student);
			studentTracker.setCurrentStudentsCount(studentTracker.getCurrentStudentsCount()+1);
		}
		studentRepositiry.saveAll(students);
		return "All students data is been saved successfully!";
	}

	@Override
	public StudentModel updateStudent(StudentRequestModel requestedStudent) {
		requestedStudent.setPassword(passwordEncoderDecoderService.
				getEncryptedPassword(requestedStudent.getPassword()));
		Student student = new ModelMapper().map(requestedStudent, Student.class);
		student.setResult(requestedStudent.getTotalMarks());
		
		Student oldData = studentRepositiry.findByUserName(student.getUserName());
		if(oldData == null) {
			StudentModel studentModel = new StudentModel();
			studentModel.setFirstName("No Such User Found");
			studentModel.setLastName("No Such User Found");
			studentModel.setPhoneNo(0L);
			return studentModel;
		}
		else {
			studentRepositiry.save(student);
			studentTracker.setCurrentStudentsCount(studentTracker.getCurrentStudentsCount()+1);
			return new ModelMapper().map(student, StudentModel.class);
		}
	}

	@Override
	public String deleteStudent(String userName) {
		Student currentData = studentRepositiry.findByUserName(userName);
		if(currentData == null) {
			return "No such user found !";
		}
		else {
			studentRepositiry.delete(currentData);
			studentTracker.setCurrentStudentsCount(studentTracker.getCurrentStudentsCount()-1);
			return "User with username '"+userName+"' is removed successfully !";
		}
	}

	@Override
	public Student getStudentByUserName(String userName) {
		return studentRepositiry.findByUserName(userName);
	}

	@Override
	public Student getStudent(String userName) {
		return studentRepositiry.getByUserName(userName);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepositiry.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepositiry.findByUserName(username);
		if(student == null) throw new UsernameNotFoundException("student not found!");
		
		return new User(student.getUserName(), student.getPassword(), 
				true, true, true, true, new ArrayList<>());
	}

	@Override
	public List<StudentResponseModel> getLeaderBoard() {
		List<Student> students = studentRepositiry.findAll();
		List<StudentResponseModel> response = new ArrayList<>();
		for(int i=0; i<10 && students.size()>=10; i++) {
			Student s = students.get(i);
			response.add(new ModelMapper().map(s, StudentResponseModel.class));
		}
		return response;
	}
	
	
	
	@Override
	public int getSizeOfTheTable() {
		return studentRepositiry.findTotalNumberOfStudents();
	}
	
	
}
