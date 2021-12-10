package rain.finalproject.minilabs.dominic.recursion;

import java.util.stream.Stream;

public class GCD {
	// A > 0 && B > 0
	public static long Recursion(long A, long B) {
		assert (A > 0 && B > 0) : "A and B must be positive integers.";
		return B == 0 ? A : Recursion(B, A % B);
	}

	public static long ForLoop(long A, long B) {
		assert (A > 0 && B > 0) : "A and B must be positive integers.";
		long GCD = 1;

		for (long Value = 1; Value <= A && Value <= B; ++Value) {
			if (A % Value == 0 && B % Value == 0) {
				GCD = Value;
			}
		}

		return GCD;
	}

	public static long Stream(long A, long B) {
		assert (A > 0 && B > 0) : "A and B must be positive integers.";
		return Stream.iterate(new long[]{A, B}, n -> n[1] != 0, n -> new long[]{n[1], n[0] % n[1]})
				.reduce((a, b) -> b)
				.get()[1];
	}

	public static void main(String[] args) {
		System.out.println(Recursion(1254,654));
		System.out.println(ForLoop(1254,654));
		System.out.println(Stream(1254,654));
	}
}
