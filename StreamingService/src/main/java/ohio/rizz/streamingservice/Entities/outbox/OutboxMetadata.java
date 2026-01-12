package ohio.rizz.streamingservice.Entities.outbox;

import com.bush.adapter.uuid.UuidTimeEpochGeneratorAdapter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metadata_outbox")
public class OutboxMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(algorithm = UuidTimeEpochGeneratorAdapter.class)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID operationId;
    @Column(name = "table_name", nullable = false, length = 63)
    private String tableName;
    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private CrudOperationType operationType;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    @Column(nullable = false)
    private String payload;
}
