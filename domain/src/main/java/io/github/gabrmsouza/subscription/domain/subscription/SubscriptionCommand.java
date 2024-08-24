package io.github.gabrmsouza.subscription.domain.subscription;


import io.github.gabrmsouza.subscription.domain.AssertionConcern;
import io.github.gabrmsouza.subscription.domain.plan.Plan;

public sealed interface SubscriptionCommand extends AssertionConcern {
    record CancelSubscription() implements SubscriptionCommand {}

    record IncompleteSubscription(String aReason, String aTransactionId) implements SubscriptionCommand {
        public IncompleteSubscription {
            this.assertArgumentNotEmpty(aTransactionId, "'transactionId' should not be empty");
        }
    }

    record RenewSubscription(Plan selectedPlan, String aTransactionId) implements SubscriptionCommand {
        public RenewSubscription {
            this.assertArgumentNotNull(selectedPlan, "'selectedPlan' should not be empty");
            this.assertArgumentNotEmpty(aTransactionId, "'transactionId' should not be empty");
        }
    }

    record ChangeStatus(String status) implements SubscriptionCommand {
        public ChangeStatus {
            this.assertArgumentNotEmpty(status, "'status' should not be empty");
        }
    }
}