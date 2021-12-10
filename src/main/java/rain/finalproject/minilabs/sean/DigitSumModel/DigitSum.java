package rain.finalproject.minilabs.sean.DigitSumModel;

public class DigitSum {

    private String input;
    private static int output;

    public DigitSum(String input){
        output = computeDigitSum(input);
    }

    private int computeDigitSum(String input){
        int l = input.length();
        //System.out.println("the length of " + input + " is " + l);
        if (l==1){
            return Integer.parseInt(input);
        }
        else{
            //System.out.println("value of left most int is " + Integer.parseInt(input.substring(0,1)));
            return Integer.parseInt(input.substring(0,1)) + computeDigitSum(input.substring(1,l));
        }
    }

    public static int getOutput(){
        return output;
    }

}
