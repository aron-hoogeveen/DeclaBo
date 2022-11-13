package ch.bolkhuis.declabo.fund;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
public class CreditFundTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String name = "name";
        long balance = 2L;
        CreditFund fund = new CreditFund(name, balance);

        assertThat(fund).hasFieldOrPropertyWithValue("name", name);
        assertThat(fund).hasFieldOrPropertyWithValue("balance", balance);
    }

    @Test
    public void should_decrease_balance_when_debited_and_increase_when_credited() {
        CreditFund fund = new CreditFund("name", 0L);
        assertThat(fund.getBalance()).isEqualTo(0L);

        fund.debit(2L);
        assertThat(fund.getBalance()).isEqualTo(-2L);

        fund.credit(2L);
        assertThat(fund.getBalance()).isEqualTo(0L);
    }

}
