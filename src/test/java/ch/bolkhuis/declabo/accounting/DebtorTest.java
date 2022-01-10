package ch.bolkhuis.declabo.accounting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DebtorTest {

    private static final transient String LEGAL_NAME = "Hendrik";

    @Test
    public void test_whenDebited_thenBalanceIncreases() {
        long initialBalance = 3600L;
        long amount = 40L;
        long newBalance = initialBalance + amount;
        Debtor debtor = new Debtor(LEGAL_NAME, initialBalance);
        Assertions.assertEquals(newBalance, debtor.debit(amount));
        Assertions.assertEquals(newBalance, debtor.getBalance());
    }

    @Test
    public void test_whenCredited_thenBalanceDecreases() {
        long initialBalance = 42069L;
        long amount = 36L;
        long newBalance = initialBalance - amount;
        Debtor debtor = new Debtor(LEGAL_NAME, initialBalance);
        Assertions.assertEquals(newBalance, debtor.credit(amount));
        Assertions.assertEquals(newBalance, debtor.getBalance());
    }
}
