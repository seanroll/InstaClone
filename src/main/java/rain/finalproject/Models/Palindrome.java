package rain.finalproject.Models;


import lombok.Getter;


public class Palindrome {

    private String input;

    public Boolean palRec;
    public Boolean palLoop;

    public double timeRec;
    public double timeLoop;

    public Palindrome(String input) {

        this.input = input;


        long Start = System.nanoTime();
        this.palRec = palRecs(input);
        long End = System.nanoTime();
        this.timeRec = ((End - Start) / 1000000.0);


        Start = System.nanoTime();
        this.palLoop = palLoop();
        End = System.nanoTime();
        this.timeLoop = ((End - Start) / 1000000.0);


    }

    public boolean palRecs(String input) {

        if (input.length() <= 1) {
            return true;
        }
        if (input.charAt(0) == input.charAt(input.length() - 1)) {

            palRecs(input.substring(1, input.length() - 1));
        }
        return false;
    }

    public boolean palLoop() { for (int n = 0; n < input.length()/2; n++) {
       int j = n+1;
            if (input.charAt(0) == input.charAt(input.length() - j)) {}
            else {return false;}

        }
        return true;
    }

    public Boolean getPalRec() {
        return palRec;
    }

    public double getTimeRec() {
        return timeRec;
    }

    public double getTimeLoop() {
        return timeLoop;
    }

    public Boolean getPalLoop() {
        return palLoop;
    }

}



