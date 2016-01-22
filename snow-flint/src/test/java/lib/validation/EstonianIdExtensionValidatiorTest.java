package lib.validation;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import lib.validation.EstonianIdExtensionValidatior;

public class EstonianIdExtensionValidatiorTest {

	private static final List<String> CORRECT_ID_EXTENSIONS = Arrays.asList("37605030299", "49403136515", "39109260242", "34501234215", "49403136526");
	private static final List<String> INCORRECT_ID_EXTENSIONS = Arrays.asList("37613030293", "49403336516", "99109260248");
	//Fault in id extension in order : incorrect month; incorrect day; incorrect first number
	private static final List<String> FUTURE_ID_EXTENSIONS = Arrays.asList("87605030293", "89403136519", "79109260246");

	
	private static final int INCORRECT_YEAR_FOR_3_AND_4 = 1800;
	private static final int CORRECT_YEAR_FOR_3_AND_4 = 2000;

	public static class IsValidWithoutDateTest {
		
		@Test
		public void correctIdExtensions() {
			for(String idExtension : CORRECT_ID_EXTENSIONS) {
				Assert.assertTrue(EstonianIdExtensionValidatior.isValid(idExtension));
			}
		}
		
		@Test
		public void incorrectIdExtensions() {
			for(String idExtension : INCORRECT_ID_EXTENSIONS) {
				Assert.assertFalse(EstonianIdExtensionValidatior.isValid(idExtension));
			}
		}
		
		@Test
		public void futureIdExtensions() {
			for(String idExtension : FUTURE_ID_EXTENSIONS) {
				Assert.assertFalse(EstonianIdExtensionValidatior.isValid(idExtension));
			}
		}
	}
	
	
	public static class IsValidWithDateTest {
		
		@Test
		public void correctDateAndCorrectIdExtensions() {
			for(String idExtension : CORRECT_ID_EXTENSIONS) {
				Assert.assertTrue(EstonianIdExtensionValidatior.isValid(idExtension, CORRECT_YEAR_FOR_3_AND_4));
			}
		}
		
		@Test
		public void incorrectDateAndCorrectIdExtensions() {
			for(String idExtension : CORRECT_ID_EXTENSIONS) {
				Assert.assertFalse(EstonianIdExtensionValidatior.isValid(idExtension, INCORRECT_YEAR_FOR_3_AND_4));
			}
		}

		@Test
		public void correctDateAndIncorrectIdExtensions() {
			for(String idExtension : INCORRECT_ID_EXTENSIONS) {
				Assert.assertFalse(EstonianIdExtensionValidatior.isValid(idExtension, CORRECT_YEAR_FOR_3_AND_4));
			}
		}
		
		@Test
		public void incorrectDateAndIncorrectIdExtensions() {
			for(String idExtension : INCORRECT_ID_EXTENSIONS) {
				Assert.assertFalse(EstonianIdExtensionValidatior.isValid(idExtension, INCORRECT_YEAR_FOR_3_AND_4));
			}
		}
	}

}
