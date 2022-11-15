package ch.bolkhuis.declabo.fund;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD")
@DataJpaTest(showSql = false)
public class FundRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    public FundRepository repository;

    @Test
    public void should_find_no_funds_if_repository_is_empty() {
        Iterable<Fund> funds = repository.findAll();

        assertThat(funds).isEmpty();
    }

    @Test
    public void should_store_a_fund() {
        String name = "My Fund";
        long balance = 69L;
        Fund fund = repository.save(new DebitFund(name, balance));

        assertThat(fund).hasFieldOrPropertyWithValue("name", name);
        assertThat(fund).hasFieldOrPropertyWithValue("balance", balance);
    }

    @Test
    public void should_find_all_funds() {
        Fund f1 = new DebitFund("fund1", 0L);
        entityManager.persist(f1);

        Fund f2 = new CreditFund("fund2", 1L);
        entityManager.persist(f2);

        Iterable<Fund> funds = repository.findAll();
        assertThat(funds).hasSize(2).contains(f1, f2);
    }

    @Test
    public void should_reject_save_on_duplicate_name() {
        String name = "fundname";
        Fund f1 = repository.saveAndFlush(new DebitFund(name, 0L));
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(new CreditFund(name, 2L)));
    }

}
