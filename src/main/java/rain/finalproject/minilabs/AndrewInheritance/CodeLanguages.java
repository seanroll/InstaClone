package rain.finalproject.minilabs.AndrewInheritance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeLanguages extends Generic {
    public enum KeyType{title, name, age}
    public static KeyType key = KeyType.title;
    private String name;
    private int age;

    public CodeLanguages(String name, int age) {
        super.setType("CLS");
        this.name = name;
        this.age = age;
    }

    public static Generic[] CLData() {
        return new Generic[] {
                new CodeLanguages("C", 1972),
                new CodeLanguages("Java", 1996),
                new CodeLanguages("Python", 1991)
        };
    }

    @Override
    public String toString() {
        String output = "";
        switch (key) {
            case name:
                output += this.name;
                break;
            case age:
                output += "00" + this.age;
                output = output.substring(output.length()-2);
                break;
            case title:
                output += super.getType() + ": " + this.getName() + ": " + this.getAge();

        }
        return output;
    }
}
