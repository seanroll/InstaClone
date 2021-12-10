package rain.finalproject.minilabs.Jason;

public class BinaryConversion {

    private static int output = 0;
    private static int input;
    private int binaryCode;
    private int length;
    private int count;
    int sum;

    public BinaryConversion(int Input){
        this.input = Input;
        String BinaryString = String.valueOf(Input);
        length = BinaryString.length();
        count = 0;
        binaryCode = Input;
        this.output = BinaryConverter(binaryCode);
    }
    public static int getOut(){
        return output;
    }
    public static int getIn(){ return input;}
    public int BinaryConverter(int Binary){
        int result = 0;
        if(Binary%(10) == 1){
            result += Math.pow(2,count);
            System.out.println("Added" + result);
            count += 1;

        }
        else if(Binary%(10) != 1){
            System.out.println("Added 0");
            count += 1;
        }
        if(Binary/10 != 0){
            System.out.println("Result" + result);
            return result + BinaryConverter((Binary/10));
        }
        else
            return (int)Math.pow(2,(count-1));

    }


}

