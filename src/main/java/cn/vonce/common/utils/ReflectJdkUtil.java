package cn.vonce.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class ReflectJdkUtil {

    private final Map<String, Method> methodMap = new WeakHashMap<>();
    private final Map<Class<?>, Constructor> constructorMap = new WeakHashMap<>();

    private static ReflectJdkUtil reflectJdkUtil;

    private ReflectJdkUtil() {

    }

    public static ReflectJdkUtil instance() {
        if (reflectJdkUtil == null) {
            synchronized (ReflectJdkUtil.class) {
                if (reflectJdkUtil == null) {
                    reflectJdkUtil = new ReflectJdkUtil();
                }
            }
        }
        return reflectJdkUtil;
    }

    public Object newObject(Class<?> clazz) {
        Constructor constructor = constructorMap.get(clazz);
        Object object = null;
        if (constructor == null) {
            try {
                constructor = clazz.getDeclaredConstructor();
                object = constructor.newInstance();
                constructorMap.put(clazz, constructor);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public Object get(Class<?> clazz, Object instance, String name) {
        if (clazz == null || name == null || name.trim().length() == 0) {
            return null;
        }
        name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return invoke(clazz, instance, name);
    }

    public void set(Class<?> clazz, Object instance, String name, Object value) {
        if (clazz == null || name == null || name.trim().length() == 0) {
            return;
        }
        name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        invoke(clazz, instance, name, value);
    }

    public Object invoke(Class<?> clazz, Object instance, String name) {
        try {
            String methodFullName = clazz.getName() + "." + name;
            Method method = methodMap.get(methodFullName);
            if (method == null) {
                method = clazz.getMethod(name);
                methodMap.put(methodFullName, method);
            }
            return method.invoke(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void invoke(Class<?> clazz, Object instance, String name, Object value) {
        try {
            String methodFullName = clazz.getName() + "." + name;
            Method method = methodMap.get(methodFullName);
            if (method == null) {
                method = getMethod(clazz.getMethods(), name, 1);
                methodMap.put(methodFullName, method);
            }
            method.invoke(instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Object invoke(Class<?> clazz, Object instance, String name, Class<?>[] parameterTypes, Object[] values) {
        try {
            String methodFullName = clazz.getName() + "." + name;
            Method method = methodMap.get(methodFullName);
            if (method == null) {
                method = clazz.getMethod(name, parameterTypes);
                methodMap.put(methodFullName, method);
            }
            return method.invoke(instance, values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method getMethod(Method[] methods, String name, int paramCount) {
        for (Method method : methods) {
            if (method.getName().equals(name) && method.getParameterTypes().length == paramCount) {
                return method;
            }
        }
        return null;
    }

}
