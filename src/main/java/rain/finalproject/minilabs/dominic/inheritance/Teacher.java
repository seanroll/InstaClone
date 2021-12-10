package rain.finalproject.minilabs.dominic.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Teacher extends Person {
	private String subject;

	public String toString() {
		return super.toString() + " is a(n) " + subject + " teacher.";
	}

	public Teacher(String firstName, String lastName, Integer age, String subject) {
		super(firstName, lastName, age);
		setSubject(subject);
	}

	public Teacher() {

	}
}
