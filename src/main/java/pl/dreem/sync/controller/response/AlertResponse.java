package pl.dreem.sync.controller.response;

import pl.dreem.sync.domain.dto.AlertDto;

import java.util.Objects;
import java.util.UUID;

public final class AlertResponse {

    private final UUID alertId;
    private final String message;
    private final int code;

    public AlertResponse(final UUID alertId, final String message, final int code) {
        this.alertId = alertId;
        this.message = message;
        this.code = code;
    }

    public UUID getAlertId() {
        return alertId;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public static AlertResponse from(final AlertDto alert) {
        return new AlertResponse(alert.getId().getAlertId(), alert.getMessage(), alert.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertResponse that = (AlertResponse) o;
        return code == that.code &&
                Objects.equals(alertId, that.alertId) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId, message, code);
    }

    @Override
    public String toString() {
        return "AlertResponse{" +
                "id=" + alertId +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
