package com.slicequeue.jamcar.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectUtil {

    /**
     * Class 파일로 새로운 객체 생성
     *
     * @param tClass : 생성하고자 하는 객체
     * @return T : 생성된 객체
     * @param <T>
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T getNewInstance(Class<T> tClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {

        Constructor<T> declaredConstructor = tClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance();
    }

    /**
     * 객체에 필드 값 설정
     * - 부모 필드까지 찾아 변경
     * @param obj : 필드값을 설정는 객체
     * @param fieldName : 필드명
     * @param value : 설정 값
     * @param <T>
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> void setField(Object obj, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        if (Objects.isNull(value)) {
            value = null;
        }
        Field declaredField = null;
        Class<?> clazz = obj.getClass();
        do {
            try {
                declaredField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException nsfe) {
                clazz = clazz.getSuperclass();
            }
        } while (declaredField == null && clazz != Object.class);

        if (declaredField == null)
            throw new NoSuchFieldException();

        if (!declaredField.isAccessible()) {
            declaredField.setAccessible(true);
            declaredField.set(obj, value);
            declaredField.setAccessible(false);
        } else {
            declaredField.set(obj, value);
        }
    }

    /**
     * Map의 필드명과 설정값을 설정하여 객체 생성
     *
     * @param tClass : 생성 클래스
     * @param param : 필드명(key), 설정값(value) Map
     * @return
     * @param <T>
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static <T> T getFieldValueObject(Class<T> tClass, Map<String, Object> param)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, NoSuchFieldException {
        T newInstance = getNewInstance(tClass);

        for (String fieldName : param.keySet()) {
            setField(newInstance, fieldName, param.get(fieldName));
        }

        return newInstance;
    }

    /**
     * Map의 필드명과 설정값을 설정하여 객체 목록 생성
     *
     * @param tClass : 생성 클래스
     * @param paramList : 필드명(key), 설정값(value) Map 목록
     * @return
     * @param <T>
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static <T> List<T> getFieldValueObjectList(
            Class<T> tClass, List<Map<String, Object>> paramList)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, NoSuchFieldException {
        List<T> newInstanceList = new ArrayList<>();
        for (Map<String, Object> param : paramList) {
            newInstanceList.add(getFieldValueObject(tClass, param));
        }

        return newInstanceList;
    }
}