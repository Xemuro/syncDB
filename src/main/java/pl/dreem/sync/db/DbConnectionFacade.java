package pl.dreem.sync.db;

import org.springframework.scheduling.annotation.Async;
import pl.dreem.sync.domain.dto.AlertDto;
import pl.dreem.sync.domain.dto.AlertsToSyncDto;
import pl.dreem.sync.domain.dto.NewAlertDto;
import pl.dreem.sync.domain.dto.UpdateAlertDto;
import pl.dreem.sync.domain.identifer.AlertId;
import pl.dreem.sync.util.HasLogger;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class DbConnectionFacade implements HasLogger {

    private final DBConnection dbConnection;

    public DbConnectionFacade(final DBConnection dbConnection) {
        this.dbConnection = Objects.requireNonNull(dbConnection);
    }

    public AlertId saveAlert(final NewAlertDto dto) {
        getLogger().info("Adding new alert: " + dto.toString());
        return dbConnection.saveAlert(dto);
    }

    public void updateAlert(final UpdateAlertDto dto) {
        getLogger().info("Updating alert: " + dto.toString());
        dbConnection.updateAlert(dto);
    }

    public void deleteAlertById(final AlertId alertId) {
        getLogger().info("Deleting alert: " + alertId.getAlertId().toString());
        dbConnection.deleteAlertById(alertId);
    }

    public Optional<AlertDto> getAlertById(final AlertId alertId) {
        return dbConnection.getAlertById(alertId);
    }

    public Set<AlertDto> getAllAlerts() {
        return dbConnection.getAllAlerts();
    }

    public AlertsToSyncDto getAlertsToSync(final Instant timestamp) {
        final AlertsToSyncDto alertsToSync = dbConnection.getDataToSync(timestamp);
        getLogger().info("Fetched " + alertsToSync.getAlerts().size() + " to sync.");
        return alertsToSync;
    }

    public void saveOrUpdate(final AlertDto alert) {
        dbConnection.saveOrUpdate(alert);
    }

    @Async
    public void cleanupRecordsToDelete() {
        dbConnection.cleanupDeletedRecords();
    }
}
