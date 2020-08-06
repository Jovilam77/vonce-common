package cn.vonce.common.utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Jovi on 2018/6/24.
 */
public class ReflectAsmUtil {

    private final Map<Class<?>, MethodAccess> methodAccessMap = new WeakHashMap<>();
    private final Map<Class<?>, ConstructorAccess> constructorAccessMap = new WeakHashMap<>();
    private static ReflectAsmUtil reflectAsmUtil;

    public static ReflectAsmUtil instance() {
        if (reflectAsmUtil == null) {
            synchronized (ReflectAsmUtil.class) {
                if (reflectAsmUtil == null) {
                    reflectAsmUtil = new ReflectAsmUtil();
                }
            }
        }
        return reflectAsmUtil;
    }

    public Object newObject(Class<?> clazz) {
        ConstructorAccess<?> constructorAccess = constructorAccessMap.get(clazz);
        if (constructorAccess == null) {
            constructorAccess = ConstructorAccess.get(clazz);
            constructorAccessMap.put(clazz, constructorAccess);
        }
        return constructorAccess.newInstance();
    }

    public Object get(Class<?> clazz, Object instance, String name) {
        if (clazz == null || name == null || name.trim().length() == 0) {
            return null;
        }
        name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return this.invoke(clazz, instance, name);
    }

    public void set(Class<?> clazz, Object instance, String name, Object value) {
        if (clazz == null || name == null || name.trim().length() == 0) {
            return;
        }
        name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        this.invoke(clazz, instance, name, value);
    }

    public Object invoke(Class<?> clazz, Object instance, String name) {
        MethodAccess methodAccess = methodAccessMap.get(clazz);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(clazz);
            methodAccessMap.put(clazz, methodAccess);
        }
        return methodAccess.invoke(instance, name);
    }

    public void invoke(Class<?> clazz, Object instance, String name, Object value) {
        MethodAccess methodAccess = methodAccessMap.get(clazz);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(clazz);
            methodAccessMap.put(clazz, methodAccess);
        }
        methodAccess.invoke(instance, name, value);
    }

    public Object invoke(Class<?> clazz, Object instance, String name, Class<?>[] parameterTypes, Object[] values) {
        MethodAccess methodAccess = methodAccessMap.get(clazz);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(clazz);
            methodAccessMap.put(clazz, methodAccess);
        }
        return methodAccess.invoke(instance, name, parameterTypes, values);
    }

}
