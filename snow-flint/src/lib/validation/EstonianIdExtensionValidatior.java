package lib.validation;

import java.util.Calendar;

public class EstonianIdExtensionValidatior {

	/**https://et.wikipedia.org/wiki/Isikukood*/
	private static final int ID_EXTENSION_LENGTH = 11;
	private static final String I_RANK_WEIGHT = "1234567891";
	private static final String II_RANK_WEIGHT = "3456789123";
	
	private static final String BIRTY_YEAR = "([0-9]{2})";
	private static final String BIRTH_MONTH = "(0[1-9]|1[012])";
	private static final String BIRTH_DAY = "(0[1-9]|[12]\\d|3[01]) ";
	private static final String BIRTH_SEQUENCE_NR = "(\\d{3})";
	private static final String CONTROL_NUMBER = "(\\d$)";
	
	/**
	 * Validates given id extension as estonian id.
	 * Id extension format depends on year. Current year is used.
	 * Current year is based on the current time in the default time zone. (Calendar.getInstance())
	 * @param code string to validate.
	 * @return if given code is estonian id extension.
	 */
	public static boolean isValid(String code) {
		return isValid(code, Calendar.getInstance().get(Calendar.YEAR));
	}
	
	/**
	 * Validates given id extension as estonian id.
	 * Id extension format depends on year.
	 * Minimum year is 1800 and maximum 2199.
	 * @param code string to validate.
	 * @param year the year at which the id extension must be valid.
	 * @return if given code is estonian id extension. 
	 */
	public static boolean isValid(String code,  int year) {
		
		String idRegex = getIdRegex(year);
		if(code == null || idRegex == null) return false;

		code = code.trim();
		if(code.length() != ID_EXTENSION_LENGTH) return false;
		if(!code.matches(idRegex)) return false;
		
		int checkSum = calculateCheckSum(code, I_RANK_WEIGHT);
		
		if(checkSum == 10) checkSum = calculateCheckSum(code, II_RANK_WEIGHT);
		
		if(Character.getNumericValue(code.charAt(ID_EXTENSION_LENGTH - 1)) == checkSum) return true;
		
		return false;
	}

	/**
	 * Calculates checksum of id extension.
	 * @param code id extension for what the checksum will be calculated.
	 * @param weight constant used for checksum calculation.
	 * @return checksum of given code.
	 */
	private static int calculateCheckSum(String code, String weight) {
		int checkSum = 0;
		for(int i = 0; i < weight.length(); i++) {
			checkSum += Character.getNumericValue(code.charAt(i)) * Character.getNumericValue(weight.charAt(i));
		}
		return checkSum % 11;
	}

	/**
	 * Gets correct regex for century + sex part of the id extension (the first digit).
	 * Minimum year is 1800 and maximum 2199.
	 * @param year latest year what the regex will have to match
	 * @return regex that matches id extension first digits from 1800 to given date,
	 */
	private static String getCenturyAndSexRegex(int year) {
		String regex = null;
		
		if( 1800 <= year && year >= 1899 ) {
			regex = "(^[1-2])";
		} else if( 1900 <= year && year >= 1999 ) {
			regex = "(^[1-4])";
		} else if( 2000 <= year && year >= 2099 ) {
			regex = "(^[1-6])";
		} else if( 2100 <= year && year >= 2199 ) {
			regex = "(^[1-8])";
		}
		
		return regex;
	}

	/**
	 * Adds all id extension regex parts together for given year.
	 * Minimum year is 1800 and maximum 2199.
	 * @param year latest year at which the regex must match 
	 * @return full regex for id extension
	 */
	private static String getIdRegex(int year) {
		return getCenturyAndSexRegex(year) == null ? null : getCenturyAndSexRegex(year) + BIRTY_YEAR + BIRTH_MONTH + BIRTH_DAY + BIRTH_SEQUENCE_NR + CONTROL_NUMBER;
	}
}