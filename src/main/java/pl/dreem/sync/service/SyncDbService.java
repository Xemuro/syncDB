package pl.dreem.sync.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dreem.sync.db.DbConnectionFacade;
import pl.dreem.sync.domain.dto.AlertDto;
import pl.dreem.sync.domain.dto.AlertsToSyncDto;
import pl.dreem.sync.domain.dto.SyncAlertDto;
import pl.dreem.sync.domain.vo.SyncResult;
import pl.dreem.sync.util.HasLogger;

import java.time.Instant;
import java.util.Objects;

@Component
public final class SyncDbService implements HasLogger {

    private final DbConnectionFacade replicateFacade;
    private final SourceDBService sourceDBService;

    public SyncDbService(@Qualifier("replicateDBFacade") final DbConnectionFacade replicateFacade,
                         final SourceDBService sourceDBService) {
        this.replicateFacade = Objects.requireNonNull(replicateFacade);
        this.sourceDBService = Objects.requireNonNull(sourceDBService);
    }

    public SyncResult syncAlerts(final Instant lastSyncTimestamp) {
        final AlertsToSyncDto alertsToSync = sourceDBService.getDataToSync(lastSyncTimestamp);
        alertsToSync.getAlerts()
                    .forEach(this::syncAlerts);
        return SyncResult.from(lastSyncTimestamp, alertsToSync.getFetchTimestamp());
    }

    private void syncAlerts(SyncAlertDto syncAlert) {
        getLogger().info("Syncing: " + syncAlert.toString());
        if (syncAlert.getOperation().isDeleteOperation()) {
            replicateFacade.deleteAlertById(syncAlert.getId());
        } else {
            replicateFacade.saveOrUpdate(AlertDto.from(syncAlert));
        }
    }
}