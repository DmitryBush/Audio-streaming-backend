package ohio.rizz.streamingservice.dto.outbox;

import ohio.rizz.streamingservice.Entities.outbox.CrudOperationType;

public record OutboxMetadataDto<T>(String tableName, CrudOperationType operationType, T payload) {
}
