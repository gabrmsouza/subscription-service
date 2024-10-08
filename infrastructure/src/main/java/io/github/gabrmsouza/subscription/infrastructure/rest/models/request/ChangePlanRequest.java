package io.github.gabrmsouza.subscription.infrastructure.rest.models.request;

import io.github.gabrmsouza.subscription.application.plan.ChangePlan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record ChangePlanRequest(
        @NotNull Long planId,
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 1000) String description,
        @NonNull Double price,
        @NotBlank @Size(min = 3, max = 3) String currency,
        Boolean active
) implements ChangePlan.Input {

}