package ch.bolkhuis.declabo.old.user;

import ch.bolkhuis.declabo.old.fund.CreditFund;
import ch.bolkhuis.declabo.old.fund.Fund;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FundUserTest {

    private static final transient long ID = 1L;
    private static final transient String EMAIL = "hendrik@bolkhuis.ch";
    private static final transient String NAME = "Hendrik";
    private static final transient String SURNAME = "Troelala";
    private static final transient String NICKNAME = null;
    public static final transient Fund FUND = new CreditFund(0L, "HendrikFund", 0L);

    @Test
    public void test_constructor() {
        new FundUser(ID, EMAIL, NAME, SURNAME, NICKNAME, FUND);
        Assertions.assertThrows(NullPointerException.class, () ->
                new FundUser(ID, EMAIL, NAME, SURNAME, NICKNAME, null)
        );
    }

    @Test
    public void test_getters() {
        FundUser fundUser = new FundUser(ID, EMAIL, NAME, SURNAME, NICKNAME, FUND);
        Assertions.assertEquals(FUND, fundUser.getFund());
    }

    @Test
    public void test_equals() {
        FundUser fundUser1 = new FundUser(ID, EMAIL, NAME, SURNAME, NICKNAME, FUND);
        FundUser fundUser2 = new FundUser(ID, EMAIL, NAME, SURNAME, NICKNAME, FUND);
        FundUser fundUser3 = new FundUser(ID, EMAIL, NAME, SURNAME, "different nickname", FUND);

        Assertions.assertEquals(fundUser1, fundUser2);
        Assertions.assertNotEquals(fundUser1, fundUser3);
    }
}
