package com.gc.common;


import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;


public class BeanUtils {
	
	public static Class<?> classForName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object objectForName(String className) {
		return newInstance(classForName(className));
	}
	
	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field[] getFields(Object o) {
		return getFields(o.getClass());
	}

	public static Field getField(Class<?> c, String f) {
		try {
			return c.getField(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Field getField(Object c, String f) {
		return getField(c.getClass(), f);
	}

	public static Field[] getFields(Class<?> c) {
		return c.getDeclaredFields();
	}

	public static Method[] getMethods(Object o) {
		return getMethods(o.getClass());
	}

	public static Method[] getMethods(Class<?> c) {
		return c.getDeclaredMethods();
	}

	public static Method getMethod(Class<?> c, String method, Class<?>[] types) {
		try {
			return c.getMethod(method, types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void copyFields(Object src, Object target) throws Exception {
		Field[] srcFields = src.getClass().getDeclaredFields();
		Field[] targetFields = target.getClass().getDeclaredFields();
		for (Field targetField : targetFields) {
			for (Field srcField : srcFields) {
				if (targetField.getName().equals(srcField.getName())) {
					targetField.setAccessible(true);
					srcField.setAccessible(true);
					targetField.set(target, Convert.parseTo(srcField.get(src),
							targetField.getType()));
					break;
				}
			}
		}
	}

	public static Method getMethod(Object c, String method) {
		return getMethod(c.getClass(), method);
	}

	public static Method getMethod(Class<?> c, String method) {
		try {
			Method[] ms = c.getMethods();
			for (Method m : ms) {
				if (m.getName().equals(method)) {
					return m;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Object c, String method, Class<?>[] types) {
		return getMethod(c.getClass(), method, types);
	}
	
	public static Field findAttribute(Object obj, String name) throws NoSuchFieldException {
		Class<?> clazz = obj.getClass();
		Field field = null;
		while (field == null && clazz != Object.class) {
				try {
					field = clazz.getDeclaredField(name);
				} catch (NoSuchFieldException e) { }
			if (field == null) {
				clazz = clazz.getSuperclass();
			}
		}
		if (field == null) {
			throw new NoSuchFieldException(name);
		}
		return field;
	}
	
	public static Object getAttribute(Object obj, String name) throws Exception {
		Field field = BeanUtils.findAttribute(obj, name);
		field.setAccessible(true);
		return field.get(obj);
	}
	
	public static void setAttribute(Object obj, String name, Object value) throws Exception {
		Field field = BeanUtils.findAttribute(obj, name);
		field.setAccessible(true);
		field.set(obj, Convert.parseTo(value,field.getType()));
	}
	
	public static Object getProperty(Object obj, String name) throws Exception {
		PropertyDescriptor pd = new PropertyDescriptor(name, obj.getClass());
		return pd.getReadMethod().invoke(obj);
	}
	
	public static void setProperty(Object obj, String name, Object value) throws Exception {
		PropertyDescriptor pd = new PropertyDescriptor(name, obj.getClass());
		pd.getWriteMethod().invoke(obj, Convert.parseTo(value, pd.getPropertyType()));
	}
	
	public static Object copy(Object source) throws Exception {
		Object target = source.getClass().newInstance();
		BeanUtils.copy(source, target);
		return target;
	}
	
	public static <T> void copy(T source, T target) {
		Field[] arr = source.getClass().getDeclaredFields();
		try {
			for (int i = 0;i < arr.length;++i) {
				Field field = arr[i];
				field.setAccessible(true);
				field.set(target, field.get(source));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void map2obj(Map<String, Object> map, Object obj) throws Exception {
		for (Entry<String, Object> item : map.entrySet()) {
			Field field = obj.getClass().getDeclaredField(item.getKey());
			field.setAccessible(true);
			field.set(obj, Convert.parseTo(item.getValue(), field.getType()));
		}
	}
	
	public static Map<String,Object> obj2map(Object obj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] arr = obj.getClass().getDeclaredFields();
		for (int i = 0;i < arr.length; ++i) {
			Field field = arr[i];
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}
		return map;
	}
	
	public static Class<?> getEntityClass(Object obj, int index) {
		return (Class<?>) ((ParameterizedType)obj.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	public static Class<?>[] getClasses(Object... objects) {
		if (objects != null) {
			Class<?>[] classes = new Class<?>[objects.length];
			for (int i = 0;i < objects.length;++i) {
				classes[i] = objects[i].getClass();
			}
			return classes;
		}
		return new Class<?>[0];
	}
	
	public static final List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
		return getClasses(packageName, false);
	}

	public static final List<Class<?>> getClasses(String packageName, boolean iterative) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));
		List<File> dirs = new Vector<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class<?>> classes = new Vector<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName, iterative));
		}
		return classes;
	}

	private static final List<Class<?>> findClasses(File directory, String packageName, boolean iterative) throws ClassNotFoundException {
		List<Class<?>> classes = new Vector<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if (iterative) {
					classes.addAll(findClasses(file, packageName + "." + file.getName(), iterative));
				}
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	
}
