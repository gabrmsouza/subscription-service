package io.github.gabrmsouza.subscription.infrastructure.observer.domainevent;

import io.github.gabrmsouza.subscription.application.account.RemoveFromGroup;
import io.github.gabrmsouza.subscription.domain.DomainEvent;
import io.github.gabrmsouza.subscription.domain.subscription.SubscriptionCanceled;
import io.github.gabrmsouza.subscription.infrastructure.configuration.properties.KeycloakProperties;
import io.github.gabrmsouza.subscription.infrastructure.observer.Subscriber;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RemoveFromGroupSubscriber implements Subscriber<DomainEvent> {
    private final RemoveFromGroup removeFromGroup;
    private final String subscribersGroup;

    public RemoveFromGroupSubscriber(final RemoveFromGroup removeFromGroup, final KeycloakProperties keycloakProperties) {
        this.removeFromGroup = Objects.requireNonNull(removeFromGroup);
        this.subscribersGroup = Objects.requireNonNull(keycloakProperties).subscribersGroupId();
    }

    @Override
    public boolean test(final DomainEvent ev) {
        return ev instanceof SubscriptionCanceled;
    }

    @Override
    public void onEvent(final DomainEvent ev) {
        if (ev instanceof SubscriptionCanceled sc) {
            this.removeFromGroup.execute(new RemoveFromGroupInput(sc, subscribersGroup));
        }
    }

    record RemoveFromGroupInput(String accountId, String subscriptionId, String groupId) implements RemoveFromGroup.Input {
        public RemoveFromGroupInput(SubscriptionCanceled s, String groupId) {
            this(s.accountId(), s.subscriptionId(), groupId);
        }
    }
}