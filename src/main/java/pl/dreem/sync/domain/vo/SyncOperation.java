package pl.dreem.sync.domain.vo;

public enum SyncOperation {

    SAVE_OR_UPDATE(false),
    REMOVE(true);

    boolean delete;

    SyncOperation(boolean delete) {
        this.delete = delete;
    }

    public boolean isDeleteOperation() {
        return delete;
    }
}
