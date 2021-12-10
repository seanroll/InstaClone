package rain.finalproject.minilabs.dominic.recursion;

import org.springframework.security.core.parameters.P;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Fibonacci {
	// Number >= 0
	public static long Recursion(long Number) {
		return Number <= 1 ? Number : (Recursion(Number - 1) + Recursion(Number - 2));
	}

	public static long ForLoop(long Number) {
		if (Number <= 1) {
			return Number;
		} else {
			int A = 0, B = 1;
			for (int Index = 2; Index <= Number; ++Index) {
				int Temp = B;
				B += A;
				A = Temp;
			}
			return B;
		}
	}

	public static long Stream(long Number) {
		return Number <= 1 ? Number : Stream.iterate(new long[]{0, 1}, n -> new long[]{n[1], n[0] + n[1]})
										.limit(Number)
										.reduce((a, b) -> b)
										.get()[1];
	}

	public static void main(String[] args) {
		System.out.println(Recursion(46));
		System.out.println(ForLoop(46));
		System.out.println(Stream(46));
	}
}
