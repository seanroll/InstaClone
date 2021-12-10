package rain.finalproject.minilabs.sean.NewtonSqrtModel;

public class NewtonSqrt {

    private static double input;
    private static double output;

    public NewtonSqrt(double input){
        this.input = input;
        this.output = findNewtonSqrt(input);
    }

    public double findNewtonSqrt(double n){
        return sqrtIter(1,n);
    }

    private double sqrtIter(double guess, double n){
        if (Math.abs(guess * guess - n) <= 0.001){
            return guess;
        }
        else return sqrtIter((guess + n / guess) / 2.0, n);
    }

    public static double getOutput() {
        return output;
    }

    public static double getInput(){
        return input;
    }

    public static double getRealSqrt(){
        return Math.sqrt(input);
    }
}
