package rain.finalproject.minilabs.dominic.sort;

public class SortAlgorithms {
	// https://en.wikipedia.org/wiki/Bubble_sort
	public static void BubbleSort2(Object Array[], boolean CastInt) {
		int AdjustedLength = Array.length;
		do {
			int NewLength = 0;
			for (int Index = 1; Index <= AdjustedLength - 1; ++Index) {
				if (CastInt ? (int)Array[Index - 1] > (int)Array[Index] : Array[Index - 1].toString().compareTo(Array[Index].toString()) > 0) {
					Object Temp = Array[Index - 1];
					Array[Index - 1] = Array[Index];
					Array[Index] = Temp;

					NewLength = Index;
				}
			}
			AdjustedLength = NewLength;
		} while (AdjustedLength > 1);
	}

	public static void BubbleSort(Object Array[], boolean CastInt) {
		int AdjustedLength = Array.length;
		boolean Swapped = true;
		do {
			Swapped = false;
			for (int Index = 1; Index <= AdjustedLength - 1; ++Index) {
				if (CastInt ? (int)Array[Index - 1] > (int)Array[Index] : Array[Index - 1].toString().compareTo(Array[Index].toString()) > 0) {
					Object Temp = Array[Index - 1];
					Array[Index - 1] = Array[Index];
					Array[Index] = Temp;
					Swapped = true;
				}
			}
			--AdjustedLength;
		} while (Swapped);
	}

	// https://en.wikipedia.org/wiki/Selection_sort
	public static void SelectionSort(Object Array[], boolean CastInt) {
		for (int LowerBoundary = 0; LowerBoundary < Array.length - 1; ++LowerBoundary) {
			int MinIndex = LowerBoundary;
			for (int Index = LowerBoundary + 1; Index < Array.length; ++Index) {
				if (CastInt ? (int)Array[Index] < (int)Array[MinIndex] : Array[Index].toString().compareTo(Array[MinIndex].toString()) < 0) {
					MinIndex = Index;
				}
			}
			if (MinIndex != LowerBoundary) {
				Object Temp = Array[LowerBoundary];
				Array[LowerBoundary] = Array[MinIndex];
				Array[MinIndex] = Temp;
			}
		}
	}

	// https://en.wikipedia.org/wiki/Insertion_sort
	public static void InsertionSort(Object Array[], boolean CastInt) {
		for (int Index = 1; Index < Array.length; ++Index) {
			int Index2 = Index;
			while (Index2 > 0 && (CastInt ? (int)Array[Index2 - 1] > (int)Array[Index2] : Array[Index2 - 1].toString().compareTo(Array[Index2].toString()) > 0)) {
				Object Temp = Array[Index2];
				Array[Index2] = Array[Index2 - 1];
				Array[Index2 - 1] = Temp;
				--Index2;
			}
		}
	}

	private static boolean IsSorted(Object Array[], boolean CastInt) {
		for (int Index = 0; Index < Array.length - 1; ++Index) {
			if (CastInt ? (int)Array[Index] > (int)Array[Index + 1] : Array[Index].toString().compareTo(Array[Index + 1].toString()) > 0) {
				return false;
			}
		}
		return true;
	}

	public static double GetFunctionRunTime(Runnable Function) {
		long Start = System.nanoTime();
		Function.run();
		return (System.nanoTime() - Start) / 1000000.0;
	}

	// Testing
	public static void main(String[] args) {
		boolean PassedTests = true;
		for (int Test = 1; Test <= 1; ++Test) {
			/// System.out.print("Test #" + Test + ": ");

			// Create a random array
			Integer Array[] = new Integer[1000];
			for (int Index = 0; Index < 1000; ++Index) {
				Array[Index] = (int)(Math.random() * 100);
			}

			System.out.println("Bubble: " + GetFunctionRunTime(() -> BubbleSort(Array.clone(), true)));
			System.out.println("Bubble2: " + GetFunctionRunTime(() -> BubbleSort2(Array.clone(), true)));
			System.out.println("Selection: " + GetFunctionRunTime(() -> SelectionSort(Array.clone(), true)));
			System.out.println("Insertion: " + GetFunctionRunTime(() -> InsertionSort(Array.clone(), true)));

			//if (!IsSorted(Array, true)) {
			//	PassedTests = false;
			//	break;
			//}
		}

		//System.out.println(PassedTests);
	}
}
