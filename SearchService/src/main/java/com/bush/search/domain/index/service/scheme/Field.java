package com.bush.search.domain.index.service.scheme;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Field(String type, Boolean optional, @JsonProperty("field") String fieldName) {
}
