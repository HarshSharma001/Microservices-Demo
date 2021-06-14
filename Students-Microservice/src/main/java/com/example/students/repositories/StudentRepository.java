package com.example.students.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.students.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	/**
	 * method to get a student corresponding to it's user-name
	 * 
	 * @param - userName the user-name of the student
	 * @return Student type instance
	 */
	Student findByUserName(String userName);

	/**
	 * method to delete a particular student with corresponding user-name
	 * 
	 * @param userName - the user-name of the student
	 */
	void deleteByUserName(String userName);

	/**
	 * method to get Student instance corresponding to the user-name
	 * 
	 * @param userName - String type value denoting the user-name of the student
	 * @return Student - a Student type instance
	 */
	Student getByUserName(String userName);

	/**
	 * method to get List<Student> of Student instances in Descending order
	 * 
	 * @return Student - a Student type instance
	 */
	@Query("from Student ORDER BY result DESC")
	List<Student> findAll();

	/**
	 * method to get the total number of students available in the DB
	 * 
	 * @return int - an integer type value representing the total number of students
	 *         available in the DB
	 */
	@Query(value = "SELECT COUNT(result) from student", nativeQuery = true)
	int findTotalNumberOfStudents();
}
