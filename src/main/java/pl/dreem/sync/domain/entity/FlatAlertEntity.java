package pl.dreem.sync.domain.entity;

import pl.dreem.sync.domain.dto.AlertDto;
import pl.dreem.sync.domain.identifer.AlertId;

import java.util.Objects;

public final class FlatAlertEntity {

    private AlertId id;
    private String message;
    private Integer code;

    public FlatAlertEntity(final AlertId id, final String message, final Integer code) {
        this.id = id;
        this.message = message;
        this.code = code;
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

    public static FlatAlertEntity from(final AlertDto alert) {
        return new FlatAlertEntity(alert.getId(), alert.getMessage(), alert.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAlertEntity that = (FlatAlertEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(message, that.message) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, code);
    }
}
