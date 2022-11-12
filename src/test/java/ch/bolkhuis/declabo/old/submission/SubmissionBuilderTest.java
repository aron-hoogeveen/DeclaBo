package ch.bolkhuis.declabo.old.submission;

import ch.bolkhuis.declabo.old.fund.CreditFund;
import ch.bolkhuis.declabo.old.fund.DebitFund;
import ch.bolkhuis.declabo.old.transaction.Transaction;
import ch.bolkhuis.declabo.old.user.FundUser;
import ch.bolkhuis.declabo.old.user.FundUserTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SubmissionBuilderTest {

    private static final Long ID = 2L;
    private static final String NAME = "Submission";
    private static final long AMOUNT = 5500;
    private static final LocalDate DATE = LocalDate.of(2022, 3, 1);
    private static final String DESCRIPTION = "Description";
    private static final LocalDate CREATED_ON = LocalDate.of(2022, 1, 15);
    private static final FundUser PAID_BY = new FundUser(
            0L, "Hendrik", "Surname", "hendrik@bolkhuis.ch", null, FundUserTest.FUND);

    @Test
    public void test_buildMinimalSubmission_doesNotFail() {
        Submission.Builder builder = Submission.builder();
        builder
                .setName(NAME)
                .setAmount(AMOUNT)
                .setDate(DATE)
                .setDescription(DESCRIPTION)
                .setCreatedOn(CREATED_ON)
                .setPaidBy(PAID_BY)
                .isSettled(false)
                .isProcessed(false)
                .build();
    }

    @Test
    public void test_ifMandatoryFieldsAreMissing_thenBuildFails() {
        Submission.Builder builder = Submission.builder();
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setName(NAME);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setDate(DATE);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setDescription(DESCRIPTION);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setPaidBy(PAID_BY);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
        builder.setCreatedOn(CREATED_ON);
        builder.build();
    }

    @Test
    public void test_ifTransactionsAreAvailable_thenFieldSubmissionOfTransactionIsSet() {
        Submission.Builder builder = Submission.builder(getMinimalSubmission());
        Set<Transaction> transactions = Set.of(
                new Transaction(0L, PAID_BY.getFund(), new CreditFund(2L, "credit1", 0L),
                        4500L, LocalDate.of(2022, 3, 2), "Trans1", null),
                new Transaction(1L, new CreditFund(3L, "credit1", 0L), PAID_BY.getFund(),
                        1500L, LocalDate.of(2022, 3, 2), "Trans1", null)
        );
        builder.setTransactions(transactions);
        Submission submission = builder.build();
        Assertions.assertEquals(submission.getTransactions(), transactions);
        for (Transaction t : submission.getTransactions()) {
            Assertions.assertEquals(submission, t.getSubmission());
        }
    }

    @Test
    public void test_ifTransactionWithoutPaidByIsAdded_thenSubmissionIsInvalid() {
        Submission.Builder builder = Submission.builder(getMinimalSubmission());
        Set<Transaction> transactions = Set.of(
                new Transaction(0L, PAID_BY.getFund(), new CreditFund(2L, "credit1", 0L),
                        4500L, LocalDate.of(2022, 3, 2), "Trans1", null),
                new Transaction(1L, new CreditFund(3L, "credit1", 0L),
                        new DebitFund(4L, "debit1", 0L),
                        1500L, LocalDate.of(2022, 3, 2), "Trans1", null)
        );
        builder.setTransactions(transactions);
        Assertions.assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    public void test_ifBuilderIsReused_thenTheSubmissionIsReset() {
        Submission.Builder builder = Submission.builder(getMinimalSubmission());
        builder.build();
        Assertions.assertThrows(IllegalStateException.class, builder::build);
    }

    /**
     * Creates a new Submission with minimal information. This method is intended for making testing
     * in other classes easier, by providing a default minimal Submission.
     *
     * @return a submission
     */
    public static Submission getMinimalSubmission() {
        return Submission.builder()
                .setName(NAME)
                .setAmount(AMOUNT)
                .setDate(DATE)
                .setDescription(DESCRIPTION)
                .setCreatedOn(CREATED_ON)
                .setPaidBy(PAID_BY)
                .isSettled(false)
                .isProcessed(false)
                .build();
    }
}
