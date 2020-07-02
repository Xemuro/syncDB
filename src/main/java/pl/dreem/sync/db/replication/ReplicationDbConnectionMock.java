package pl.dreem.sync.db.replication;

import org.springframework.stereotype.Component;
import pl.dreem.sync.db.DBConnection;
import pl.dreem.sync.domain.dto.*;
import pl.dreem.sync.domain.entity.FlatAlertEntity;
import pl.dreem.sync.domain.identifer.AlertId;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component("replicateDB")
public final class ReplicationDbConnectionMock implements DBConnection {

    private final Map<AlertId, FlatAlertEntity> db = new ConcurrentHashMap<>();

    @Override
    public AlertId saveAlert(final NewAlertDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateAlert(final UpdateAlertDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAlertById(final AlertId alertId) {
        db.remove(alertId);
    }

    @Override
    public void saveOrUpdate(final AlertDto alert) {
        db.put(alert.getId(), FlatAlertEntity.from(alert));
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
    public AlertsToSyncDto getDataToSync(final Instant timestamp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cleanupDeletedRecords() {
        throw new UnsupportedOperationException();
    }
}