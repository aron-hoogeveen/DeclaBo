package ch.bolkhuis.declabo.fund;

import ch.bolkhuis.declabo.event.Event;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
public class DebitEventFundTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String name = "name";
        long balance = 2L;
        Event event = new Event(2L);
        DebitEventFund fund = new DebitEventFund(name, balance, event);

        assertThat(fund).hasFieldOrPropertyWithValue("name", name);
        assertThat(fund).hasFieldOrPropertyWithValue("balance", balance);
        assertThat(fund).hasFieldOrPropertyWithValue("event", event);
    }

    @Test
    public void should_increase_balance_when_debited_and_decrease_when_credited() {
        DebitEventFund fund = new DebitEventFund("name", 0L, new Event(2L));
        assertThat(fund.getBalance()).isEqualTo(0L);

        fund.debit(2L);
        assertThat(fund.getBalance()).isEqualTo(2L);

        fund.credit(2L);
        assertThat(fund.getBalance()).isEqualTo(0L);
    }

}
