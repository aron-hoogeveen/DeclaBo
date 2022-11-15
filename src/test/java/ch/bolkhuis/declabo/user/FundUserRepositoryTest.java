package ch.bolkhuis.declabo.user;

import ch.bolkhuis.declabo.fund.CreditFund;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("PMD")
@DataJpaTest
public class FundUserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    FundUserRepository repository;

    @Test
    public void should_find_no_users_if_repo_is_empty() {
        Iterable<FundUser> users = repository.findAll();

        assertThat(users).isEmpty();
    }

    @Test
    public void should_store_a_fund_user() {
        String email = "aron@bolkhuis.ch";
        String name = "Aron";
        String surname = "Hoogeveen";
        int room = 101;
        CreditFund fund = new CreditFund(name + " " + surname, 0L);
        FundUser user = repository.save(new FundUser(email, name, surname, room, fund));

        assertThat(user).hasFieldOrPropertyWithValue("email", email);
        assertThat(user).hasFieldOrPropertyWithValue("name", name);
        assertThat(user).hasFieldOrPropertyWithValue("surname", surname);
        assertThat(user).hasFieldOrPropertyWithValue("room", room);
        assertThat(user).hasFieldOrPropertyWithValue("fund", fund); // should have been
                                                                          // eagerly fetched
    }

    @Test
    public void should_increase_balance_if_updated_through_fund_user_or_through_fund() {
        String email = "aron@bolkhuis.ch";
        String name = "Aron";
        String surname = "Hoogeveen";
        int room = 101;
        CreditFund fund = new CreditFund(name + " " + surname, 0L);
        FundUser user = repository.save(new FundUser(email, name, surname, room, fund));

        assertThat(user.getFund().getBalance()).isEqualTo(0L);

        user.getFund().credit(25L);
        user = repository.save(user);

        assertThat(user.getFund().getBalance()).isEqualTo(25L);

        user.getFund().credit(25L);
        entityManager.persist(user.getFund());

        Optional<FundUser> userOptional = repository.findById(user.getId());
        assertTrue(userOptional.isPresent());
        assertThat(userOptional.get().getFund().getBalance()).isEqualTo(50L);
    }

}
