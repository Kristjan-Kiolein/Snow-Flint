package test.lib.reflection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.activation.UnknownObjectException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import lib.reflection.ClassCastHelper;

//@RunWith(Enclosed.class)
public class ClassCastHelperTest {

	
	private static final List<Class<?>> PRIMITIVE_NUMBER_CLASSES = Arrays.asList(int.class, short.class, long.class, float.class, double.class, byte.class);
	private static final List<Class<?>> WRAPPER_NUMBER_CLASSES = Arrays.asList(Integer.class, Short.class, Long.class, Float.class, Double.class, Byte.class);
	private static final List<Class<?>> NON_SIMPLE_CLASSES = Arrays.asList(List.class, Map.class, Set.class, Thread.class, Iterator.class);
	private static final List<Class<?>> SIMPLE_CLASSES;
	static {
		SIMPLE_CLASSES = new ArrayList<>();
		SIMPLE_CLASSES.addAll(PRIMITIVE_NUMBER_CLASSES);
		SIMPLE_CLASSES.addAll(WRAPPER_NUMBER_CLASSES);
		SIMPLE_CLASSES.addAll(Arrays.asList(char.class, Character.class, boolean.class, Boolean.class));
		SIMPLE_CLASSES.addAll(Arrays.asList(BigInteger.class, BigDecimal.class, Date.class));
		
		
	}
	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER;
	static {
		PRIMITIVE_TO_WRAPPER = new HashMap<>();
		PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
		PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
		PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
		PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
		PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
		PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
	}
	
	
	
	public static class isPrimitiveNumberTypeTest {
		
		@Test
		public void primitiveNumbers() {
			for(Class<?> clazz : PRIMITIVE_NUMBER_CLASSES) {
				Assert.assertTrue(ClassCastHelper.isPrimitiveOrWrapperNumberType(clazz));
			}
		}
		
		@Test
		public void primitiveWrappers() {
			for(Class<?> clazz : WRAPPER_NUMBER_CLASSES) {
				Assert.assertTrue(ClassCastHelper.isPrimitiveOrWrapperNumberType(clazz));
			}
		}

		@Test
		public void nonNumberClasses() {
			for(Class<?> clazz : NON_SIMPLE_CLASSES) {
				Assert.assertFalse(ClassCastHelper.isPrimitiveOrWrapperNumberType(clazz));
			}
		}
	}
	
	public static class getPrimitiveNumberWrapperTest {
		
		@Test
		public void primitiveNumbers() {
			for(Entry<Class<?>, Class<?>> entry : PRIMITIVE_TO_WRAPPER.entrySet()) {
				Assert.assertEquals(ClassCastHelper.getPrimitiveNumberWrapper(entry.getKey()), entry.getValue());
			}
		}
		
		@Test
		public void randomClasses() {
			for(Class<?> clazz : NON_SIMPLE_CLASSES) {
				Assert.assertEquals(ClassCastHelper.getPrimitiveNumberWrapper(clazz), null);
			}
		}
		
		@Test
		public void wrapperClasses() {
			for(Class<?> clazz : WRAPPER_NUMBER_CLASSES) {
				Assert.assertEquals(ClassCastHelper.getPrimitiveNumberWrapper(clazz), clazz);
			}
		}
		
	}
	
	public static class isSimpleClassTest {
		
		private static enum TestEnum {
			TEST1, TEST2
		}
		
		@Test
		public void wrapperClasses() {
			for(Class<?> clazz : SIMPLE_CLASSES) {
				Assert.assertTrue(ClassCastHelper.isSimpleClass(clazz));
			}
		}
		
		@Test
		public void enumClass() {
			Assert.assertTrue(ClassCastHelper.isSimpleClass(TestEnum.class));
		}
		
		@Test
		public void randomClasses() {
			for(Class<?> clazz : NON_SIMPLE_CLASSES) {
				Assert.assertFalse(ClassCastHelper.isSimpleClass(clazz));
			}
		}
		
	}
	
	public static class isBooleanTest {
		@Test
		public void booleanAndBooleanWrapper() {
			Assert.assertTrue(ClassCastHelper.isSimpleClass(boolean.class));
			Assert.assertTrue(ClassCastHelper.isSimpleClass(Boolean.class));
		}
		
