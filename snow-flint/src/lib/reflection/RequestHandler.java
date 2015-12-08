package lib.reflection;


import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.rmi.activation.UnknownObjectException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class RequestHandler {


  /**
   * Instantiates class with values from request parameters All class variables must have
   * corresponding setters Mark up for html <input> element Regular variable : name="variableName"
   * Another class : name="anotherClassVariableName.variableName" List : name="listName[index] List
   * of classes : name="listName[index].variableName" Depth of theses links may be as large as
   * needed. (class within class within list within class etc...) WARNING! Does not work with
   * anonymous enums!
   * @param clazz - class to instantiate
   * @param requestParams - html form request parameters
   * @param variableClassPrefix - class name that will be ignored ie. prefix = something then
   *          something.nothing.list[0] will be read as nothing.list[0]
   * @return instantiated class
   */
  protected <T> T getFormView(Class<T> clazz, Map<String, List<String>> requestParams) {
    Map<String, String[]> params = new HashMap<>();
    for (Entry<String, List<String>> entry : requestParams.entrySet()) {
      params.put(entry.getKey(), entry.getValue().toArray(new String[0]));
    }
    return (T) getFormView(clazz, params, null);
  }

  /**
   * Instantiates class with values from request parameters
   * @param clazz  - class to instantiate
   * @param requestParams - html form request parameters
   * @param variableClassPrefix - class name that will be ignored ie. prefix = something then
   *          something.nothing.list[0] will be read as nothing.list[0]
   * @return instantiated class
   */
  // TODO(Kristjan Kiolein) Is it safe to check enum with ENUM.isEnum()?
  //Interaction with anonymous enums.
  @SuppressWarnings("unchecked")
  private <T> T getFormView(Class<T> clazz, Map<String, String[]> requestParams,
      String variableClassPrefix) {
    T clazzObject = null;
    try {
      // If desired class is one of "simple" classes
      // Simple classes are all primitives, Date, Enum, BigInteger, BigDecimal
      if (ClassCastHelper.isSimpleClass(clazz)) {
        try {
          if (requestParams.get(variableClassPrefix).length == 1) {
            return (T) ClassCastHelper.castToSimpleClass(clazz,
                requestParams.get(variableClassPrefix)[0]);
          } else {
            throw new RuntimeException(
                "Parameter doesn't have exactly one value. Can't assign value to field.");
          }
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
            | SecurityException | ParseException | UnknownObjectException e) {
          // String message = "Error while handeling simple values.";
          e.printStackTrace();
        }
      //If desired class is not handled by castToSimpleClass
      } else {
        clazzObject = clazz.newInstance();
        for (Method clazzMethod : clazz.getMethods()) {
          try {
            if (clazzMethod.getName().startsWith("set")) {
              Class<?>[] methodParamterTypes = clazzMethod.getParameterTypes();
              // Can only work with setters that take one argument.
              if (methodParamterTypes != null && methodParamterTypes.length == 1) {
                String paramName = Introspector.decapitalize(clazzMethod.getName().substring(3));
                paramName = variableClassPrefix == null ? paramName : variableClassPrefix 
                    + "." + paramName;
                // Handel collections
                if (Collection.class.isAssignableFrom(methodParamterTypes[0])) {

                  ParameterizedType type = (ParameterizedType) clazzMethod
                      .getGenericParameterTypes()[0];
                  Class<?> genericType = (Class<?>) type.getActualTypeArguments()[0];

                  // Lists
                  if (List.class.isAssignableFrom(methodParamterTypes[0])) {
                    clazzMethod.invoke(clazzObject,
                        getFormViewList(genericType, requestParams, paramName));
                  // Sets
                  } else if (Set.class.isAssignableFrom(methodParamterTypes[0])) {
                    clazzMethod.invoke(clazzObject,
                        getFormViewSet(genericType, requestParams, paramName));
                  }
                } else {
                  // Handel simple values
									String[] argumentStringValues = requestParams.get(paramName);
									// If more than one value is present then can't decide which one to use, so skip this parameter.
									//TODO(kristjan.kiolein):Can it be done with fewer checks?
									if (argumentStringValues != null && argumentStringValues.length == 1 && !argumentStringValues[0].equals("") || hasClassVariablesInRequest(paramName, requestParams) //TODO(kristjan.kiolein):Could be checked along with lists or lists could be checked here -> remove separation bewtween similar things
											|| ClassCastHelper.isBoolean(methodParamterTypes[0])) {
										//Set simple values (using setters)
										if (ClassCastHelper.isSimpleClass(methodParamterTypes[0])) {
											String argument = argumentStringValues == null ? null : argumentStringValues[0];
											T argumentValue = (T) ClassCastHelper.castToSimpleClass(methodParamterTypes[0], argument);
											if (argumentValue != null)
												clazzMethod.invoke(clazzObject, argumentValue);
										}
										//If is another class try to initiate it
										else {
											Object o = getFormView(methodParamterTypes[0], requestParams, paramName);
											clazzMethod.invoke(clazzObject, methodParamterTypes[0].cast(o));
										}
									} else if (argumentStringValues != null && argumentStringValues.length > 1) {
										throw new RuntimeException("Parameter doesn't have exactly one value. Can't assign value to field. Parameter name = " + paramName + ", parameter values : " + argumentStringValues);
									}
								}
							}
						}
					} catch (RuntimeException | InvocationTargetException | NoSuchMethodException | ParseException | IllegalAccessException | UnknownObjectException e) {
						String message = "Error while getting form values";
						e.printStackTrace();
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e1) {
			String message = "Cannot create instance of clazz. Clazz must have public constructor with no arguments defined.";
			System.err.println(message);
			e1.printStackTrace();

		}
		return clazzObject;
	}

	/**
	 * Checks if there are any variables for parameterName. ie.:
	 * parameterName.variable.listElement[1].othervariable -> true parameterName
	 * -> false no "parameterName" in requestParams -> false
	 * 
	 * @param parameterName
	 *            - name to check
	 * @param requestParams
	 *            - map of request parameters
	 * @return - true if there is at least one
	 */
	private boolean hasClassVariablesInRequest(String parameterName, Map<String, String[]> requestParams) {
		for (String key : requestParams.keySet()) {
			if (key.startsWith(parameterName + "."))
				return true;
		}
		return false;
	}

	/**
	 * Fills a list with given class using requestParameters for values
	 * 
	 * @param clazz
	 *            - class of objects in the list
	 * @param requestParams
	 *            - map of request parameters that contain values for class
	 *            objects
	 * @param collectionName
	 *            - how is the list named in request, excluding brackets'[]'.
	 *            ie. somelist or someClass.someList
	 * @return List filled with object that have values from request parameters.
	 */
	private <T> List<T> getFormViewList(Class<T> clazz, Map<String, String[]> requestParams, String collectionName) {

		List<T> result = new ArrayList<>();
		Set<String> listClasses = getCollectionPrefixes(requestParams, collectionName);

		for (String classNamePath : listClasses) {
			T someClass = (T) getFormView(clazz, requestParams, classNamePath);
			if (someClass != null) {
				result.add(someClass);
			}
		}

		return result;
	}

	/**
	 * Fills a set with given class using requestParameters for values
	 * 
	 * @param clazz
	 *            - class of objects in the set
	 * @param requestParams
	 *            - map of request parameters that contain values for class
	 *            objects
	 * @param collectionName
	 *            - how is the set named in request, excluding brackets'[]'. ie.
	 *            someSet or someClass.someSet
	 * @return Set filled with object that have values from request parameters.
	 */
	private <T> Set<T> getFormViewSet(Class<T> clazz, Map<String, String[]> requestParams, String collectionName) {

		Set<T> result = new HashSet<>();
		Set<String> listClasses = getCollectionPrefixes(requestParams, collectionName);

		for (String classNamePath : listClasses) {
			T someClass = (T) getFormView(clazz, requestParams, classNamePath);
			if (someClass != null) {
				result.add(someClass);
			}
		}

		return result;
	}

	/**
	 * Gets all parameters that start with collectionName and have index
	 * appended.
	 * 
	 * @param requestParams
	 *            - map of request parameters that contain values for class
	 *            objects
	 * @param collectionName
	 *            - how is the collection named in request, excluding brackets.
	 *            ie. someCollection or someClass.someCollection
	 * @return set of collection prefixes
	 */
	private Set<String> getCollectionPrefixes(Map<String, String[]> requestParams, String collectionName) {

		Set<String> listClasses = new HashSet<>();
		String formInputFieldsPattern = "(([a-z_$]\\w+\\[\\d+\\]\\.?)|([a-z_$]\\w+\\.?))+(?<!\\.)$";
		String firstNumberInBracket = "^(\\[\\d+\\])\\..*";

		for (Entry<String, String[]> entry : requestParams.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith(collectionName) && key.matches(formInputFieldsPattern)) {
				String substring = key.substring(collectionName.length());
				String indexOfClass = substring.replaceFirst(firstNumberInBracket, "$1");
				listClasses.add(collectionName + indexOfClass);
			}
		}

		return listClasses;
	}


}
