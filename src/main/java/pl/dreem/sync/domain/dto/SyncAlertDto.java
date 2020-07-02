package pl.dreem.sync.domain.dto;

import pl.dreem.sync.domain.entity.AlertEntity;
import pl.dreem.sync.domain.identifer.AlertId;
import pl.dreem.sync.domain.vo.SyncOperation;

import java.util.Objects;

public final class SyncAlertDto {

    private final AlertId id;
    private final String message;
    private final int code;
    private final SyncOperation operation;

    private SyncAlertDto(final AlertId id, final String message, final int code, final SyncOperation operation) {
        this.id = Objects.requireNonNull(id);
        this.message = Objects.requireNonNull(message);
        this.code = Objects.requireNonNull(code);
        this.operation = Objects.requireNonNull(operation);
    }

    public AlertId getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public SyncOperation getOperation() {
        return operation;
    }

    public static SyncAlertDto from(final AlertEntity entity) {
        final SyncOperation syncOperation = entity.isRemoved() ? SyncOperation.REMOVE : SyncOperation.SAVE_OR_UPDATE;
        return new SyncAlertDto(entity.getId(), entity.getMessage(), entity.getCode(), syncOperation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncAlertDto that = (SyncAlertDto) o;
        return code == that.code &&
                Objects.equals(id, that.id) &&
                Objects.equals(message, that.message) &&
                operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, code, operation);
    }

    @Override
    public String toString() {
        return "SyncAlertDto{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", operation=" + operation +
                '}';
    }
}
