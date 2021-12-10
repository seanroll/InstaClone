package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Getter;

@Getter
public class Mammal extends Animal{

    private String type = "mammal";

    public Mammal(String n, String s, int a, String d){
        super(n, s, a, d);
        this.type = "mammal";
    }
    @Override
    public String toString() {
        return  type + " " + super.toString();
    }
}
