package ch.bolkhuis.declabo.accounting;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractAccountingEntityTest {

    @Test
    public void testConstructor_whenEmtpyName_thenThrows() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new MyAccountingEntity(null, 0)
        );
        String blankName = "   ";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new MyAccountingEntity(blankName, 0)
        );
        Assertions.assertFalse(MyAccountingEntity.isValidName(blankName));
    }

    @Test
    public void test_whenConstructed_thenFieldsAreSet() {
        String name = "Hendrik";
        int balance = 102;
        MyAccountingEntity accountingEntity = new MyAccountingEntity(name, balance);
        Assertions.assertEquals(name, accountingEntity.getName());
        Assertions.assertEquals(balance, accountingEntity.getBalance());
    }

    @Test
    public void test_whenBalanceIsChanged_thenChangeIsPersisted() {
        long balance = 1234L;
        MyAccountingEntity accountingEntity = new MyAccountingEntity("Hendrik", balance);
        Assertions.assertEquals(balance, accountingEntity.getBalance());
        long newBalance = 666L;
        accountingEntity.setBalance(newBalance);
        Assertions.assertEquals(newBalance, accountingEntity.getBalance());
    }

    private static class MyAccountingEntity extends AbstractAccountingEntity {

        /**
         * Construct a new {@code AbstractAccountingEntity}. Set the initial balance.
         *
         * @param name    the name
         * @param balance the initial balance
         * @throws NullPointerException if {@code name} is null
         */
        MyAccountingEntity(@NotNull String name, long balance) {
            super(name, balance);
        }

        @Override
        public long debit(long amount) {
            return 0;
        }

        @Override
        public long credit(long amount) {
            return 0;
        }
    }
}
