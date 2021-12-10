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
public class JasonMinilabController {

    @GetMapping("/SortJason")
    public String sort(@RequestParam(name = "type", required = false, defaultValue = "int") String type,
                       @RequestParam(name = "size", required = false, defaultValue = "10") String size,
                       Model model) {
        //Thanks Sean, I had no clue how to randomly generate strings
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int listSize = Integer.parseInt(size);
        ArrayList<Integer> all = new ArrayList<>();
        for (int i=0; i<listSize;i++){
            all.add(i);
        }
        model.addAttribute("all",all);
        double insertionSortTime = 0;
        double bubbleSortTime = 0;
        double selectionSortTime = 0;

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

            bubbleSortTime = GetFunctionRunTime(() -> intBubbleSort(bubbleList));
            insertionSortTime = GetFunctionRunTime(() -> intInsertionSort(insertionList));
            selectionSortTime = GetFunctionRunTime(() -> intSelectionSort(selectionList));
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
            bubbleSortTime = GetFunctionRunTime(() -> stringBubbleSort(bubbleList));
            insertionSortTime = GetFunctionRunTime(() -> stringInsertionSort(insertionList));
            selectionSortTime = GetFunctionRunTime(() -> stringSelectionSort(selectionList));
            model.addAttribute("sortedList",bubbleList.toArray());
        }

        model.addAttribute("bubbleTime", bubbleSortTime);
        model.addAttribute("insertionTime", insertionSortTime);
        model.addAttribute("selectionTime", selectionSortTime);

        return "Sort/SortJason";
    }

    private void intBubbleSort(ArrayList<Integer> sort) {
        int n = sort.size();
        for (int i = 0; i < n-1; i++)
        {
            for (int j = 0; j < n-i-1; j++)
            {
                if (sort.get(j) > sort.get(j+1))
                {
                    int temp = sort.get(j);
                    sort.set(j,sort.get(j+1));
                    sort.set(j+1,temp);
                }
            }
        }
    }

    private void stringBubbleSort(ArrayList<String> sort) {
        int n = sort.size();
        for (int i = 0; i < n-1; i++)
        {
            for (int j = 0; j < n-i-1; j++)
            {
                if (sort.get(j).compareTo(sort.get(j+1))<0)
                {
                    String temp = sort.get(j);
                    sort.set(j,sort.get(j+1));
                    sort.set(j+1,temp);
                }
            }
        }
    }

    private void intSelectionSort(ArrayList<Integer> sort) {
        int n = sort.size();

        for (int i = 0; i < n-1; i++)
        {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (sort.get(j) < sort.get(min))
                    min = j;
            int temp = sort.get(min);
            sort.set(min,sort.get(i));
            sort.set(i,temp);
        }
    }

    private void stringSelectionSort(ArrayList<String> sort) {
        int n = sort.size();

        for (int i = 0; i < n-1; i++)
        {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (sort.get(j).compareTo(sort.get(min)) >0)
                    min = j;
            String temp = sort.get(min);
            sort.set(min,sort.get(i));
            sort.set(i,temp);
        }
    }

    public static void intInsertionSort(ArrayList<Integer> sort) {
        int n = sort.size();
        for (int i = 1; i < n; ++i) {
            int save = sort.get(i);
            int j = i - 1;
            while (j >= 0 && sort.get(j) > save) {
                sort.set(j + 1,sort.get(j));
                j = j - 1;
            }
            sort.set(j + 1,save);
        }
    }

    public static void stringInsertionSort(ArrayList<String> sort) {
        int n = sort.size();
        for (int i = 1; i < n; ++i) {
            String save = sort.get(i);
            int j = i - 1;
            while (j >= 0 && sort.get(j).compareTo(sort.get(i))>0) {
                sort.set(j + 1,sort.get(j));
                j = j - 1;
            }
            sort.set(j + 1,save);
        }
    }

    //thanks dominic!
    public double GetFunctionRunTime(Runnable Function) {
        long Start = System.nanoTime();
        Function.run();
        return (System.nanoTime() - Start) / 1000000.0;
    }


    @GetMapping("/LLJason")
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
        return "LinkedList/LLJason";
    }

    @GetMapping("/Binaryconvert")
    public String BinaryConvert(@RequestParam(name = "binconv", required = false, defaultValue = "1") String binary, Model model) {
        int x = 0;
        try{
            x = Integer.parseInt(binary);
        }
        catch(NumberFormatException ex){

        }
        rain.finalproject.minilabs.Jason.BinaryConversion binaryConvert = new rain.finalproject.minilabs.Jason.BinaryConversion(x);

        model.addAttribute("input", rain.finalproject.minilabs.Jason.BinaryConversion.getIn());
        model.addAttribute("output", rain.finalproject.minilabs.Jason.BinaryConversion.getOut());

        return "Recursion/Binaryconvert";
    }



    /*@GetMapping("/InheritanceSean")
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
     */
}