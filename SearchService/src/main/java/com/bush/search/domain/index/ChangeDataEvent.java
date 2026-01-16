package com.bush.search.domain.index;

import com.bush.search.domain.index.service.Operation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ChangeDataEvent<T>(@JsonProperty("operation_id") UUID operationId,
                                 @JsonProperty("object_name") String objectName,
                                 @JsonProperty("operation_type") Operation operationType,
                                 @JsonProperty("created_at") ZonedDateTime createdAt,
                                 @JsonUnwrapped T payload) {
}
