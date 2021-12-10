package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Getter;

@Getter
public class Amphibian extends Animal{

    private String type = "amphibian";

    public Amphibian(String n, String s, int a, String d){
        super(n, s, a, d);
        this.type = "amphibian";
    }
    @Override
    public String toString() {
        return  type + " " + super.toString();
    }
}
