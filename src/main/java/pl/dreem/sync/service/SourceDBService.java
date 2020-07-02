package pl.dreem.sync.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.dreem.sync.db.DbConnectionFacade;
import pl.dreem.sync.domain.dto.*;
import pl.dreem.sync.domain.identifer.AlertId;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class SourceDBService {

    private final DbConnectionFacade dbFacade;

    public SourceDBService(@Qualifier("sourceDBFacade") final DbConnectionFacade dbFacade) {
        this.dbFacade = Objects.requireNonNull(dbFacade);
    }

    public AlertId saveAlert(final NewAlertDto dto) {
        return dbFacade.saveAlert(dto);
    }

    public void updateAlert(final UpdateAlertDto dto) {
        dbFacade.updateAlert(dto);
    }

    public void deleteAlert(final AlertId alertId) {
        dbFacade.deleteAlertById(alertId);
    }

    public Optional<AlertDto> findAllAlerts(final AlertId alertId) {
        return dbFacade.getAlertById(alertId);
    }

    public Set<AlertDto> findAllAlerts() {
        return dbFacade.getAllAlerts();
    }

    public AlertsToSyncDto getDataToSync(final Instant timestamp) {
        final AlertsToSyncDto alertsToSync = dbFacade.getAlertsToSync(timestamp);
        dbFacade.cleanupRecordsToDelete();
        return alertsToSync;
    }
}