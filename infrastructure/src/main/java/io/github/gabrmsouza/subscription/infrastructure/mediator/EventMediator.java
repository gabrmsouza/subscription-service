package io.github.gabrmsouza.subscription.infrastructure.mediator;

import io.github.gabrmsouza.subscription.domain.DomainEvent;
import io.github.gabrmsouza.subscription.domain.exceptions.InternalErrorException;
import io.github.gabrmsouza.subscription.infrastructure.gateway.repository.EventJdbcRepository;
import io.github.gabrmsouza.subscription.infrastructure.observer.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EventMediator {
    private static final Logger LOG = LoggerFactory.getLogger(EventMediator.class);

    private final EventJdbcRepository repository;
    private final Publisher<DomainEvent> publisher;

    public EventMediator(final EventJdbcRepository repository, final Publisher<DomainEvent> publisher) {
        this.repository = Objects.requireNonNull(repository);
        this.publisher = Objects.requireNonNull(publisher);
    }

    public void mediate(final Long eventId) {
        this.repository
            .eventOfIdAndUnprocessed(eventId)
            .ifPresentOrElse(event -> process(eventId, event), logEventNotFount(eventId));
    }

    private void process(final Long eventId, final DomainEvent event) {
        if (!this.publisher.publish(event)) {
            throw InternalErrorException.with("Failed to process event %s".formatted(eventId));
        }
        this.repository.markAsProcessed(eventId);
    }

    private Runnable logEventNotFount(final Long eventId) {
        return () -> LOG.warn("Event not found [eventId:{}]", eventId);
    }
}
