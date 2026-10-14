package net.nco.sklad.services;

import java.time.LocalDateTime;

public interface CommittableDocument {
    Long getId();

    LocalDateTime getCommittedAt();
    void setCommittedAt(LocalDateTime committedAt);

    default boolean isCommitted() {
        return checkNotNull(getCommittedAt());
    }

    private boolean checkNotNull(Object obj){
        return obj != null;
    }
}
