package com.example.students.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student {

	@NotNull(message = "firstName - lastName can't be null")
	@Size(min = 3, max = 50)
	private String firstName, lastName;

	@Id
	@Size(max = 30)
	private String userName;

	@NotNull
	@Size(min = 8, max = 64)
	private String password;

	@NotNull
	private long phoneNo;
	
	@Min(0)
	@Max(300)
	private int result;

}
