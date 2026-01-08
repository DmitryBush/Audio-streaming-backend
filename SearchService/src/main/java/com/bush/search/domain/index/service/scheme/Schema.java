package com.bush.search.domain.index.service.scheme;

import java.util.List;

public record Schema(String type, List<Field> fields, Boolean optional, String topicName) {
}
