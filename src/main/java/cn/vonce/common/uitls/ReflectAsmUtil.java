package cn.vonce.common.uitls;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Jovi on 2018/6/24.
 */
public class ReflectAsmUtil {

    private final static Map<Class<?>, MethodAccess> methodAccessMap = new WeakHashMap<>();
    private final static Map<Class<?>, ConstructorAccess> constructorAccessMap = new WeakHashMap<>();

    public static Object getInstance(Class<?> clazz) {
        ConstructorAccess<?> constructorAccess = constructorAccessMap.get(clazz);
        if (constructorAccess == null) {
            constructorAccess = ConstructorAccess.get(clazz);
            constructorAccessMap.put(clazz, constructorAccess);
        }
        return constructorAccess.newInstance();
    }

    public static Object get(Class<?> clazz, Object instance, String name) {
        if (clazz == null || name == null || name.trim().length() == 0) {
            return null;
        }
        name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        MethodAccess methodAccess = methodAccessMap.get(clazz);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(clazz);
            methodAccessMap.put(clazz, methodAccess);
        }
        return methodAccess.invoke(instance, name);
    }

    public static void set(Class<?> clazz, Object instance, String name, Object value) {
        if (clazz == null || name == null || name.trim().length() == 0) {
            return;
        }
        name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        MethodAccess methodAccess = methodAccessMap.get(clazz);
        if (methodAccess == null) {
            methodAccess = MethodAccess.get(clazz);
            methodAccessMap.put(clazz, methodAccess);
        }
        methodAccess.invoke(instance, name, value);
    }

}
