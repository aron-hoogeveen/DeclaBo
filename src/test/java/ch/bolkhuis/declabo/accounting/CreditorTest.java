package ch.bolkhuis.declabo.accounting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditorTest {

    private static final transient String LEGAL_NAME = "Hendrik";

    @Test
    public void test_whenDebited_thenBalanceDecreases() {
        long initialBalance = 8348947943L;
        long amount = 234142134213L;
        long newBalance = initialBalance - amount;
        Creditor creditor = new Creditor(LEGAL_NAME, initialBalance);
        Assertions.assertEquals(newBalance, creditor.debit(amount));
        Assertions.assertEquals(newBalance, creditor.getBalance());
    }

    @Test
    public void test_whenCredited_thenBalanceIncreses() {
        long initialBalance = 12390543849L;
        long amount = 34342L;
        long newBalance = initialBalance + amount;
        Creditor creditor = new Creditor(LEGAL_NAME, initialBalance);
        Assertions.assertEquals(newBalance, creditor.credit(amount));
        Assertions.assertEquals(newBalance, creditor.getBalance());
    }
}
