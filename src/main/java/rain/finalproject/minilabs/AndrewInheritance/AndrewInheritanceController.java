package rain.finalproject.minilabs.AndrewInheritance;


import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Getter
@Controller
public class AndrewInheritanceController {

    private ArrayList<Object> list;
    private int count;

    private boolean codelang;
    private boolean State;
    private CodeLanguages.KeyType CLkey;
    private States.KeyType Stateskey;


    public AndrewInheritanceController() {
        this.list = new ArrayList<>();
        count = 0;
    }

    public void AddToQueue(Object[] objects) {
        for (Object object : objects) {
            list.add(object);
            count++;
        }
    }

    public void insertSort() {
      for (int i = 0; i < count; i++) {
          Object key = list.get(i);
          int j = i-1;
          while(j >= 0 && ((list.get(j).toString().compareTo(key.toString()) > 0))) {
              list.set(j+1, list.get(j));
              j--;
          }
          list.set(j+1, key);
      }
    }

    @GetMapping("/InheritanceAnd")
    public String data(Model model) {


        this.count = 0;
        this.list = new ArrayList<Object>();

        this.CLkey = CodeLanguages.KeyType.title;
        CodeLanguages.key = this.CLkey;
        this.AddToQueue(CodeLanguages.CLData());

        this.Stateskey = States.KeyType.title;
        States.key = this.Stateskey;
        this.AddToQueue(States.StatesData());


        model.addAttribute("ctl", this);
        return "Inheritance/InheritanceAnd";
    }

    @PostMapping("/InheritanceAnd")
    public String dataFilter(
            @RequestParam(value = "Codelanguages", required = false) String CodeLanguages,
            @RequestParam(value = "CLkey") CodeLanguages.KeyType CLkey,
            @RequestParam(value = "States", required = false) String States,
            @RequestParam(value = "Stateskey") States.KeyType Stateskey,
          /*  @RequestParam(value = "alpha", required = false) String alpha,
            @RequestParam(value = "alphaKey", required = false) Alphabet.KeyType alphaKey, */
            Model model)
    {

        count = 0;
        list = new ArrayList<Object>();

        if (CodeLanguages != null) {
            this.AddToQueue(rain.finalproject.minilabs.AndrewInheritance.CodeLanguages.CLData());
            this.codelang = true;
            this.CLkey = CLkey;
            rain.finalproject.minilabs.AndrewInheritance.CodeLanguages.key = this.CLkey;
        } else {
            this.codelang = false;
        }
        if (States != null) {

            this.AddToQueue(rain.finalproject.minilabs.AndrewInheritance.States.StatesData());
            this.State = true;
            this.Stateskey = Stateskey;
            rain.finalproject.minilabs.AndrewInheritance.States.key = this.Stateskey;
        } /*else {
            this.cake = false;
        }
        if (alpha != null) {
            this.addCQueue(Alphabet.alphabetData());
            this.alpha = true;
            this.alphaKey = alphaKey;
            Alphabet.key = this.alphaKey;
        } else {
            this.alpha = false;
        }
        */


        insertSort();
        model.addAttribute("ctl", this);
        return "Inheritance/InheritanceAnd";
    }


}
