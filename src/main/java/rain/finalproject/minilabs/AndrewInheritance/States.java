package rain.finalproject.minilabs.AndrewInheritance;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class States extends Generic {
    public enum KeyType{title, name, population}
    public static States.KeyType key = States.KeyType.title;
    private String name;
    private int population;

    public States(String name, int population) {
        super.setType("STS");
        this.name = name;
        this.population = population;
    }

    public static Generic[] StatesData() {
        return new Generic[] {
                new CodeLanguages("California", 39512223),
                new CodeLanguages("Texas", 28995881),
                new CodeLanguages("Florida", 21477737),
                new CodeLanguages("New York", 19453561),
                new CodeLanguages("Illinois", 12671821),
                new CodeLanguages("Pennsylvania", 12801989)
        };
    }

    @Override
    public String toString() {
        String output = "";
        switch (key) {
            case name:
                output += this.name;
                break;
            case population:
                output += "00" + this.population;
                output = output.substring(output.length()-2);
                break;
            case title:
                output += super.getType() + ": " + this.getName() + ": " + this.getPopulation();

        }
        return output;
    }
}


