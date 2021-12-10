package rain.finalproject.minilabs.dominic.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Student extends Person {
	private Integer grade;

	public String toString() {
		return super.toString() + " is a grade " + grade + " student.";
	}

	public Student(String firstName, String lastName, Integer age, Integer grade) {
		super(firstName, lastName, age);
		setGrade(grade);
	}

	public Student() {

	}
}
