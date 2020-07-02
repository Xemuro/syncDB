package pl.dreem.sync.controller.request;

import java.util.Objects;

public final class NewAlertRequest {

    private String message;
    private int code;

    public NewAlertRequest(final String message, final  int code) {
        this.message = Objects.requireNonNull(message);
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public static NewAlertRequest from(final String message, final int code) {
        return new NewAlertRequest(message, code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewAlertRequest that = (NewAlertRequest) o;
        return code == that.code &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code);
    }

    @Override
    public String toString() {
        return "AlertRequest{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
