package ch.bolkhuis.declabo.old.fund;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractFundTest {

    private static final transient long ID = 1L;
    private static final transient long BALANCE = 3600L;
    private static final transient String NAME = "Hendrik";

    @Test
    public void test_getters() {
        AbstractFund fund = new MyFund(ID, NAME, BALANCE);
        Assertions.assertEquals(ID, fund.getId());
        Assertions.assertEquals(NAME, fund.getName());
        Assertions.assertEquals(BALANCE, fund.getBalance());
    }

    private static class MyFund extends AbstractFund {

        public MyFund(long id, @NotNull String name, long balance) {
            super(id, name, balance);
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
