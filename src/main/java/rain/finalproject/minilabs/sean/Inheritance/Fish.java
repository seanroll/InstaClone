package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Getter;

@Getter
public class Fish extends Animal{

    private String type = "fish";

    public Fish(String n, String s, int a, String d){
        super(n, s, a, d);
        this.type = "fish";
    }
    @Override
    public String toString() {
        return  type + " " + super.toString();
    }
}
