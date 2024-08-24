package io.github.gabrmsouza.subscription.domain.subscription.status;

import io.github.gabrmsouza.subscription.domain.subscription.Subscription;
import io.github.gabrmsouza.subscription.domain.subscription.SubscriptionCommand.ChangeStatus;

public record TrailingSubscriptionStatus(Subscription subscription) implements SubscriptionStatus {
    @Override
    public void trailing() {
    }

    @Override
    public void incomplete() {
        this.subscription.execute(new ChangeStatus(INCOMPLETE));
    }

    @Override
    public void active() {
        this.subscription.execute(new ChangeStatus(ACTIVE));
    }

    @Override
    public void cancel() {
        this.subscription.execute(new ChangeStatus(CANCELED));
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return value();
    }
}
