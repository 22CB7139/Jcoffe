package com.sorry.utils.reflect;

import java.lang.reflect.*;
import java.util.ArrayList;

import com.nqzero.permit.Permit;
import sun.reflect.ReflectionFactory;

/**
 * created by 0x22cb7139 on 2021/6/29
 */
public class Reflections {
    public static void setAccessible(AccessibleObject member){
        // quiet runtime warnings from JDK9+
        Permit.setAccessible(member);
    }
    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        }
        catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static Method getMethod(Class<?> clazz,final String methodName,Class<?>... parameters) {
        Method method = null;
        try{
            method = clazz.getDeclaredMethod(methodName,parameters);
            setAccessible(method);
        }
        catch (NoSuchMethodException e){
            if(clazz.getSuperclass() != null)
                method = getMethod(clazz.getSuperclass(),methodName,parameters);
        }
        return method;

    }

    public static Object invoke(Object obj,String methodName,Object... parameters){
        try {
            ArrayList classes = new ArrayList();
            if (parameters != null) {
                for (Object o1 : parameters) {
                    if (o1 != null) {
                        classes.add(o1.getClass());
                    } else {
                        classes.add(null);
                    }
                }
            }
            return getMethod(obj.getClass(), methodName, (Class[]) classes.toArray(new Class[0])).invoke(obj, parameters);
        } catch (Exception e) {
            return null;
        }
    }

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Object newInstance(String className, Object ... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static <T> T createWithoutConstructor ( Class<T> classToInstantiate )
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }

    @SuppressWarnings ( {"unchecked"} )
    public static <T> T createWithConstructor ( Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs )
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        setAccessible(objCons);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        setAccessible(sc);
        return (T)sc.newInstance(consArgs);
    }



}
