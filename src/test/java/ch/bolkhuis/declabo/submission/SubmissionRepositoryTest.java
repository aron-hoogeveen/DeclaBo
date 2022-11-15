package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.user.FundUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SubmissionRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SubmissionRepository repository;

    @Test
    public void should_find_no_submissions_if_repository_is_empty() {
        Iterable<Submission> submissions = repository.findAll();

        assertThat(submissions).isEmpty();
    }

    @Test
    public void should_store_a_submission() {
        LocalDate createdOn = LocalDate.now();
        LocalDate date = LocalDate.now();
        FundUser payedBy = FundUser.getTestUser();
        Event event = Event.getTestEvent();
        String name = "name of submission";
        String remarks = "any remarks";
        boolean processed = false;
        boolean settled = false;
        Submission submission = new Submission(createdOn, date, payedBy, event, name, remarks, processed, settled);

        submission.setPayedBy(entityManager.persist(submission.getPayedBy()));
        submission.setEvent(entityManager.persist(submission.getEvent()));
        submission = entityManager.persist(submission);

        assertThat(submission).hasFieldOrPropertyWithValue("createdOn", createdOn);
        assertThat(submission).hasFieldOrPropertyWithValue("date", date);
        assertThat(submission).hasFieldOrPropertyWithValue("payedBy", payedBy);
        assertThat(submission).hasFieldOrPropertyWithValue("event", event);
        assertThat(submission).hasFieldOrPropertyWithValue("name", name);
        assertThat(submission).hasFieldOrPropertyWithValue("remarks", remarks);
        assertThat(submission).hasFieldOrPropertyWithValue("processed", processed);
        assertThat(submission).hasFieldOrPropertyWithValue("settled", settled);
    }

}
