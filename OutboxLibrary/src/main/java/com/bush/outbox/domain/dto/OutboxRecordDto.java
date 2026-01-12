package com.bush.outbox.domain.dto;

import com.bush.outbox.domain.entity.CrudOperationType;

public record OutboxRecordDto<T>(String tableName, CrudOperationType operationType, T payload) {
}
