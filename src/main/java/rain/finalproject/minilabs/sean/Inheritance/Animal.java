package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
public abstract class Animal {
    private String name;
    private String species;
    private int age;
    private String diet;
    private String type;

    public Animal(String n, String s, int a, String d){
        name = n;
        species = s;
        age = a;
        diet = d;
    }

    @Override
    public String toString() {
        return name + " " + species + " " + age + " " + diet;
    }
}
