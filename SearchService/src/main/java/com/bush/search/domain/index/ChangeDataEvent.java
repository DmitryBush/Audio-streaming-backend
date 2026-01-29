package com.bush.search.domain.index;

import com.bush.search.domain.index.service.CrudOperationConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ChangeDataEvent<T>(@JsonProperty("operation_id") UUID operationId,
                                 @JsonProperty("object_name") String objectName,
                                 @JsonProperty("operation_type") CrudOperationConstants crudOperationConstantsType,
                                 @JsonProperty("created_at") ZonedDateTime createdAt,
                                 T payload) {
}
