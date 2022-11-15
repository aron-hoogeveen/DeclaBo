package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.fund.CreditFund;
import ch.bolkhuis.declabo.fund.DebitFund;
import ch.bolkhuis.declabo.fund.Fund;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    public void should_set_all_fields_on_construction() {
        Fund debtor = new CreditFund("my credit fund", 0L);
        Fund creditor = new DebitFund("my debit fund", 0L);
        long amount = 500L;
        LocalDate date = LocalDate.now();
        String description = "my description";
        boolean settled = false;
        Transaction transaction = new Transaction(debtor, creditor, amount, date, description, settled);

        assertThat(transaction).hasFieldOrPropertyWithValue("debtor", debtor);
        assertThat(transaction).hasFieldOrPropertyWithValue("creditor", creditor);
        assertThat(transaction).hasFieldOrPropertyWithValue("amount", amount);
        assertThat(transaction).hasFieldOrPropertyWithValue("date", date);
        assertThat(transaction).hasFieldOrPropertyWithValue("description", description);
        assertThat(transaction).hasFieldOrPropertyWithValue("settled", settled);
    }

    @Test
    public void should_reject_zero_and_negative_amount() {
        Fund debtor = new CreditFund("my credit fund", 0L);
        Fund creditor = new DebitFund("my debit fund", 0L);
        LocalDate date = LocalDate.now();
        String description = "my description";
        boolean settled = false;

        assertThrows(IllegalArgumentException.class, () -> new Transaction(debtor, creditor, 0L, date, description, settled));
        assertThrows(IllegalArgumentException.class, () -> new Transaction(debtor, creditor, -500L, date, description, settled));
    }

    @Test
    public void should_reject_modifications_if_settled() {
        Fund debtor = new CreditFund("my credit fund", 0L);
        Fund creditor = new DebitFund("my debit fund", 0L);
        long amount = 500L;
        LocalDate date = LocalDate.now();
        String description = "my description";
        boolean settled = true;
        Transaction transaction = new Transaction(debtor, creditor, amount, date, description, settled);

        assertThrows(IllegalStateException.class, () -> transaction.setDebtor(debtor));
        assertThrows(IllegalStateException.class, () -> transaction.setCreditor(creditor));
        assertThrows(IllegalStateException.class, () -> transaction.setAmount(amount));
        assertThrows(IllegalStateException.class, () -> transaction.setDate(date));
        assertThrows(IllegalStateException.class, () -> transaction.setDescription(description));
        assertDoesNotThrow(() -> transaction.setSettled(false));
    }

}
