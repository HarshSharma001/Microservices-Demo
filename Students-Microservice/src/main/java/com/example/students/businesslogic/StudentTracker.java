package com.example.students.businesslogic;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Scope(value = "singleton")
@Getter
@Setter
@NoArgsConstructor
@ToString
/**
 * A class which will keep the track of total number of students available in the DB
 * at any particular moment. 
 * 
 * @author Harsh Sharma - adda247
 *
 */
public class StudentTracker {
	
	private int currentStudentsCount;
}
