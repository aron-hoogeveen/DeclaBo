package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.fund.Fund;
import ch.bolkhuis.declabo.submission.Submission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A Transaction that might also have a related Submission.
 */
public class Transaction extends BaseTransaction {

    private @Nullable Submission submission;

    /**
     * Constructs a new BaseTransaction.
     *
     * @param id          the id
     * @param debtor      the debtor fund
     * @param creditor    the creditor fund
     * @param amount      the amount
     * @param date        the date
     * @param description the description
     * @throws NullPointerException if {@code debtor}, {@code creditor}, {@code date}, or
     *                              {@code description} is null
     */
    public Transaction(
            @Nullable Long id,
            @NotNull Fund debtor,
            @NotNull Fund creditor,
            long amount,
            @NotNull LocalDate date,
            @NotNull String description,
            @Nullable Submission submission
    ) {
        super(id, debtor, creditor, amount, date, description);

        this.submission = submission;
    }

    public @Nullable Submission getSubmission() {
        return submission;
    }

    public void setSubmission(@Nullable Submission submission) {
        this.submission = submission;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        return (o instanceof Transaction);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
