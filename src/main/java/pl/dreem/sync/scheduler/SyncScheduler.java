package pl.dreem.sync.scheduler;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.dreem.sync.domain.vo.SyncResult;
import pl.dreem.sync.service.SyncDbService;
import pl.dreem.sync.util.HasLogger;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public final class SyncScheduler implements HasLogger {

    private final SyncDbService syncDbService;
    private final ScheduledExecutorService schedulerService;

    public SyncScheduler(final SyncDbService syncDbService, final ScheduledExecutorService schedulerService) {
        this.syncDbService = Objects.requireNonNull(syncDbService);
        this.schedulerService = Objects.requireNonNull(schedulerService);
    }

    @EventListener
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        schedulePeriodicSync(Instant.now());
    }

    private void schedulePeriodicSync(final Instant syncCriteria) {
        schedulerService.schedule(() -> syncDB(syncCriteria), 2, TimeUnit.SECONDS);
    }

    private void syncDB(final Instant syncCriteria) {
        getLogger().info("Sync started");
        final SyncResult syncResult = syncDbService.syncAlerts(syncCriteria);
        schedulePeriodicSync(syncResult.getNextSyncCriteria());
        getLogger().info("Sync finished");
    }
}