package rain.finalproject.minilabs.dominic.inheritance;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String firstName;
	private String lastName;
	private Integer age;

	public Person(String firstName, String lastName, Integer age) {
		setFirstName(firstName);
		setLastName(lastName);
		setAge(age);
	}

	public Person() {

	}

	public String toString() {
		return firstName + " " + lastName + " (age " + age + ")";
	}
}
