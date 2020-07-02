package pl.dreem.sync.domain.vo;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public final class SyncResult {

    private final Instant syncStarted;
    private final Instant fetchFinished;

    private SyncResult(final Instant syncStarted, final Instant fetchFinished) {
        this.syncStarted = Objects.requireNonNull(syncStarted);
        this.fetchFinished = Objects.requireNonNull(fetchFinished);
    }

    public Instant getSyncStarted() {
        return syncStarted;
    }

    public Instant getFetchFinished() {
        return fetchFinished;
    }

    public Instant getNextSyncCriteria() {
        final Duration durationOfSync = Duration.between(syncStarted, fetchFinished);
        return syncStarted.plus(durationOfSync);
    }

    public static SyncResult from(final Instant syncStarted, final Instant fetchFinished){
        return new SyncResult(syncStarted, fetchFinished);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncResult that = (SyncResult) o;
        return Objects.equals(syncStarted, that.syncStarted) &&
                Objects.equals(fetchFinished, that.fetchFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(syncStarted, fetchFinished);
    }

    @Override
    public String toString() {
        return "SyncResult{" +
                "syncStarted=" + syncStarted +
                ", fetchFinished=" + fetchFinished +
                '}';
    }
}
