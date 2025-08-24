package io.github.gabrmsouza.subscription.infrastructure.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventMsg(@JsonProperty("event_id") Long eventId) {
}
