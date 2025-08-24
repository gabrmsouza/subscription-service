package io.github.gabrmsouza.subscription.infrastructure.mediator;

import io.github.gabrmsouza.subscription.domain.DomainEvent;
import io.github.gabrmsouza.subscription.domain.Fixture;
import io.github.gabrmsouza.subscription.domain.UnitTest;
import io.github.gabrmsouza.subscription.domain.account.AccountCreated;
import io.github.gabrmsouza.subscription.infrastructure.gateway.repository.EventJdbcRepository;
import io.github.gabrmsouza.subscription.infrastructure.observer.Publisher;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;

class EventMediatorTest extends UnitTest {

    @Mock
    private EventJdbcRepository eventRepository;

    @Mock
    private Publisher<DomainEvent> publisher;

    @InjectMocks
    private EventMediator eventMediator;

    @Test
    public void givenEvent_whenCallsMediate_shouldFinishOk() {
        // given
        var expectedEventId = 123L;

        when(publisher.publish(any())).thenReturn(true);

        when(eventRepository.eventOfIdAndUnprocessed(expectedEventId))
                .thenReturn(Optional.of(new AccountCreated(Fixture.Accounts.john())));

        doNothing().when(eventRepository).markAsProcessed(expectedEventId);

        // when
        eventMediator.mediate(expectedEventId);

        // then
        verify(eventRepository, times(1)).markAsProcessed(expectedEventId);
        verify(publisher, times(1)).publish(any());
    }
}