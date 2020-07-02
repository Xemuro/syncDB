package pl.dreem.sync.db.source;

import org.springframework.stereotype.Component;
import pl.dreem.sync.db.DBConnection;
import pl.dreem.sync.domain.dto.*;
import pl.dreem.sync.domain.entity.AlertEntity;
import pl.dreem.sync.domain.identifer.AlertId;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component("sourceDB")
public class SourceDBConnectionMock implements DBConnection {

    private final Map<AlertId, AlertEntity> db = new ConcurrentHashMap<>();

    @Override
    public AlertId saveAlert(final NewAlertDto alert) {
        final AlertId alertId = AlertId.generateNew();
        db.put(alertId, AlertEntity.from(alertId, alert));
        return alertId;
    }

    @Override
    public Optional<AlertDto> getAlertById(final AlertId alertId) {
        return Optional.ofNullable(db.get(alertId))
                       .map(AlertDto::from);
    }

    @Override
    public Set<AlertDto> getAllAlerts() {
        return db.values()
                 .stream()
                 .map(AlertDto::from)
                 .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public AlertsToSyncDto getDataToSync(final Instant lastSync) {
        final Set<SyncAlertDto> alertsToSync = db.values()
                                                 .stream()
                                                 .filter(alert -> alert.getLastUpdate().isAfter(lastSync))
                                                 .map(SyncAlertDto::from)
                                                 .collect(Collectors.toUnmodifiableSet());
        return AlertsToSyncDto.from(Instant.now(), alertsToSync);
    }

    @Override
    public void updateAlert(final UpdateAlertDto alert) {
        db.put(alert.getId(), AlertEntity.from(alert));
    }

    @Override
    public void deleteAlertById(final AlertId id) {
        final AlertEntity alertEntity = db.get(id);
        alertEntity.setLastUpdate(Instant.now());
        alertEntity.setRemoved(true);
        db.put(id, alertEntity);
    }

    @Override
    public void saveOrUpdate(AlertDto alert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cleanupDeletedRecords() {
        db.values()
          .stream()
          .filter(AlertEntity::isRemoved)
          .collect(Collectors.toUnmodifiableSet())
          .forEach(alertToDelete -> db.remove(alertToDelete.getId()));
    }

}
