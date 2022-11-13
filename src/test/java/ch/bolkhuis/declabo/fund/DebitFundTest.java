package ch.bolkhuis.declabo.fund;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
public class DebitFundTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String name = "name";
        long balance = 2L;
        DebitFund fund = new DebitFund(name, balance);

        assertThat(fund).hasFieldOrPropertyWithValue("name", name);
        assertThat(fund).hasFieldOrPropertyWithValue("balance", balance);
    }

    @Test
    public void should_increase_balance_when_debited_and_decrease_when_credited() {
        DebitFund fund = new DebitFund("name", 0L);
        assertThat(fund.getBalance()).isEqualTo(0L);

        fund.debit(2L);
        assertThat(fund.getBalance()).isEqualTo(2L);

        fund.credit(2L);
        assertThat(fund.getBalance()).isEqualTo(0L);
    }

}
