package com.bush.search.domain.index;

import com.bush.search.domain.index.service.scheme.Schema;

public record ChangeDataEvent<T>(Schema schema, T payload) {
}
