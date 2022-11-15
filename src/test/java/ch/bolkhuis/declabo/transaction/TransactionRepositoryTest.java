package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.fund.CreditFund;
import ch.bolkhuis.declabo.fund.DebitFund;
import ch.bolkhuis.declabo.fund.Fund;
import ch.bolkhuis.declabo.submission.Submission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TransactionRepository repository;

    @Test
    public void should_find_no_transactions_if_repo_is_empty() {
        Iterable<Transaction> transactions = repository.findAll();

        assertThat(transactions).isEmpty();
    }

    @Test
    public void should_store_a_transaction() {
        Fund debtor = new CreditFund("credit fund", 1L);
        Fund creditor = new DebitFund("debit fund", 2L);
        long amount = 555L;
        LocalDate date = LocalDate.now();
        String description = "this is description :)";
        Submission submission = null;
        Event event = null;
        boolean settled = false;

        debtor = entityManager.persist(debtor);
        creditor = entityManager.persist(creditor);

        Transaction transaction = repository.save(
                new Transaction(debtor, creditor, amount, date, description, submission, event, settled)
        );

        assertThat(transaction).hasFieldOrPropertyWithValue("debtor", debtor);
        assertThat(transaction).hasFieldOrPropertyWithValue("creditor", creditor);
        assertThat(transaction).hasFieldOrPropertyWithValue("amount", amount);
        assertThat(transaction).hasFieldOrPropertyWithValue("date", date);
        assertThat(transaction).hasFieldOrPropertyWithValue("description", description);
        assertThat(transaction).hasFieldOrPropertyWithValue("submission", submission);
        assertThat(transaction).hasFieldOrPropertyWithValue("event", event);
        assertThat(transaction).hasFieldOrPropertyWithValue("settled", settled);
    }

}
