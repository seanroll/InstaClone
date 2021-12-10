
package rain.finalproject.Models;

public class BinaryConversion {

    private int output = 0;
    private int binaryCode;
    private int length;
    private int count;
    int sum;

    public BinaryConversion(int Input){
        String BinaryString = String.valueOf(Input);
        length = BinaryString.length();
        count = 0;
        binaryCode = Input;
    }
    public void ConvertToAnalog(){
        if (binaryCode == 0){
            output = 0;
        }
        else{
            output = BinaryConversion(binaryCode);
        }
    }
    public void PrintOutput(){
        System.out.println(output);
    }
    public int BinaryConversion(int Binary){
        int result = sum;

        if(Binary%(10) == 1){
            result = (int)Math.pow(2,count);
            count += 1;
        }
        else if(Binary%(10) != 1){
            count += 1;
        }
        if(count <= length) {
            return result + BinaryConversion((Binary / 10));
        }
        else{
            return 0;
        }

    }


}
