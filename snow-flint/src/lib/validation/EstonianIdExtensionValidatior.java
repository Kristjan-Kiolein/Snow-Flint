package lib.validation;

import java.util.Calendar;

public class EstonianIdExtensionValidatior {

	/**https://et.wikipedia.org/wiki/Isikukood*/
	private static final int ID_EXTENSION_LENGTH = 11;
	private static final int LAST_YEAR_BEAFORE_CHANGE_OF_FIRST_DIGIT = 2099;
	private static final String I_RANK_WEIGHT = "1234567891";
	private static final String II_RANK_WEIGHT = "3456789123";
	
	private static final String CENTURY_AND_SEX = Calendar.getInstance().get(Calendar.YEAR) > LAST_YEAR_BEAFORE_CHANGE_OF_FIRST_DIGIT ? "(^[1-8])" : "(^[1-6])";;
	private static final String BIRTY_YEAR = "([0-9]{2})";
	private static final String BIRTH_MONTH = "(0[1-9]|1[012])";
	private static final String BIRTH_DAY = "(0[1-9]|[12]\\d|3[01]) ";
	private static final String BIRTH_SEQUENCE_NR = "(\\d{3})";
	private static final String CONTROL_NUMBER = "(\\d$)";
	private static final String ID_REGEX = CENTURY_AND_SEX + BIRTY_YEAR + BIRTH_MONTH + BIRTH_DAY + BIRTH_SEQUENCE_NR + CONTROL_NUMBER;
	
	
	public static boolean isValidEstonianIdExtension(String code) {
		code = code.trim();
		
		if(code == null) return false;
		if(code.length() != ID_EXTENSION_LENGTH) return false;
		if(!code.matches(ID_REGEX)) return false;
		
		int checkSum = calculateCheckSum(code, I_RANK_WEIGHT);
		
		if(checkSum == 10) checkSum = calculateCheckSum(code, II_RANK_WEIGHT);
		
		if(Character.getNumericValue(code.charAt(ID_EXTENSION_LENGTH - 1)) == checkSum) return true;
		
		return false;
	}

	private static int calculateCheckSum(String code, String weight) {
		int checkSum = 0;
		for(int i = 0; i < weight.length(); i++) {
			checkSum += Character.getNumericValue(code.charAt(i)) * Character.getNumericValue(weight.charAt(i));
		}
		return checkSum % 11;
	}
}