package io.github.gabrmsouza.subscription.domain.subscription.status;

import io.github.gabrmsouza.subscription.domain.exceptions.DomainException;
import io.github.gabrmsouza.subscription.domain.subscription.Subscription;

public record CanceledSubscriptionStatus(Subscription subscription) implements SubscriptionStatus {
    @Override
    public void trailing() {
        throw DomainException.with("Subscription with status canceled can´t transit to trailing");
    }

    @Override
    public void incomplete() {
        throw DomainException.with("Subscription with status canceled can´t transit to incomplete");
    }

    @Override
    public void active() {
        throw DomainException.with("Subscription with status canceled can´t transit to active");
    }

    @Override
    public void cancel() {
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
