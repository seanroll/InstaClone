package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Getter;

@Getter
public class Reptile extends Animal{

    private String type = "reptile";

    public Reptile(String n, String s, int a, String d){
        super(n, s, a, d);
        this.type = "reptile";
    }
    @Override
    public String toString() {
        return  type + " " + super.toString();
    }
}
