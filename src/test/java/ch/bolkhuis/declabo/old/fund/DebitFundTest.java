package ch.bolkhuis.declabo.old.fund;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DebitFundTest {

    private static final transient long ID = 1L;
    private static final transient String NAME = "Hendrik";
    private static final transient long BALANCE = 3600L;

    @Test
    public void test_constructor() {
        new DebitFund(ID, NAME, BALANCE);
        Assertions.assertThrows(NullPointerException.class, () ->
                new DebitFund(ID, null, BALANCE)
        );
    }

    @Test
    public void test_ifDebited_thenBalanceIncreases() {
        long balance = 8349738383L;
        long amount = 733L;
        long expected = balance + amount;
        DebitFund fund = new DebitFund(ID, NAME, balance);
        Assertions.assertEquals(expected, fund.debit(amount));
        Assertions.assertEquals(expected, fund.getBalance());
    }

    @Test
    public void test_ifCredited_thenBalanceDecreases() {
        long balance = 83947L;
        long amount = 3333L;
        long expected = balance - amount;
        DebitFund fund = new DebitFund(ID, NAME, balance);
        Assertions.assertEquals(expected, fund.credit(amount));
        Assertions.assertEquals(expected, fund.getBalance());
    }

    @Test
    public void test_ifBalanceOverflowsOrUnderflows_thenDebitAndCreditThrow() {
        long maxBalance = Long.MAX_VALUE;
        DebitFund maxFund = new DebitFund(ID, NAME, maxBalance);
        Assertions.assertThrows(ArithmeticException.class, () ->
                maxFund.debit(1)
        );
        maxFund.credit(1);

        long minBalance = Long.MIN_VALUE;
        DebitFund minFund = new DebitFund(ID, NAME, minBalance);
        Assertions.assertThrows(ArithmeticException.class, () ->
                minFund.credit(1)
        );
        minFund.debit(1);

        DebitFund df1 = new DebitFund(ID, NAME, Long.MAX_VALUE);
        Assertions.assertEquals(0L, df1.credit(Long.MAX_VALUE));
        DebitFund df2 = new DebitFund(ID, NAME, Long.MIN_VALUE);
        Assertions.assertEquals(-1L, df2.debit(Long.MAX_VALUE));
    }
}
