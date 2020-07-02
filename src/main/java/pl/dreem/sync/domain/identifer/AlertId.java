package pl.dreem.sync.domain.identifer;

import java.util.Objects;
import java.util.UUID;

public final class AlertId {

    private final UUID alertId;

    private AlertId(final UUID alertId) {
        this.alertId = Objects.requireNonNull(alertId);
    }

    public UUID getAlertId() {
        return alertId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertId alertId = (AlertId) o;
        return Objects.equals(this.alertId, alertId.alertId);
    }

    public static AlertId from(final UUID alertId){
        return new AlertId(alertId);
    }

    public static AlertId generateNew(){
        return new AlertId(UUID.randomUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId);
    }

    @Override
    public String toString() {
        return "AlertId{" +
                "alertID=" + alertId +
                '}';
    }
}
