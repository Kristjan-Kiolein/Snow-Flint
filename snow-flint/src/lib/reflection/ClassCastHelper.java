package lib.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.activation.UnknownObjectException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lib.datetime.DateTimeHelper;

public final class ClassCastHelper {

	
	
	
	/**
	 * If is simple class, then cast given string to desired simple class. 
	 * @param castToType type of class to cast 
	 * @param value value for the class object in string form
	 * @return class object if can cast, null otherwise
	 * @throws UnknownObjectException if object to cast to is not simple class as defined by {@link ClassCastHelper#isSimpleClass(Class)}
	 * @throws ParseException if parsing date fails
	 */
	public static <T> T castToSimpleClass(Class<T> castToType, String value) throws UnknownObjectException, ParseException {
		try {
			return castToSimpleClassThrowExceptions(castToType, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException  e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	/**
	 * If is simple class, then cast given string to desired simple class. 
	 * @param castToType type of class to cast 
	 * @param value value for the class object in string form
	 * @return class object if can cast, null otherwise
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ParseException
	 * @throws UnknownObjectException 
	 */
	@SuppressWarnings("unchecked")
	private static <T> T castToSimpleClassThrowExceptions(Class<T> castToType, String value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
					NoSuchMethodException, SecurityException, ParseException, UnknownObjectException {
		if (value == null) return null;
		
		T returnValue = null;
	
		//String
		if (String.class.isAssignableFrom(castToType)) {
			returnValue = value.equals("") ? null : (T) value;
		} 
		//Boolean
		else if (castToType.equals(boolean.class) || Boolean.class.isAssignableFrom(castToType) ) {
			Boolean isTrue = value != null && (value.equals("on") || value.equals("true")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
			returnValue = (T) isTrue;
		} 
		//Numbers
		else if(ClassCastHelper.isPrimitiveOrWrapperNumberType(castToType)) {
			Class<?> primitiveType = ClassCastHelper.getPrimitiveNumberWrapper(castToType);
			Method valueOfMethod = primitiveType.getMethod("valueOf", String.class);
			Object primitiveValue = null;
			//Catch the error if the string is with wrong format or too large for primitive type.
			try {
				primitiveValue = valueOfMethod.invoke(primitiveType, value.replaceAll(",", "."));
			} catch (InvocationTargetException e) {
				if(ClassCastHelper.isNumberFormatException(e)) {
					throw (NumberFormatException) e.getCause();
				}
					 
			}
			returnValue = (T) primitiveValue;
		} 
		//Chars
		else if(castToType.equals(char.class) || castToType.isAssignableFrom(Character.class)){
			//Set the value only if there is exactly one char in string (default value if it is primitive)
			Character character = null;
			if(value.length() == 1) {
				character = value.charAt(0);
			} else if(castToType.equals(char.class)) {
				character = '\u0000';
			}
			returnValue = (T) character;
		}
		//Enums (No anonymous enums)
		else if(castToType.isEnum()) { 
			Method valueOfMethod = castToType.getMethod("valueOf", String.class);
			returnValue = (T) valueOfMethod.invoke(castToType, value);
		}
		//Date
		else if(Date.class.isAssignableFrom(castToType)) {
			String pattern = DateTimeHelper.getDateFormat(value);
			if(pattern != null) {
				DateFormat dateFormat = new SimpleDateFormat(pattern);
				returnValue = (T) dateFormat.parse(value);
			}
		} 
		//BigInteger
		else if(BigInteger.class.isAssignableFrom(castToType)) {
			returnValue = (T) new BigInteger(value);
		}
		//BigDecimal
		else if(BigDecimal.class.isAssignableFrom(castToType)) {
			returnValue = (T) new BigDecimal(value.replaceAll(",", "."));
		} else {
			throw new UnknownObjectException("Can not cast this type of object. Only primitives, enums, BigInteger, BigDecimal and Date");
		}
		
		return returnValue;
	}

	/**
	 * Checks if class is a primitive number type
	 * @param clazz class to check
	 * @return true, only if is class is one of int, short, long, float, double, byte or their corresponding java.lang."wrapper" 
	 */
	public static boolean isPrimitiveOrWrapperNumberType(Class<?> clazz) {
		return clazz.equals(int.class)    || clazz.isAssignableFrom(Integer.class)
			|| clazz.equals(short.class)  || clazz.isAssignableFrom(Short.class)
			|| clazz.equals(long.class)   || clazz.isAssignableFrom(Long.class)
			|| clazz.equals(float.class)  || clazz.isAssignableFrom(Float.class)
			|| clazz.equals(double.class) || clazz.isAssignableFrom(Double.class)
			|| clazz.equals(byte.class)   || clazz.isAssignableFrom(Byte.class);
	}

	
	/**
	 * Gets corresponding java.lang."wrapper" for primitive number class.
	 * @param primitiveClazz primitive number class
	 * @return java.lang."wrapper" class if is primitive or wrapper,  null if one is not found
	 */
	public static Class<?> getPrimitiveNumberWrapper(Class<?> primitiveClazz) {
		if(!isPrimitiveOrWrapperNumberType(primitiveClazz)) return null;
		if(primitiveClazz.equals(int.class)) return Integer.class;
		if(primitiveClazz.equals(short.class)) return Short.class;
		if(primitiveClazz.equals(long.class)) return Long.class;
		if(primitiveClazz.equals(float.class)) return Float.class;
		if(primitiveClazz.equals(double.class)) return Double.class;
		if(primitiveClazz.equals(byte.class)) return Byte.class;
		return primitiveClazz;
	}

	/**
	 * Checks if class is of type boolean
	 * @param clazz class to check
	 * @return true, only if is class is boolean or its corresponding java.lang.Boolean 
	 */
	public static boolean isBoolean(Class<?> clazz) {
		return clazz.equals(boolean.class) || clazz.equals(Boolean.class);
	}

	/**
	 * Checks if class is one that can be casted from string to given class using {@link ClassCastHelper#castToSimpleClass(Class, String)}
	 * @param clazz - class to check
	 * @return true if can be handled
	 */
	public static boolean isSimpleClass(Class<?> clazz) {
		return isPrimitiveOrWrapperNumberType(clazz) || clazz.equals(String.class)
			|| clazz.equals(char.class) || clazz.equals(Character.class)
			||  clazz.equals(boolean.class) || clazz.equals(Boolean.class)
			|| clazz.equals(BigInteger.class) || clazz.equals(BigDecimal.class)
			|| clazz.equals(Date.class) || clazz.isEnum();
	}
	
	/**
	 * Checks if given throwable is NumberFormatException
	 * @param e - throwable to check
	 * @return true, if given throwable is NumberFormatException 
	 */
	private static boolean isNumberFormatException(Throwable e) {
		while (e != null) {
			if (e instanceof NumberFormatException)
				return true;
			e = e.getCause();
		}
		return false;
	}
	
}
