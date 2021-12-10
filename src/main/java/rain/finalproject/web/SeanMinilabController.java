package rain.finalproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rain.finalproject.minilabs.Andrew.sort.SortingMethods;
import rain.finalproject.minilabs.AndrewInheritance.CodeLanguages;
import rain.finalproject.minilabs.sean.Inheritance.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Controller
public class SeanMinilabController {

    @GetMapping("/SortSean")
    public String sort(@RequestParam(name = "type", required = false, defaultValue = "int") String type,
                      @RequestParam(name = "size", required = false, defaultValue = "10") String size,
                      Model model) {

        //System.out.println(type);
        //System.out.println(size);


        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int listSize = Integer.parseInt(size);
        ArrayList<Integer> all = new ArrayList<>();
       for (int i=0; i<listSize;i++){
           all.add(i);
       }
       model.addAttribute("all",all);
        double insertionTime = 0;
        double bubbleTime = 0;
        double selectionTime = 0;

        //create list
        if(type.equals("int")){
            ArrayList<Integer> intList = new ArrayList(0);
            //adds integers between 1-1000
            for (int i = 0; i < listSize; i++){
                intList.add((int) (1000 * Math.random()));
            }
            model.addAttribute("list", intList.toArray());
            ArrayList<Integer> insertionList = new ArrayList<Integer>(intList);
            ArrayList<Integer> bubbleList = new ArrayList<Integer>(intList);
            ArrayList<Integer> selectionList = new ArrayList<Integer>(intList);

            bubbleTime = GetFunctionRunTime(() -> intBubbleSort(bubbleList));
            insertionTime =  insertionTime = GetFunctionRunTime(() -> intInsertionSort(insertionList));
            selectionTime = GetFunctionRunTime(() -> intSelectionSort(selectionList));
            model.addAttribute("sortedList",insertionList.toArray());
        }
        else if(type.equals("String")){
            ArrayList<String> stringList = new ArrayList(0);
            for (int i = 0; i < listSize; i++){
                stringList.add(Character.toString(alphabet.charAt((int)(52*Math.random()))) + Character.toString(alphabet.charAt((int)(52*Math.random()))) + Character.toString(alphabet.charAt((int)(52*Math.random()))));
            }
            model.addAttribute("list", stringList.toArray());
            ArrayList<String> insertionList = new ArrayList<String>(stringList);
            ArrayList<String> bubbleList = new ArrayList<String>(stringList);
            ArrayList<String> selectionList = new ArrayList<String>(stringList);
            bubbleTime = GetFunctionRunTime(() -> stringBubbleSort(bubbleList));
            insertionTime = GetFunctionRunTime(() -> stringInsertionSort(insertionList));
            selectionTime = GetFunctionRunTime(() -> stringSelectionSort(selectionList));
            model.addAttribute("sortedList",bubbleList.toArray());
        }

        model.addAttribute("bubbleTime", bubbleTime);
        model.addAttribute("insertionTime", insertionTime);
        model.addAttribute("selectionTime", selectionTime);

        return "Sort/SortSean";
    }

