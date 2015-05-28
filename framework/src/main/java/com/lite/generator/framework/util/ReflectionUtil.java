package com.lite.generator.framework.util;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReflectionUtil {
	
	/**
	 * 反射获取父类的泛型参数的实际类型。
	 * @param clazz   需要反射的类，该类继承泛型父类。
	 * @param index   泛型参数所在索引，从0开始。
	 * @return 泛型参数的实际类型，如果没有实现ParameterizedType接口，既不支持泛型，直接返回<code>Object.class</code>
	 */
	public static Class getSuperClassGenericsType(Class clazz,int index){
		ParameterizedType parameterizedType = getParameterizedType(clazz,true);
		if(parameterizedType == null){
			return Object.class;
		}
		Type []params = parameterizedType.getActualTypeArguments(); 
		if (!(params[index] instanceof Class)) {
			return Object.class;   
		}   
		return (Class) params[index];
	}
	
	/**
	 * 反射获取父类的泛型参数
	 * @param clazz	需要反射的类，该类继承泛型父类。
	 * @param deep	是否递归查找，如果父类不是泛型类则继续查找，如果没有泛型父类返回null
	 * @return 泛型参数类型
	 */
	public static ParameterizedType getParameterizedType(Class clazz, boolean deep){
		if(clazz == null){
			return null;
		}
		Type genType = clazz.getGenericSuperclass();
		if( genType instanceof ParameterizedType ){
			return (ParameterizedType)genType;            
		}
		if( deep ){
			return getParameterizedType(clazz.getSuperclass(), true);
		}
		return null;
	}

    public static void loadJar(String jarPath){
        File libPath = new File(jarPath);
        File[] jarFiles = libPath.listFiles((File dir, String name) ->  name.endsWith(".jar") || name.endsWith(".zip"));
        if (jarFiles != null) {
            try {
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                for (File file : jarFiles) {
                    URL url = file.toURI().toURL();
                    try {
                        method.invoke(classLoader, url);
                        LogUtil.logger.info("[加载Jar][" + file.getName() + "]");
                    } catch (Exception e) {
                        LogUtil.logger.error("[加载Jar][" + file.getName() + "]", e);
                        throw new RuntimeException(e);
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final static List<Class> loadClasses = new ArrayList<>();

    public static void loadClass(String classPath){
        File clazzPath = new File(classPath);

        if (clazzPath.exists() && clazzPath.isDirectory()) {
            try {
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                method.invoke(classLoader, clazzPath.toURI().toURL());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

            int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;
            Stack<File> stack = new Stack<>();
            stack.push(clazzPath);

            while (!stack.isEmpty()) {
                File path = stack.pop();
                File[] classFiles = path.listFiles((File pathname) ->
                    pathname.isDirectory() || pathname.getName().endsWith(".class")
                );

                for (File subFile : classFiles) {
                    if (subFile.isDirectory()) {
                        stack.push(subFile);
                    }
                    else {
                        String className = subFile.getAbsolutePath();
                        className = className.substring(clazzPathLen, className.length() - 6);
                        className = className.replace(File.separatorChar, '.');
                        try {
                            loadClasses.add(Class.forName(className));
                            LogUtil.logger.info("[加载Class][" + className + "]");
                        }
                        catch (Exception e) {
                            LogUtil.logger.error("[加载Class][" + className + "]", e);
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

}

