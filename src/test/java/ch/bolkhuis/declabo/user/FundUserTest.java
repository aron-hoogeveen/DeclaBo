package ch.bolkhuis.declabo.user;

import ch.bolkhuis.declabo.fund.CreditFund;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
public class FundUserTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String email = "aron@bolkhuis.ch";
        String name = "Aron";
        String surname = "Hoogeveen";
        int room = 101;
        CreditFund fund = new CreditFund("Aron Hoogeveen", 0L);
        FundUser user = new FundUser(email, name, surname, null, room, fund);

        assertThat(user).hasFieldOrPropertyWithValue("email", email);
        assertThat(user).hasFieldOrPropertyWithValue("name", name);
        assertThat(user).hasFieldOrPropertyWithValue("surname", surname);
        assertThat(user).hasFieldOrPropertyWithValue("room", room);
        assertThat(user).hasFieldOrPropertyWithValue("fund", fund);
    }

    public static FundUser getTestCreditFundUser() {
        CreditFund fund = new CreditFund("John Smith", 420L);
        return new FundUser("john@bolkhuis.ch", "John", "Smith", null, 101, fund);
    }

}
