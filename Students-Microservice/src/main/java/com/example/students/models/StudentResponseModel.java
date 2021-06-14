package com.example.students.models;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class StudentResponseModel implements Serializable {
	private String firstName, lastName;
	private int result;
}	
