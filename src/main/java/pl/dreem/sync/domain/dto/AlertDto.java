package pl.dreem.sync.domain.dto;

import pl.dreem.sync.domain.entity.AlertEntity;
import pl.dreem.sync.domain.entity.FlatAlertEntity;
import pl.dreem.sync.domain.identifer.AlertId;

import java.util.Objects;

public final class AlertDto {

    private final AlertId id;
    private final String message;
    private final int code;

    private AlertDto(final AlertId id, final String message, final int code) {
        this.id = Objects.requireNonNull(id);
        this.message = Objects.requireNonNull(message);
        this.code = code;
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

    public static AlertDto from(final AlertEntity entity) {
        return new AlertDto(entity.getId(), entity.getMessage(), entity.getCode());
    }

    public static AlertDto from(final SyncAlertDto alert) {
        return new AlertDto(alert.getId(), alert.getMessage(), alert.getCode());
    }

    public static AlertDto from(final FlatAlertEntity entity) {
        return new AlertDto(entity.getId(), entity.getMessage(), entity.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertDto alertDto = (AlertDto) o;
        return code == alertDto.code &&
                Objects.equals(id, alertDto.id) &&
                Objects.equals(message, alertDto.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, code);
    }

    @Override
    public String toString() {
        return "AlertDto{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}