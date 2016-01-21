package test.lib.reflection;

import java.lang.reflect.InvocationTargetException;
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
		
		
		private static final List<String> INPUTS  = Arrays.asList("4000000000","32,767", "400000000000", "123.123", "123.123", "127 ");
		private static final List<Object> RESULTS = Arrays.asList(400000000,32.767, 400000000000L, 123.123F, 123.123D, 127);
		
		@Test
		public void castToString() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(String.class, "Tere"), "Tere");
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(String.class, null), null);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(String.class, ""), null);
		}
		
		@Test
		public void castToBoolean() throws UnknownObjectException, ParseException {
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Boolean.class, "true"), true);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(Boolean.class, null), false);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(boolean.class, "true"), true);
			Assert.assertEquals(ClassCastHelper.castToSimpleClass(boolean.class, null), false);
		}
		
//		@Test
//		public void castToNumber() throws UnknownObjectException, ParseException {
//			for(int i = 0; i < PRIMITIVE_NUMBER_CLASSES.size(); i++) {
//				Assert.assertEquals(ClassCastHelper.castToSimpleClass(PRIMITIVE_NUMBER_CLASSES.get(i), INPUTS.get(i)),PRIMITIVE_NUMBER_CLASSES.get(i).cast(RESULTS.get(i)));
//				Assert.assertEquals(ClassCastHelper.castToSimpleClass(WRAPPER_NUMBER_CLASSES.get(i), INPUTS.get(i)), RESULTS.get(i).getClass().cast(PRIMITIVE_NUMBER_CLASSES.get(i)));
//			}
//		}
	}
	
}
