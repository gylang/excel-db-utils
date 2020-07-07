package com.gylang.excel.db.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射相关辅助方法
 * @author Louis
 * @date Aug 19, 2018
 */
public class ReflectionUtils {


	/**
	 * 根据方法名调用指定对象的方法
	 * @param object 要调用方法的对象
	 * @param method 要调用的方法名
	 * @param args 参数对象数组
	 * @return 反射执行结果
	 */
	public static Object invoke(Object object, String method, Object... args) {
		Object result = null;
		Class<?> clazz = object.getClass();
		Method queryMethod = getMethod(clazz, method, args);
		if(queryMethod != null) {
			try {
				result = queryMethod.invoke(object, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new NoSuchMethodException(clazz.getName() + " 类中没有找到 " + method + " 方法。");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 根据方法名和参数对象查找方法
	 * @param clazz 放射类
	 * @param name 方法名
	 * @param args 参数实例数据
	 * @return 返回反射方法
	 */
	public static Method getMethod(Class<?> clazz, String name, Object[] args) {
		Method queryMethod = null;
		Method[] methods = clazz.getMethods();
		for(Method method:methods) {
			if(method.getName().equals(name)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if(parameterTypes.length == args.length) {
					boolean isSameMethod = true;
					for(int i=0; i<parameterTypes.length; i++) {
						if (args[i] != null && !parameterTypes[i].equals(args[i].getClass())) {
							isSameMethod = false;
							break;
						}
					}
					if(isSameMethod) {
						queryMethod = method;
						break ;
					}
				}
			}
		}
		return queryMethod;
	}
}
