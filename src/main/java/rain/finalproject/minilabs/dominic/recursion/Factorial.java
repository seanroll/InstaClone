package rain.finalproject.minilabs.dominic.recursion;

import java.util.stream.LongStream;

public class Factorial {
	// Number >= 0
	public static long Recursion(long Value) {
		return Value == 0 ? 1 : Value * Recursion(Value - 1);
	}

	public static long ForLoop(long Value) {
		if (Value == 0) {
			return 1;
		} else {
			long Result = 1;
			for (long Counter = 2; Counter <= Value; ++Counter) {
				Result *= Counter;
			}
			return Result;
		}
	}

	public static long Stream(long Value) {
		if (Value == 0) {
			return 1;
		} else {
			return LongStream.rangeClosed(1, Value).reduce(1, (long a, long b) -> a * b);
		}
	}

	public static void main(String[] args) {
		System.out.println(Recursion(15));
		System.out.println(ForLoop(15));
		System.out.println(Stream(15));
	}
}
