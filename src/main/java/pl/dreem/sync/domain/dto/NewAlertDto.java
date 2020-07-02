package pl.dreem.sync.domain.dto;

import pl.dreem.sync.controller.request.NewAlertRequest;

import java.util.Objects;

public final class NewAlertDto {

    private final String message;
    private final int code;

    private NewAlertDto(final String message, final int code) {
        this.message = Objects.requireNonNull(message);
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public static NewAlertDto from(NewAlertRequest request){
        return new NewAlertDto(request.getMessage(), request.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewAlertDto that = (NewAlertDto) o;
        return code == that.code &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code);
    }

    @Override
    public String toString() {
        return "NewAlertDto{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
