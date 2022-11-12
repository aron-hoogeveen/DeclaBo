package ch.bolkhuis.declabo.old.transaction;

import ch.bolkhuis.declabo.old.fund.CreditFund;
import ch.bolkhuis.declabo.old.fund.DebitFund;
import ch.bolkhuis.declabo.old.fund.Fund;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BaseTransactionTest {

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
        new BaseTransaction(ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION);
        new BaseTransaction(null, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION);
        Assertions.assertThrows(NullPointerException.class, () ->
                new BaseTransaction(ID, null, CREDITOR, AMOUNT, DATE, DESCRIPTION)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new BaseTransaction(ID, DEBTOR, null, AMOUNT, DATE, DESCRIPTION)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new BaseTransaction(ID, DEBTOR, CREDITOR, AMOUNT, null, DESCRIPTION)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new BaseTransaction(ID, DEBTOR, CREDITOR, AMOUNT, DATE, null)
        );
    }

    @Test
    public void test_getters() {
        BaseTransaction baseTransaction = new BaseTransaction(
                ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION);
        Assertions.assertEquals(ID, baseTransaction.getId());
        Assertions.assertEquals(DEBTOR, baseTransaction.getDebtor());
        Assertions.assertEquals(CREDITOR, baseTransaction.getCreditor());
        Assertions.assertEquals(AMOUNT, baseTransaction.getAmount());
        Assertions.assertEquals(DATE, baseTransaction.getDate());
        Assertions.assertEquals(DESCRIPTION, baseTransaction.getDescription());
    }

    @Test
    public void test_equals() {
        BaseTransaction baseTransaction1 = new BaseTransaction(
                ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION);
        BaseTransaction baseTransaction2 = new BaseTransaction(
                ID, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION);
        BaseTransaction baseTransaction3 = new BaseTransaction(
                null, DEBTOR, CREDITOR, AMOUNT, DATE, DESCRIPTION);
        BaseTransaction baseTransaction4 = new BaseTransaction(
                ID, DEBTOR, CREDITOR, AMOUNT, DATE, "Different description");
        BaseTransaction baseTransaction5 = new BaseTransaction(
                ID, DEBTOR, CREDITOR, AMOUNT - 1, DATE, DESCRIPTION);
        BaseTransaction baseTransaction6 = new BaseTransaction(
                ID, DEBTOR, CREDITOR, AMOUNT, LocalDate.of(1999, 1, 1), DESCRIPTION);

        Assertions.assertEquals(baseTransaction1, baseTransaction2);
        Assertions.assertEquals(baseTransaction1, baseTransaction3);
        Assertions.assertNotEquals(baseTransaction1, baseTransaction4);
        Assertions.assertNotEquals(baseTransaction1, baseTransaction5);
        Assertions.assertNotEquals(baseTransaction1, baseTransaction6);
    }
}
