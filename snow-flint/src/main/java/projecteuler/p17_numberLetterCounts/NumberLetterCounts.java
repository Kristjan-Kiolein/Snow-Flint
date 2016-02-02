package projecteuler.p17_numberLetterCounts;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>Number letter counts</h1><br>
 * 
 * If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.<br>
 * <br>
 * <i>If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used?</i><br>
 * <br>
 * <b>NOTE:</b> Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23 letters and 115 (one hundred and fifteen) contains 20 letters.
 * The use of "and" when writing out numbers is in compliance with British usage.
 */
public class NumberLetterCounts {
	
	
	private static final List<String> BASE_NUMBERS = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
	private static final List<String> TEN_TO_NINETEEN = Arrays.asList("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen");
	private static final List<String> TWENTY_TO_NINETY= Arrays.asList("twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety");
	private static final String HUNDRED = "hundred";
	private static final String THOUSAND = "thousand";
	private static final String AND = "and";
	

	public static void main(String[] args) {
//		System.out.println("1 - 99 : " + "onetwothreefourfivesixseveneightnineteneleventwelvethirteenfourteenfifteensixteenseventeeneighteennineteentwentytwentyonetwentytwotwentythreetwentyfourtwentyfivetwentysixtwentyseventwentyeighttwentyninethirtythirtyonethirtytwothirtythreethirtyfourthirtyfivethirtysixthirtyseventhirtyeightthirtyninefortyfortyonefortytwofortythreefortyfourfortyfivefortysixfortysevenfortyeightfortyninefiftyfiftyonefiftytwofiftythreefiftyfourfiftyfivefiftysixfiftysevenfiftyeightfiftyninesixtysixtyonesixtytwosixtythreesixtyfoursixtyfivesixtysixsixtysevensixtyeightsixtynineseventyseventyoneseventytwoseventythreeseventyfourseventyfiveseventysixseventysevenseventyeightseventynineeightyeightyoneeightytwoeightythreeeightyfoureightyfiveeightysixeightyseveneightyeighteightynineninetyninetyoneninetytwoninetythreeninetyfourninetyfiveninetysixninetysevenninetyeightninetynine".length());
//		System.out.println("20 - 99 : " + "twentytwentyonetwentytwotwentythreetwentyfourtwentyfivetwentysixtwentyseventwentyeighttwentyninethirtythirtyonethirtytwothirtythreethirtyfourthirtyfivethirtysixthirtyseventhirtyeightthirtyninefortyfortyonefortytwofortythreefortyfourfortyfivefortysixfortysevenfortyeightfortyninefiftyfiftyonefiftytwofiftythreefiftyfourfiftyfivefiftysixfiftysevenfiftyeightfiftyninesixtysixtyonesixtytwosixtythreesixtyfoursixtyfivesixtysixsixtysevensixtyeightsixtynineseventyseventyoneseventytwoseventythreeseventyfourseventyfiveseventysixseventysevenseventyeightseventynineeightyeightyoneeightytwoeightythreeeightyfoureightyfiveeightysixeightyseveneightyeighteightynineninetyninetyoneninetytwoninetythreeninetyfourninetyfiveninetysixninetysevenninetyeightninetynine".length());
//		System.out.println("1-19 : " + "onetwothreefourfivesixseveneightnineteneleventwelvethirteenfourteenfifteensixteenseventeeneighteennineteen".length());
//		System.out.println("1-9 : " + "onetwothreefourfivesixseveneightnine".length());
//		System.out.println("10-19 : " + "teneleventwelvethirteenfourteenfifteensixteenseventeeneighteennineteen".length());
//		System.out.println("20-29 : " + "twentytwentyonetwentytwotwentythreetwentyfourtwentyfivetwentysixtwentyseventwentyeighttwentynine".length());
//		System.out.println("30-39 : " + "thirtythirtyonethirtytwothirtythreethirtyfourthirtyfivethirtysixthirtyseventhirtyeightthirtynine".length());
//		System.out.println("40-49 : " + "fortyfortyonefortytwofortythreefortyfourfortyfivefortysixfortysevenfortyeightfortynine".length());
//		System.out.println("50-59 : " + "fiftyfiftyonefiftytwofiftythreefiftyfourfiftyfivefiftysixfiftysevenfiftyeightfiftynine".length());
//		System.out.println("60-69 : " + "sixtysixtyonesixtytwosixtythreesixtyfoursixtyfivesixtysixsixtysevensixtyeightsixtynine".length());
//		System.out.println("70-79 : " + "seventyseventyoneseventytwoseventythreeseventyfourseventyfiveseventysixseventysevenseventyeightseventynine".length());
//		System.out.println("80-89 : " + "eightyeightyoneeightytwoeightythreeeightyfoureightyfiveeightysixeightyseveneightyeighteightynine".length());
//		System.out.println("90-99 : " + "ninetyninetyoneninetytwoninetythreeninetyfourninetyfiveninetysixninetysevenninetyeightninetynine".length());
		
		System.out.println(solve(1000));
	}
	
	private static int solve(int last) {
		
		int oneToNine = 0;
		int tenToNineTeen = 0;
		int twentyToNinetyNine = 0;
		int oneToNinetyNine = 0;
		int hundredToNineHundredAndNinetyNine = 0;
		int oneToThousand = 0;
		
		for (String string : BASE_NUMBERS) {
			oneToNine += string.length();
		}
		
		for (String string : TEN_TO_NINETEEN) {
			tenToNineTeen += string.length();
		}
		
		for (String string : TWENTY_TO_NINETY) {
			twentyToNinetyNine += string.length()*10 + oneToNine;
		}
		
		oneToNinetyNine = oneToNine + tenToNineTeen + twentyToNinetyNine;
		
		for (String string : BASE_NUMBERS) {
			hundredToNineHundredAndNinetyNine += string.length()*100 + HUNDRED.length()*100 + AND.length()*99 + oneToNinetyNine; 
		}
		
		oneToThousand = oneToNinetyNine + hundredToNineHundredAndNinetyNine + BASE_NUMBERS.get(0).length() + THOUSAND.length() ;
		
		return oneToThousand;
	}

}
