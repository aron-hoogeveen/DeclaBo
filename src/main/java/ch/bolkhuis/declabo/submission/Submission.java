package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.transaction.Transaction;
import ch.bolkhuis.declabo.user.FundUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Submission {

    private @Nullable Long id;
    private @NotNull LocalDate date;
    private @NotNull String name;
    private @NotNull String description;
    private long amount;
    private @NotNull FundUser paidBy;
    private @NotNull LocalDate createdOn;
    private @NotNull Set<Transaction> transactions;
    private @NotNull Set<URI> attachments;
    private boolean processed;
    private boolean settled;
    private @Nullable Event event;

    private Submission() {
        this.transactions = new HashSet<>();
        this.attachments = new HashSet<>();
    }

    public @Nullable Long getId() {
        return id;
    }

    private void setId(@Nullable Long id) {
        this.id = id;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    private void setDate(@NotNull LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public @NotNull String getName() {
        return name;
    }

    private void setName(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    public @NotNull String getDescription() {
        return description;
    }

    private void setDescription(@NotNull String description) {
        this.description = Objects.requireNonNull(description);
    }

    public long getAmount() {
        return amount;
    }

    private void setAmount(long amount) {
        this.amount = amount;
    }

    public @NotNull FundUser getPaidBy() {
        return paidBy;
    }

    private void setPaidBy(@NotNull FundUser paidBy) {
        this.paidBy = Objects.requireNonNull(paidBy);
    }

    public @NotNull LocalDate getCreatedOn() {
        return createdOn;
    }

    private void setCreatedOn(@NotNull LocalDate createdOn) {
        this.createdOn = Objects.requireNonNull(createdOn);
    }

    /**
     * Gets an unmodifiable copy of the transactions.
     *
     * @return the transactions
     */
    public @NotNull Set<Transaction> getTransactions() {
        return Set.copyOf(transactions);
    }

    private void setTransactions(@NotNull Set<Transaction> transactions) {
        this.transactions = Objects.requireNonNull(transactions);
    }

    /**
     * Gets an unmodifiable copy of the attachments.
     *
     * @return the attachments
     */
    public @NotNull Set<URI> getAttachments() {
        return Set.copyOf(attachments);
    }

    private void setAttachments(@NotNull Set<URI> attachments) {
        this.attachments = Objects.requireNonNull(attachments);
    }

    public boolean isProcessed() {
        return processed;
    }

    private void setProcessed(boolean isProcessed) {
        this.processed = isProcessed;
    }

    public boolean isSettled() {
        return settled;
    }

    private void setSettled(boolean isSettled) {
        this.settled = isSettled;
    }

    public @Nullable Event getEvent() {
        return event;
    }

    private void setEvent(@Nullable Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Submission that = (Submission) o;

        if (!date.equals(that.date)) return false;
        if (!name.equals(that.name)) return false;
        return paidBy.equals(that.paidBy);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + paidBy.hashCode();
        return result;
    }

    /**
     * Gets a new {@code Builder} for a {@code Submission}.
     *
     * @return a Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Gets a new {@code Builder} for a {@code Submission} and sets the encapsulated Submission to
     * be a copy of the provided {@code submission}. A shallow copy of the submission will be used.
     *
     * @param submission the submission to copy
     * @return a Builder
     */
    public static Builder builder(@NotNull Submission submission) {
        return new Builder(submission);
    }

    /**
     * Builder class for {@code Submission}.
     */
    public static class Builder {

        private transient @NotNull Submission obj;

        private Builder() {
            obj = new Submission();
        }

        private Builder(@NotNull Submission submission) {
            this.obj = new Submission();
            this.obj.id = submission.id;
            this.obj.date = submission.date;
            this.obj.name = submission.name;
            this.obj.description = submission.description;
            this.obj.amount = submission.amount;
            this.obj.paidBy = submission.paidBy;
            this.obj.transactions = submission.transactions;
            this.obj.attachments = submission.attachments;
            this.obj.processed = submission.processed;
            this.obj.settled = submission.settled;
            this.obj.createdOn = submission.createdOn;
            this.obj.event = submission.event;
        }

        public Builder setId(Long id) {
            this.obj.id = id;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.obj.date = date;
            return this;
        }

        public Builder setName(String name) {
            this.obj.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.obj.description = description;
            return this;
        }

        public Builder setAmount(long amount) {
            this.obj.amount = amount;
            return this;
        }

        public Builder setPaidBy(FundUser paidBy) {
            this.obj.paidBy = paidBy;
            return this;
        }

        public Builder setTransactions(Set<Transaction> transactions) {
            this.obj.transactions = transactions;
            return this;
        }

        public Builder setAttachments(Set<URI> attachments) {
            this.obj.attachments = attachments;
            return this;
        }

        public Builder isProcessed(boolean isProcessed) {
            this.obj.processed = isProcessed;
            return this;
        }

        public Builder isSettled(boolean isSettled) {
            this.obj.settled = isSettled;
            return this;
        }

        public Builder setCreatedOn(LocalDate createdOn) {
            this.obj.createdOn = createdOn;
            return this;
        }

        public Builder setEvent(Event event) {
            this.obj.event = event;
            return this;
        }

        /**
         * Validates the state of the object. Throws an exception if the state is invalid.
         *
         * @throws IllegalStateException if the state of {@code obj} is invalid
         */
        protected void validateObj() {
            if (this.obj.name == null) {
                throw new IllegalStateException("Field 'name' must not be null");
            }
            if (this.obj.date == null) {
                throw new IllegalStateException("Field 'date' must not be null");
            }
            if (this.obj.name == null) {
                throw new IllegalStateException("Field 'name' must not be null");
            }
            if (this.obj.description == null) {
                throw new IllegalStateException("Field 'description' must not be null");
            }
            if (this.obj.paidBy == null) {
                throw new IllegalStateException("Field 'paidBy' must not be null");
            }
            if (this.obj.createdOn == null) {
                throw new IllegalStateException("Field 'createdOn' must not be null");
            }
            if (this.obj.transactions == null) {
                throw new IllegalStateException("Field 'transactions' must not be null");
            }
            if (this.obj.attachments == null) {
                throw new IllegalStateException("Field 'attachments' must not be null");
            }

            for (Transaction t : this.obj.transactions) {
                if (!t.getDebtor().equals(this.obj.paidBy.getFund())
                        && !t.getCreditor().equals(this.obj.paidBy.getFund())) {
                    throw new IllegalStateException("Transaction '" + t.getDescription()
                            + "' does not contain the payer of this submission");
                }
            }
        }

        /**
         * Builds the encapsulated {@code Submission}.
         *
         * @return the encapsulated {@code Submission}
         * @throws IllegalStateException if the state of the encapsulated object is invalid
         */
        public Submission build() {
            /*
             * Upon creation the following must be set:
             *   - The transactions must be altered to reflect that they belong to the Submission.
             *   - For every Transaction the debtor or creditor must be equal to the paidBy field of
             *     the Submission
             */
            validateObj();

            for (Transaction t : this.obj.transactions) {
                t.setSubmission(this.obj);
            }

            Submission res = this.obj;
            this.obj = new Submission();
            return res;
        }
    }
}
