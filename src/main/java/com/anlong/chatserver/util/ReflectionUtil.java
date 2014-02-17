package com.anlong.chatserver.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

	@SuppressWarnings("unchecked")
	public static Object invokeMethod(Object obj, String mehtodName,Object... parameter) {
		Class c = obj.getClass();
		Method method = null;
		Object result = null;
		if (parameter == null) {
			try {
				method = c.getMethod(mehtodName, new Class[] {});
				result = method.invoke(obj, new Object[] {});
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			Class[] clas = new Class[parameter.length];
			for (int i = 0; i < parameter.length; i++) {
				clas[i] = parameter[i].getClass();
			}
			try {
				method = c.getMethod(mehtodName, clas);
				result = method.invoke(obj, parameter);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

}