package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.fund.CreditFund;
import ch.bolkhuis.declabo.fund.DebitFund;
import ch.bolkhuis.declabo.fund.Fund;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    public void should_set_all_fields_on_construction() {
        Fund debtor = new CreditFund("my credit fund", 0L);
        Fund creditor = new DebitFund("my debit fund", 0L);
        long amount = 500L;
        LocalDate date = LocalDate.now();
        String description = "my description";
        Transaction transaction = new Transaction(debtor, creditor, amount, date, description);

        assertThat(transaction).hasFieldOrPropertyWithValue("debtor", debtor);
        assertThat(transaction).hasFieldOrPropertyWithValue("creditor", creditor);
        assertThat(transaction).hasFieldOrPropertyWithValue("amount", amount);
        assertThat(transaction).hasFieldOrPropertyWithValue("date", date);
        assertThat(transaction).hasFieldOrPropertyWithValue("description", description);
    }

    @Test
    public void should_reject_zero_and_negative_amount() {
        Fund debtor = new CreditFund("my credit fund", 0L);
        Fund creditor = new DebitFund("my debit fund", 0L);
        LocalDate date = LocalDate.now();
        String description = "my description";

        assertThrows(IllegalArgumentException.class, () -> new Transaction(debtor, creditor, 0L, date, description));
        assertThrows(IllegalArgumentException.class, () -> new Transaction(debtor, creditor, -500L, date, description));
    }

}
