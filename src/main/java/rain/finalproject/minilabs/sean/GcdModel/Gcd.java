package rain.finalproject.minilabs.sean.GcdModel;

public class Gcd {

    private int inputA;
    private int inputB;
    private static int output;

    public Gcd(int a, int b){
        inputA = a;
        inputB = b;
        output = findGcd(inputA, inputB);
    }

    private int findGcd(int a,int b){
        if (b==0){
            return a;
        }
        else
            return findGcd(b, a % b);
    }

    public static int getOutput(){
        return output;
    }
}