		@Test
		public void randomClasses() {
			for(Class<?> clazz : NON_SIMPLE_CLASSES) {
				Assert.assertFalse(ClassCastHelper.isSimpleClass(clazz));
			}
		}
	}
	
	public static class castToSimpleClassTest {
		
		
		@Test
		public void castToString() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(String.class, "Tere"), "Tere");
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(String.class, null), null);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(String.class, ""), null);
		}
		
		@Test
		public void castToBoolean() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Boolean.class, "true"), true);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Boolean.class, "false"), false);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(boolean.class, "true"), true);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(boolean.class, "false"), false);
		}
		
		@Test
		public void castToInteger() throws UnknownObjectException, ParseException {

		}
		
		@Test
		public void castToIntegerValues() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Byte.class, "123"), Byte.valueOf("123"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(byte.class, "123"), Byte.valueOf("123"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Short.class, "12345"), Short.valueOf("12345"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(short.class, "12345"), (short) 12345);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Integer.class, "1234567"), 1234567);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(int.class, "1234567"), 1234567);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Long.class, "123451234512345"), Long.valueOf("123451234512345"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(long.class, "123451234512345"), Long.valueOf("123451234512345"));
		}
		
		@Test
		public void castToDecimalValues() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Float.class, "123451234"), Float.valueOf("123451234"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(float.class, "123451234"), Float.valueOf("123451234"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Float.class, "123451234.1234"), Float.valueOf("123451234.1234"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(float.class, "123451234,1234"), Float.valueOf("123451234.1234"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Double.class, "12345"), Double.valueOf("12345"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(double.class, "12345"), Double.valueOf("12345"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Double.class, "12345.123"), Double.valueOf("12345.123"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(double.class, "12345,123"), Double.valueOf("12345.123"));
		}
		
		/*
		@Rule
		public ExpectedException thrown = ExpectedException.none();
		
		@Test
		public void castIncorrectInputToNumber() throws UnknownObjectException, ParseException {
			thrown.expect(NumberFormatException.class);

			ClassCastHelper.castToSimpleClass(Byte.class, "123.123");
			ClassCastHelper.castToSimpleClass(byte.class, "123,123");
			ClassCastHelper.castToSimpleClass(Short.class, "12345.123");
			ClassCastHelper.castToSimpleClass(short.class, "12345,23");
			ClassCastHelper.castToSimpleClass(Integer.class, "1234567.123");
			ClassCastHelper.castToSimpleClass(int.class, "1234567,123");
			ClassCastHelper.castToSimpleClass(Long.class, "123451234512345.123");
			ClassCastHelper.castToSimpleClass(long.class, "123451234512345,123");
			ClassCastHelper.castToSimpleClass(Byte.class, "123.123");
			ClassCastHelper.castToSimpleClass(byte.class, "123,123");
			ClassCastHelper.castToSimpleClass(Short.class, "12345.123");
			ClassCastHelper.castToSimpleClass(short.class, "12345,23");
			ClassCastHelper.castToSimpleClass(Integer.class, "1234567.123");
			ClassCastHelper.castToSimpleClass(int.class, "1234567,123");
			ClassCastHelper.castToSimpleClass(Long.class, "123451234512345.123");
			ClassCastHelper.castToSimpleClass(long.class, "123451234512345,123");
		}
		*/
		
		@Test
		public void castToBigInteger() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(BigInteger.class, "123451234512345123451234512345"), new BigInteger("123451234512345123451234512345"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(BigInteger.class, "12345"), new BigInteger("12345"));
		}
		
		@Test
		public void castToBigDecimal() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(BigDecimal.class, "123451234512345123451234512345.12313123123123"), new BigDecimal("123451234512345123451234512345.12313123123123"));
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(BigDecimal.class, "12345.123"), new BigDecimal("12345.123"));
		}
		
		/*
		@Test
		public void castToDate() throws UnknownObjectException, ParseException {
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2015);
			cal.set(Calendar.MONTH, 10);
			cal.set(Calendar.DAY_OF_MONTH, 10);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, 0);
			Date dateRepresentation = cal.getTime();
			
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Date.class, "2015-10-10"), dateRepresentation);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Date.class, "2015.10.10"), new Date(2015, 10, 10));
		}
		*/
		
	}
		
}