    private void intBubbleSort(ArrayList<Integer> a) {
        boolean sorted = false;
        int temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i) > a.get(i+1)) {
                    temp = a.get(i);
                    a.set(i, a.get(i+1)) ;
                    a.set(i+1, temp);
                    sorted = false;
                }
            }
        }
    }

    private void stringBubbleSort(ArrayList<String> a) {
        boolean sorted = false;
        String temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < a.size() - 1; i++) {
                if (a.get(i).compareTo(a.get(i+1)) > 0) {
                    temp = a.get(i);
                    a.set(i, a.get(i+1)) ;
                    a.set(i+1, temp);
                    sorted = false;
                }
            }
        }
    }

    private void intSelectionSort(ArrayList<Integer> a) {
        int smallInt = 0;
        int j=0;
        int smallIntIndex = 0;

        for(int i=1;i<a.size();i++){

            smallInt = a.get(i-1);
            smallIntIndex = i-1;

            for(j=i;j<a.size();j++){
                if(a.get(j)<smallInt){
                    smallInt = a.get(j);
                    smallIntIndex = j;
                }
            }

            int temp = a.get(smallIntIndex);
            a.set(smallIntIndex, a.get(i-1));
            a.set(i-1, temp);
        }
    }

    private void stringSelectionSort(ArrayList<String> a) {
        String smallString = "";
        int j=0;
        int smallStringIndex = 0;

        for(int i=1;i<a.size();i++){

            smallString = a.get(i-1);
            smallStringIndex = i-1;

            for(j=i;j<a.size();j++){
                if(a.get(j).compareTo(smallString) > 0){
                    smallString = a.get(j);
                    smallStringIndex = j;
                }
            }

            String temp = a.get(smallStringIndex);
            a.set(smallStringIndex, a.get(i-1));
            a.set(i-1, temp);
        }
    }

    //insertion sort algo
    public static void intInsertionSort(ArrayList<Integer> array) {
        for (int j = 1; j < array.size(); j++) {
            Integer current = array.get(j);
            int i = j-1;
            while ((i > -1) && (array.get(i) > current)) {
                array.set(i+1, array.get(i));
                i--;
            }
            array.set(i+1, current);
        }
    }

    public static void stringInsertionSort(ArrayList<String> array) {
        for (int j = 1; j < array.size(); j++) {
            String current = array.get(j);
            int i = j-1;
            while ((i > -1) && (array.get(i).compareTo(current) > 0)) {
                array.set(i+1, array.get(i));
                i--;
            }
            array.set(i+1, current);
        }
    }

    //thanks dominic!
    public double GetFunctionRunTime(Runnable Function) {
        long Start = System.nanoTime();
        Function.run();
        return (System.nanoTime() - Start) / 1000000.0;
    }


    @GetMapping("/LLSean")
    public String llVsAl(@RequestParam(name = "option", required = false, defaultValue = "none") String option,
                         Model model) {

        System.out.println("start initialization");

        //ll and al initialization
        LinkedList ll5 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            ll5.add(100 * Math.random());
        }
        LinkedList ll10 = new LinkedList();
        for (int i = 0; i < 10; i++) {
            ll10.add(100 * Math.random());
        }
        LinkedList ll100 = new LinkedList();
        for (int i = 0; i < 100; i++) {
            ll100.add(100 * Math.random());
        }
        ArrayList al5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            al5.add(100 * Math.random());
        }
        ArrayList al10 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            al10.add(100 * Math.random());
        }
        ArrayList al100 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            al100.add(100 * Math.random());
        }

        String alComplexity = "";
        String llComplexity = "";
        double ll5T = 0;
        double ll10T = 0;
        double ll100T = 0;
        double al5T = 0;
        double al10T = 0;
        double al100T = 0;


        switch (option) {
            case "insertHead":
                ll5T = GetFunctionRunTime(() -> {
                    ll5.addFirst(100 * Math.random());
                });
                al5T = GetFunctionRunTime(() -> {
                    al5.add(0, 100 * Math.random());
                });

                ll10T = GetFunctionRunTime(() -> {
                    ll10.addFirst(100 * Math.random());
                });
                al10T = GetFunctionRunTime(() -> {
                    al10.remove(0);
                });

                ll100T = GetFunctionRunTime(() -> {
                    ll100.addFirst(100 * Math.random());
                });
                al100T = GetFunctionRunTime(() -> {
                    al100.remove(0);
                });

                alComplexity = "O(1)";
                llComplexity = "O(n)";
                break;
            case "deleteHead":
                ll5T = GetFunctionRunTime(ll5::removeFirst);
                al5T = GetFunctionRunTime(() -> {
                    al5.remove(0);
                });

                ll10T = GetFunctionRunTime(ll10::removeFirst);
                al10T = GetFunctionRunTime(() -> {
                    al10.remove(0);
                });

                ll100T = GetFunctionRunTime(ll100::removeFirst);
                al100T = GetFunctionRunTime(() -> {
                    al100.remove(0);
                });

                alComplexity = "O(n)";
                llComplexity = "O(1)";
                break;
            case "insertMid":
                ll5T = GetFunctionRunTime(() -> {
                    ll5.add(2, 100 * Math.random());
                });
                al5T = GetFunctionRunTime(() -> {
                    al5.add(2, 100 * Math.random());
                });

                ll10T = GetFunctionRunTime(() -> {
                    ll10.add(5, 100 * Math.random());
                });
                al10T = GetFunctionRunTime(() -> {
                    al10.add(5, 100 * Math.random());
                });

                ll100T = GetFunctionRunTime(() -> {
                    ll100.add(50, 100 * Math.random());
                });
                al100T = GetFunctionRunTime(() -> {
                    al100.add(50, 100 * Math.random());
                });

                alComplexity = "O(n)";
                llComplexity = "O(n)";
                break;
            case "deleteMid":
                ll5T = GetFunctionRunTime(() -> {
                    ll5.remove(2);
                });
                al5T = GetFunctionRunTime(() -> {
                    al5.remove(2);
                });

                ll10T = GetFunctionRunTime(() -> {
                    ll10.remove(5);
                });
                al10T = GetFunctionRunTime(() -> {
                    al10.remove(5);
                });

                ll100T = GetFunctionRunTime(() -> {
                    ll100.remove(50);
                });
                al100T = GetFunctionRunTime(() -> {
                    al100.remove(50);
                });
                alComplexity = "O(n)";
                llComplexity = "O(n)";
                break;
            case "insertTail":
                ll5T = GetFunctionRunTime(() -> {
                    ll5.addLast(100 * Math.random());
                });
                al5T = GetFunctionRunTime(() -> {
                    al5.add(100 * Math.random());
                });

                ll10T = GetFunctionRunTime(() -> {
                    ll10.addLast(100 * Math.random());
                });
                al10T = GetFunctionRunTime(() -> {
                    al10.add(100 * Math.random());
                });

                ll100T = GetFunctionRunTime(() -> {
                    ll100.addLast(100 * Math.random());
                });
                al100T = GetFunctionRunTime(() -> {
                    al100.add(100 * Math.random());
                });
                alComplexity = "O(1)";
                llComplexity = "O(1)";
                break;
            case "deleteTail":
                ll5T = GetFunctionRunTime(ll5::removeLast);
                al5T = GetFunctionRunTime(() -> {
                    al5.remove(4);
                });

                ll10T = GetFunctionRunTime(ll10::removeLast);
                al10T = GetFunctionRunTime(() -> {
                    al10.remove(9);
                });

                ll100T = GetFunctionRunTime(ll100::removeLast);
                al100T = GetFunctionRunTime(() -> {
                    al100.remove(99);
                });
                alComplexity = "O(1)";
                llComplexity = "O(1)";
                break;
            case "sort":
                ll5T = GetFunctionRunTime(() -> {
                    Collections.sort(ll5);
                });
                al5T = GetFunctionRunTime(() -> {
                    Collections.sort(al5);
                });

                ll10T = GetFunctionRunTime(() -> {
                    Collections.sort(ll10);
                });
                al10T = GetFunctionRunTime(() -> {
                    Collections.sort(al10);
                });

                ll100T = GetFunctionRunTime(() -> {
                    Collections.sort(ll100);
                });
                al100T = GetFunctionRunTime(() -> {
                    Collections.sort(al100);
                });
                alComplexity = "O(nlogn)";
                llComplexity = "O(nlogn)";
                break;
        }

        model.addAttribute("al5T", al5T);
        model.addAttribute("al10T", al10T);
        model.addAttribute("al100T", al100T);
        model.addAttribute("ll5T", ll5T);
        model.addAttribute("ll10T", ll10T);
        model.addAttribute("ll100T", ll100T);
        model.addAttribute("alComplexity", alComplexity);
        model.addAttribute("llComplexity", llComplexity);

        System.out.println("end of function");
        return "LinkedList/LLSean";
    }

    @GetMapping("/NewtonSqrt")
    public String newtonsqrt(@RequestParam(name = "newtonsqrt", required = false, defaultValue = "1") String newtonNumber, Model model) {
        rain.finalproject.minilabs.sean.NewtonSqrtModel.NewtonSqrt newtonsqrt = new rain.finalproject.minilabs.sean.NewtonSqrtModel.NewtonSqrt(Double.valueOf(newtonNumber));

        model.addAttribute("input", rain.finalproject.minilabs.sean.NewtonSqrtModel.NewtonSqrt.getInput());
        model.addAttribute("output", rain.finalproject.minilabs.sean.NewtonSqrtModel.NewtonSqrt.getOutput());
        model.addAttribute("realSqrt", rain.finalproject.minilabs.sean.NewtonSqrtModel.NewtonSqrt.getRealSqrt());

        return "Recursion/NewtonSqrt";
    }

    @GetMapping("/DigitSum")
    public String digitsum(@RequestParam(name = "digitsum", required = false, defaultValue = "0") String digitSumNumber, Model model) {
        rain.finalproject.minilabs.sean.DigitSumModel.DigitSum digitsum = new rain.finalproject.minilabs.sean.DigitSumModel.DigitSum(digitSumNumber);

        model.addAttribute("output", rain.finalproject.minilabs.sean.DigitSumModel.DigitSum.getOutput());

        return "Recursion/DigitSum";
    }

    @GetMapping("/Gcd")
    public String gcd(@RequestParam(name = "gcdA", required = false, defaultValue = "1") String inputA,
                      @RequestParam(name = "gcdB", required = false, defaultValue = "1") String inputB,
                      Model model) {
        rain.finalproject.minilabs.sean.GcdModel.Gcd gcd = new rain.finalproject.minilabs.sean.GcdModel.Gcd(Integer.parseInt(inputA), Integer.parseInt(inputB));

        model.addAttribute("output", rain.finalproject.minilabs.sean.GcdModel.Gcd.getOutput());

        return "Recursion/Gcd";
    }

    @GetMapping("/InheritanceSean")
    public String sort(@RequestParam(name = "sortMethod", required = false, defaultValue = "age") String sortMethod, Model model) {

        //zoo initialization
        ArrayList<Animal> zoo = new ArrayList<>();
        zoo.add(new Reptile("Clyde", "TRex", 47, "carnivore"));
        zoo.add(new Reptile("Roberto", "Lizard", 1000, "Omnivore"));
        zoo.add(new Fish("Vasquez", "Goldfish", 2, "Omnivore"));
        zoo.add(new Mammal("Ben", "Golden Retriever", 6, "Omnivore"));
        zoo.add(new Mammal("Sean", "Human", 17, "Solely Pizza"));
        zoo.add(new Bird("Red", "Angry Bird", 5, "Pigs"));
        zoo.add(new Bird("Nelson", "Nighthawk", 11, "Students"));
        zoo.add(new Amphibian("Franklin", "Turtle", 70, "Herbivore"));


        ArrayList<Animal> animals = new AnimalSorter(zoo, sortMethod).getOutput();

        model.addAttribute("animals", animals);
        model.addAttribute("sortMethod", sortMethod);

        return "Inheritance/InheritanceSean";
    }
}