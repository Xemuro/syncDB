package pl.dreem.sync.domain.entity;

import pl.dreem.sync.domain.dto.NewAlertDto;
import pl.dreem.sync.domain.dto.UpdateAlertDto;
import pl.dreem.sync.domain.identifer.AlertId;

import java.time.Instant;
import java.util.Objects;

public final class AlertEntity {

    private AlertId id;
    private String message;
    private Integer code;
    private Instant lastUpdate;
    private boolean removed;

    public AlertEntity(final AlertId id, final String message, final int code, final Instant lastUpdate, boolean removed) {
        this.id = Objects.requireNonNull(id);
        this.message = Objects.requireNonNull(message);
        this.code = code;
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
        this.removed = removed;
    }

    public AlertId getId() {
        return id;
    }

    public void setId(AlertId id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public static AlertEntity from(final AlertId alertId, final NewAlertDto dto){
        return new AlertEntity(alertId, dto.getMessage(), dto.getCode(), Instant.now(), false);
    }
    public static AlertEntity from(final UpdateAlertDto dto){
        return new AlertEntity(dto.getId(), dto.getMessage(), dto.getCode(), Instant.now(), false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertEntity that = (AlertEntity) o;
        return removed == that.removed &&
                Objects.equals(id, that.id) &&
                Objects.equals(message, that.message) &&
                Objects.equals(code, that.code) &&
                Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, code, lastUpdate, removed);
    }

    @Override
    public String toString() {
        return "AlertEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", lastUpdate=" + lastUpdate +
                ", removed=" + removed +
                '}';
    }
}