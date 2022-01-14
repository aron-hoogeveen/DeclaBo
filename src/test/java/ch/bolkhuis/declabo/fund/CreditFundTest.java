package ch.bolkhuis.declabo.fund;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditFundTest {

    private static final transient long ID = 1L;
    private static final transient String NAME = "Hendrik";
    private static final transient long BALANCE = 3600L;

    @Test
    public void test_constructor() {
        new CreditFund(ID, NAME, BALANCE);
        Assertions.assertThrows(NullPointerException.class, () ->
                new CreditFund(ID, null, BALANCE)
        );
    }

    @Test
    public void test_ifCredited_thenBalanceIncreases() {
        long balance = 8349738383L;
        long amount = 733L;
        long expected = balance + amount;
        CreditFund fund = new CreditFund(ID, NAME, balance);
        Assertions.assertEquals(expected, fund.credit(amount));
        Assertions.assertEquals(expected, fund.getBalance());
    }

    @Test
    public void test_ifDebited_thenBalanceDecreases() {
        long balance = 83947L;
        long amount = 3333L;
        long expected = balance - amount;
        CreditFund fund = new CreditFund(ID, NAME, balance);
        Assertions.assertEquals(expected, fund.debit(amount));
        Assertions.assertEquals(expected, fund.getBalance());
    }

    @Test
    public void test_ifBalanceOverflowsOrUnderflows_thenDebitAndCreditThrow() {
        long maxBalance = Long.MAX_VALUE;
        CreditFund maxFund = new CreditFund(ID, NAME, maxBalance);
        Assertions.assertThrows(ArithmeticException.class, () ->
                maxFund.credit(1)
        );
        maxFund.debit(1);

        long minBalance = Long.MIN_VALUE;
        CreditFund minFund = new CreditFund(ID, NAME, minBalance);
        Assertions.assertThrows(ArithmeticException.class, () ->
                minFund.debit(1)
        );
        minFund.credit(1);

        CreditFund df1 = new CreditFund(ID, NAME, Long.MAX_VALUE);
        Assertions.assertEquals(0L, df1.debit(Long.MAX_VALUE));
        CreditFund df2 = new CreditFund(ID, NAME, Long.MIN_VALUE);
        Assertions.assertEquals(-1L, df2.credit(Long.MAX_VALUE));
    }
}
