package projecteuler.p020_p029.p024_lexiographicPermutations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lib.math.KMath;

/**
 * <h1>Lexicographic permutations</h1>
 * <br>
 * A permutation is an ordered arrangement of objects.<br>
 * For example, 3124 is one possible permutation of the digits 1, 2, 3 and 4.
 * If all of the permutations are listed numerically or alphabetically, we call it lexicographic order.
 * The lexicographic permutations of 0, 1 and 2 are:<br>
 * <br>
 * 012   021   102   120   201   210<br>
 * <br>
 * <i>What is the millionth lexicographic permutation of the digits 0, 1, 2, 3, 4, 5, 6, 7, 8 and 9?</i><br>
 */
@SuppressWarnings("unused")
public class LexiographicPermutations {

	private static final Integer[] SMALLEST_PERMUTATION = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	
	private static final Comparator<Integer[]> LEXIOCOGRAPHIC_ORDER_FOR_ARRAY = new Comparator<Integer[]>() {
		@Override
		public int compare(Integer[] o1, Integer[] o2) {
			int result = 0;
			int i = 0;
			while(result == 0 && i < o1.length) {
				result = o1[i].compareTo(o2[i]);
				i++;
			}
			return result;
		}
	};
	
	public static void main(String[] args) {
		String millionth = solve(SMALLEST_PERMUTATION);
		System.out.println(millionth);
	}

	private static String solve(Integer[] numbers) {
		
		List<Integer[]> s = KMath.permutations(numbers);
		System.out.println("Done");
		s.sort(LEXIOCOGRAPHIC_ORDER_FOR_ARRAY);
		
		return Arrays.toString(s.get(999_999));
	}
}


/*

I used a manual process similar to Euler's:

For the lowest possible number we first check if 0 can be the first digit. There are 9! combinations with 0 first. 9!<1000000, so 0 cannot be first. All combinations using 0 as first digit will not get close to the millionth combination.

Can 1 be first? 2*9! covers all combinations with 0 first AND 1 first. Unfortunately 2*9! <1000000, so 0 or 1 cannot be first.

However 3*9! > 1000000, so 2 can be first digit. The two best first digits (0 and 1) are not possible, but the third best digit (2) is. The millionth combination will be one of the combinations having 2 as the first digit.

By not having 0 or 1 first we have exhausted 2*9! combinations

So we move on the the next digit. Can it be 0? No, since 2*9! + 1*8! < 1000000. 1? 3? 4? 5? 6? 7 - yes, since 2*9! + 7*8! > 1000000. The first six best digits (0 1 3 4 5 6) are not possible, but the seventh best (7) is.
(Why did I skip 2? - Because it is already used as the first digit.)

We now have "27" as the initial ones.

And so on, finding that, in order from the beginning, we can use the third, seventh, seventh, third, sixth, second, third, second, and second best (lowest available) digits.

*/