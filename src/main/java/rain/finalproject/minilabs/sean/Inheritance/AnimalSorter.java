package rain.finalproject.minilabs.sean.Inheritance;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;

@Getter
public class AnimalSorter {
    public ArrayList<Animal> animals;
    public String sortingCriteria;
    private ArrayList<Animal> output;

    public AnimalSorter(ArrayList<Animal> a, String sC){
        animals = a;
        sortingCriteria = sC;
        output = sortedArray();
    }

    public ArrayList<Animal> sortedArray(){
        switch (sortingCriteria){
            case "name":
                animals.sort(Comparator.comparing(Animal::getName));
                break;
            case "age":
                animals.sort(Comparator.comparing(Animal::getAge));
                break;
            case "species":
                animals.sort(Comparator.comparing(Animal::getSpecies));
                break;
            case "diet":
                animals.sort(Comparator.comparing(Animal::getDiet));
                break;
            case "type":
                animals.sort(Comparator.comparing(Animal::getType));
                break;
            default:
                break;
        }
        return animals;
    }

}
