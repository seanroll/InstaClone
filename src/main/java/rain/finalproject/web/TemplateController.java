package rain.finalproject.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rain.finalproject.minilabs.Andrew.sort.SortingMethods;
import rain.finalproject.minilabs.AndrewInheritance.CodeLanguages;
import rain.finalproject.minilabs.sean.Inheritance.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Controller
public class TemplateController {

    @RequestMapping("/how")
    public String howItsMade(){return "howItsMade";}

    @RequestMapping("/doodlejump")
    public String doodleJump(){
        return "Game/doodlejump";
    }

    @GetMapping("/Factorialh")
    public String factorial(@RequestParam(name = "factorial", required = false, defaultValue = "0") String factorialNumber, Model model) {


        rain.finalproject.Models.Factorial factorial = new rain.finalproject.Models.Factorial(Long.valueOf(factorialNumber));
        model.addAttribute("output", rain.finalproject.Models.Factorial.getOutput());

        return "Recursion/Factorialh";
    }

    @GetMapping("/Palind")
    public String palindrome(@RequestParam(name = "inputS", required = false, defaultValue = "") String inputString, Model model) {


        rain.finalproject.Models.Palindrome Pal = new rain.finalproject.Models.Palindrome(inputString);

        model.addAttribute("PRout", Pal.palRec);
        model.addAttribute("PRtime", Pal.timeRec);

        model.addAttribute("PLout", Pal.palLoop);
        model.addAttribute("PLtime", Pal.timeLoop);

        return "Recursion/Palind";
    }



    @GetMapping("/SortAndrew")
    public String sortAndGet(Model model) {
        Object[] intArray = {5, 2, 1, 6, 8};
        model.addAttribute("intA", intArray);

        Object[] strArray = {"hi", "hello", "test", "string", "html"};
        model.addAttribute("strA", strArray);

        model.addAttribute("objA", CodeLanguages.CLData());

        return ("Sort/SortAndrew");
    }

    @GetMapping("/SortAndrew2")
    public String sortAndPost(Model model) {
        Object[] intArray = {5, 2, 1, 6, 8};
        Object[] strArray = {"hi", "hello", "test", "string", "html"};

        SortingMethods sort = new SortingMethods(intArray);
        model.addAttribute("intA", intArray);

        model.addAttribute("IntinsertTime", sort.insertTime);
        model.addAttribute("IntbubbleTime", sort.BubbleTime);
        model.addAttribute("IntSelectTime", sort.SelectionTime);

        sort = new SortingMethods(strArray);
        model.addAttribute("strA", strArray);

        model.addAttribute("StrinsertTime", sort.insertTime);
        model.addAttribute("StrbubbleTime", sort.BubbleTime);
        model.addAttribute("StrelectTime", sort.SelectionTime);

        sort = new SortingMethods(CodeLanguages.CLData());
        model.addAttribute("objA", CodeLanguages.CLData());

        model.addAttribute("ObjinsertTime", sort.insertTime);
        model.addAttribute("ObjbubbleTime", sort.BubbleTime);
        model.addAttribute("ObjSelectTime", sort.SelectionTime);


        return ("Sort/SortAndrew");
    }

    //thanks dominic!
    public double GetFunctionRunTime(Runnable Function) {
        long Start = System.nanoTime();
        Function.run();
        return (System.nanoTime() - Start) / 1000000.0;
    }




    public void insertSort(List L) {
        for (int i = 0; i < L.size(); i++) {
            Object key = L.get(i);
            int j = i-1;
            while(j >= 0 && ((L.get(j).toString().compareTo(key.toString()) > 0))) {
                L.set(j+1, L.get(j));
                j--;
            }
            L.set(j+1, key);
        }
    }


    LinkedList<CodeLanguages> LL = new LinkedList<>();
    ArrayList<CodeLanguages> AL = new ArrayList<>();

    @GetMapping("/LLAndrew")
    public String LinkedAndrew(@RequestParam(name = "name", required = false, defaultValue = "default") String name,
                               @RequestParam(name = "age", required = false, defaultValue = "404") int age,
                               @RequestParam(name = "option", required = false, defaultValue = "head") String opt,
                               @RequestParam(name = "AoD", required = false, defaultValue = "Add") String aod, Model model) {
        Double TimeL = 0.0;
        Double TimeA = 0.0;

        

        if (aod.equals("Add")) {

            switch (opt) {
                case "tail":
                    long Start = System.nanoTime();
                    LL.add(0, new CodeLanguages(name, age));
                    long End = System.nanoTime();
                    TimeL = ((End - Start) / 1000000.0);


                    Start = System.nanoTime();
                    AL.add(0, new CodeLanguages(name, age));
                    End = System.nanoTime();
                    TimeA = ((End - Start) / 1000000.0);

                    break;
                case "mid":
                    Start = System.nanoTime();
                    LL.add(LL.size() / 2, new CodeLanguages(name, age));
                    End = System.nanoTime();
                    TimeL = ((End - Start) / 1000000.0);

                    Start = System.nanoTime();
                    AL.add(AL.size() / 2, new CodeLanguages(name, age));
                    End = System.nanoTime();
                    TimeA = ((End - Start) / 1000000.0);
                    break;
                case "head":
                    Start = System.nanoTime();
                    LL.add(new CodeLanguages(name, age));
                    End = System.nanoTime();
                    TimeL = ((End - Start) / 1000000.0);
                    //System.out.println(LL.size());

                    Start = System.nanoTime();
                    AL.add(new CodeLanguages(name, age));
                    End = System.nanoTime();
                    TimeA = ((End - Start) / 1000000.0);

                    break;

            }


        } else if(aod.equals("Del")) {

            switch (opt) {
                case "tail":
                    long Start = System.nanoTime();
                    LL.remove(0);
                    long End = System.nanoTime();
                    TimeL = ((End - Start) / 1000000.0);


                    Start = System.nanoTime();
                    AL.remove(0);
                    End = System.nanoTime();
                    TimeA = ((End - Start) / 1000000.0);
                    break;
                case "mid":
                    Start = System.nanoTime();
                    LL.remove(LL.size() / 2);
                    End = System.nanoTime();
                    TimeL = ((End - Start) / 1000000.0);

                    Start = System.nanoTime();
                    AL.remove(AL.size() / 2);
                    End = System.nanoTime();
                    TimeA = ((End - Start) / 1000000.0);
                    break;
                case "head":
                    Start = System.nanoTime();
                    LL.remove(LL.size());
                    End = System.nanoTime();
                    TimeL = ((End - Start) / 1000000.0);

                    Start = System.nanoTime();
                    AL.remove(AL.size());
                    End = System.nanoTime();
                    TimeA = ((End - Start) / 1000000.0);
                    break;
            }


        }

        else {
            long Start = System.nanoTime();
            insertSort(LL);
            long End = System.nanoTime();
            TimeL = ((End - Start) / 1000000.0);

            Start = System.nanoTime();
            insertSort(AL);
            End = System.nanoTime();
            TimeA = ((End - Start) / 1000000.0);
        }

        model.addAttribute("LL", LL);
        model.addAttribute("timeL", TimeL);

        model.addAttribute("AL", AL);
        model.addAttribute("timeA", TimeA);

        System.out.println(LL);
        return "LinkedList/LLAndrew";

    }
}

