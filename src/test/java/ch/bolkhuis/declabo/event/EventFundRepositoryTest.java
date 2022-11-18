package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.fund.CreditEventFund;
import ch.bolkhuis.declabo.fund.DebitEventFund;
import ch.bolkhuis.declabo.fund.EventFund;
import ch.bolkhuis.declabo.fund.EventFundRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
@DataJpaTest(showSql = false)
public class EventFundRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    public EventFundRepository repository;

    @Test
    public void should_find_no_funds_if_repository_is_empty() {
        Iterable<EventFund> funds = repository.findAll();

        assertThat(funds).isEmpty();
    }

    @Test
    public void should_store_an_eventfund() {
        String name = "Event Fund";
        long balance = 36L;
        Event event = entityManager.persist(EventTest.getTestEvent());
        EventFund fund = repository.save(new CreditEventFund(name, balance, event));

        assertThat(fund).hasFieldOrPropertyWithValue("name", name);
        assertThat(fund).hasFieldOrPropertyWithValue("balance", balance);
        assertThat(fund).hasFieldOrPropertyWithValue("event", event);
    }

    @Test
    public void should_find_all_eventfunds() {
        Event event = entityManager.persist(EventTest.getTestEvent());

        EventFund f1 = new CreditEventFund("Event Fund 1", 2L, event);
        entityManager.persist(f1);

        EventFund f2 = new DebitEventFund("Event Fund 2", 3L, event);
        entityManager.persist(f2);

        Iterable<EventFund> funds = repository.findAll();
        assertThat(funds).hasSize(2).contains(f1, f2);
    }

}
