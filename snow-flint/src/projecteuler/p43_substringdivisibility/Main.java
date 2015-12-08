package projecteuler.p43_substringdivisibility;


import java.util.Arrays;

import lib.math.KMath;

public class Main {

	    public static void main(String[] args){
	        Integer[] arr = {1, 1 ,3,4,5,6};
	        
	    	for(Object[] o : KMath.combinations(arr, 3)) {
				System.out.println(Arrays.toString(o));
			}
	    }

	    
}
