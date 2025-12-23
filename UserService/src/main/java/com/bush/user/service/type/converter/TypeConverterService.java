package com.bush.user.service.type.converter;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TypeConverterService {
    public <T> List<T> convertObjectToParametrizedList(Object object, Class<T> clazz) {
        if (Objects.isNull(object)) {
            return Collections.emptyList();
        }

        if (Collection.class.isAssignableFrom(object.getClass())) {
            Collection<?> collection = (Collection<?>) object;
            return collection.stream()
                    .map(clazz::cast)
                    .toList();
        }
        throw new ClassCastException("Object parameter isn't a Collection class");
    }
}
