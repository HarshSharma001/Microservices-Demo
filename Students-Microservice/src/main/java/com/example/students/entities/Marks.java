package com.example.students.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
/**
 * A class which will be representing diff diff marks of an individual student in diff diff exam
 * modules or diff diff exams we can say
 * @author adda247
 *
 */
public class Marks {
	@NotNull(message = "marks can't be null")
	@Min(value = 0)
	@Max(value = 100)
	private int techMarks, aptitudeMarks, reasoningMarks;
	
	public int getTotal() {
		return this.techMarks + this.aptitudeMarks + this.reasoningMarks;
	}
}
