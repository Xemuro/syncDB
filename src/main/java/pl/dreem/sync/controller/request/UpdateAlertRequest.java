package pl.dreem.sync.controller.request;

import java.util.Objects;

public final class UpdateAlertRequest {

    private final String message;
    private final int code;

    public UpdateAlertRequest(final String message, final int code) {
        this.message = Objects.requireNonNull(message);
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public static UpdateAlertRequest from(final String message, final int code) {
        return new UpdateAlertRequest(message, code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAlertRequest that = (UpdateAlertRequest) o;
        return code == that.code &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code);
    }

    @Override
    public String toString() {
        return "UpdateAlertDto{" +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
