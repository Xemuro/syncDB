package pl.dreem.sync.domain.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public final class AlertsToSyncDto {

    private final Instant fetchTimestamp;
    private final Set<SyncAlertDto> alerts;

    private AlertsToSyncDto(final Instant fetchTimestamp, final Set<SyncAlertDto> alerts) {
        this.fetchTimestamp = Objects.requireNonNull(fetchTimestamp);
        this.alerts = Objects.requireNonNull(alerts);
    }

    public Instant getFetchTimestamp() {
        return fetchTimestamp;
    }

    public Set<SyncAlertDto> getAlerts() {
        return alerts;
    }

    public static AlertsToSyncDto from(final Instant fetchTimestamp, final Set<SyncAlertDto> alerts) {
        return new AlertsToSyncDto(fetchTimestamp, alerts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertsToSyncDto that = (AlertsToSyncDto) o;
        return Objects.equals(fetchTimestamp, that.fetchTimestamp) &&
                Objects.equals(alerts, that.alerts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fetchTimestamp, alerts);
    }

    @Override
    public String toString() {
        return "AlertsToSync{" +
                "fetchTimestamp=" + fetchTimestamp +
                ", alerts=" + alerts +
                '}';
    }
}
