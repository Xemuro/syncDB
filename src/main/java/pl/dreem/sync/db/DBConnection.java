package pl.dreem.sync.db;

import pl.dreem.sync.domain.dto.*;
import pl.dreem.sync.domain.identifer.AlertId;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface DBConnection {
    AlertId saveAlert(NewAlertDto dto);

    void updateAlert(UpdateAlertDto dto);

    void deleteAlertById(AlertId alertId);

    void saveOrUpdate(AlertDto entity);

    Optional<AlertDto> getAlertById(AlertId alertId);

    Set<AlertDto> getAllAlerts();

    AlertsToSyncDto getDataToSync(Instant timestamp);

    void cleanupDeletedRecords();
}