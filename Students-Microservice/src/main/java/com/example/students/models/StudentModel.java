package com.example.students.models;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
public class StudentModel {
	private String firstName, lastName;
	private long phoneNo;
}
