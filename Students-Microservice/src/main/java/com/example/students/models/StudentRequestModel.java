package com.example.students.models;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.example.students.entities.Marks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class StudentRequestModel {
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

	@Embedded
	private Marks marks;

	public int getTotalMarks() {
		return marks.getTotal();
	}
}
