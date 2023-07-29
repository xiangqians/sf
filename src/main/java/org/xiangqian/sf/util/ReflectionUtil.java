package org.xiangqian.sf.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author xiangqian
 * @date 16:07 2023/07/29
 */
public class ReflectionUtil {

    /**
     * 获取父类泛型类型
     *
     * @param clazz 指定Class
     * @param index 获取指定Class的父类的第 index 个位置的泛型类型，index从0开始
     * @return : 返回父类的 index 位置的泛型类型.
     */
    public static Class<?> getSuperClassGenericType(Class<?> clazz, int index) throws NoGenericException {
        Assert.isTrue(index >= 0, "index必须大于或等于0");
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            throw new NoGenericException(clazz.toString());
        }

        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        int len = actualTypeArguments.length;
        if (index < len) {
            return (Class<?>) actualTypeArguments[index];
        }

        throw new NoGenericException(String.format("%s [%d]", clazz, index));
    }

    /**
     * 获取对象 {@link Method}
     *
     * @param object         对象
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @return
     */
    public static Method getMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        // 循环向上转型
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // Method 不在当前类定义，继续向上转型
            }
        }

        return null;
    }

}
