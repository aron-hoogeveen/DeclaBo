package ch.bolkhuis.declabo.old.transaction;

import ch.bolkhuis.declabo.old.fund.CreditFund;
import ch.bolkhuis.declabo.old.fund.DebitFund;
import ch.bolkhuis.declabo.old.fund.Fund;
import ch.bolkhuis.declabo.old.submission.Submission;
import ch.bolkhuis.declabo.old.submission.SubmissionBuilderTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TransactionTest {

    private static final transient long DEBTOR_BALANCE = 40L;
    private static final transient long CREDITOR_BALANCE = 40L;
    private static final transient Long ID = 0L;
    private static final transient Fund DEBTOR = new DebitFund(0L, "Debtor", DEBTOR_BALANCE);
    private static final transient Fund CREDITOR = new CreditFund(1L, "Creditor", CREDITOR_BALANCE);
    private static final transient long AMOUNT = 5500L;
    private static final transient LocalDate DATE = LocalDate.of(2022, 1, 12);
    private static final transient String DESCRIPTION = "Makrorun voor koffie";

    @Test
    public void test_constructor() {
        new Transaction(ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION, null);
    }

    @Test
    public void test_gettersAndSetters() {
        Submission submission = SubmissionBuilderTest.getMinimalSubmission();
        Transaction transaction =
                new Transaction(ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION, submission);
        Assertions.assertEquals(submission, transaction.getSubmission());
        transaction.setSubmission(null);
        Assertions.assertNull(transaction.getSubmission());
    }

    @Test
    public void test_equals() {
        Submission submission = SubmissionBuilderTest.getMinimalSubmission();
        Transaction transaction1 =
                new Transaction(ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION, null);
        Transaction transaction2 =
                new Transaction(ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION, submission);
        Assertions.assertEquals(transaction1, transaction2);
    }
}
