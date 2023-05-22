package com.br.saude.infrastructure.support;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.*;
import com.br.saude.infrastructure.model.PersistenceEntity;
import com.br.saude.infrastructure.repository.BaseRepository;
import com.br.saude.infrastructure.support.GsonSup.GSONSup;
import com.br.saude.user.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.beans.PropertyDescriptor;

public class ObjectUtils {
    public final static Gson gson = GSONSup.registerGson();

    public static <T extends PersistenceEntity> T updateOrSaveObject(Map<String, Object> updates,
            BaseRepository<T> objectRepository) {

        String id = updates.containsKey("id") ? (String) updates.get("id") : null;
        if (id == null)
            return null;

        T object = objectRepository.findById(id);

        if (object == null)
            return null;

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

            try {
                Field field = getAllFields(object.getClass()).get(fieldName);
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                Method setterMethod = object.getClass().getMethod(setterName, fieldType);

                if (value == null || fieldType.isAssignableFrom(value.getClass())) {
                    setterMethod.invoke(object, value == null ? null : value);
                } else {
                    try {
                        Object related = gson.fromJson(value.toString(), fieldType);
                        System.out.println("related " + related.toString());

                        // Set the related object using the setter method
                        setterMethod.invoke(object, related);
                    } catch (JsonSyntaxException e) {
                        System.out.println("Erro ao instaciar " + e.getCause());
                    }
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Error-----\n " + e);
            }
        }
        return objectRepository.save(object);

    }

    public static Map<String, Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFieldsList(superClass));
        }

        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        return fieldMap;
    }

    public static List<Field> getAllFieldsList(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFieldsList(superClass));
        }
        return fields;
    }
}
