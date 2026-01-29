package com.bush.search.domain.index.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Constants used to classify operation in change data capture event
 */
@RequiredArgsConstructor
@Getter
public enum CrudOperationConstants {
    CREATE("C"), READ("R"), UPDATE("U"), DELETE("D");

    private final String code;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static CrudOperationConstants fromCode(String code) {
        for (CrudOperationConstants crudOperationConstants : CrudOperationConstants.values()) {
            if (crudOperationConstants.code.equals(code)) {
                return crudOperationConstants;
            }
        }
        throw new IllegalArgumentException("Unknown CRUD operation");
    }
}
