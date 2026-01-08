package com.bush.search.domain.index.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CdcMetadata(@JsonProperty("__op") Operation operation,
                          @JsonProperty("__table") String tableName,
                          @JsonProperty("__source_ts_ms") Long millisecondTimestamp) {
}
