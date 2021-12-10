package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Getter;

@Getter
public class Bird extends Animal{

    private String type = "bird";

    public Bird(String n, String s, int a, String d){
        super(n, s, a, d);
        this.type = "bird";
    }
    @Override
    public String toString() {
        return  type + " " + super.toString();
    }
}
