package pl.dreem.sync.domain.dto;

import pl.dreem.sync.controller.request.UpdateAlertRequest;
import pl.dreem.sync.domain.identifer.AlertId;

import java.util.Objects;

public final class UpdateAlertDto {

    private final AlertId id;
    private final String message;
    private final int code;

    public UpdateAlertDto(final AlertId id, final String message, final int code) {
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

    public static UpdateAlertDto from(final AlertId alertId, final UpdateAlertRequest dto){
        return new UpdateAlertDto(alertId, dto.getMessage(), dto.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAlertDto that = (UpdateAlertDto) o;
        return code == that.code &&
                Objects.equals(id, that.id) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, code);
    }

    @Override
    public String toString() {
        return "UpdateAlertDto{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
