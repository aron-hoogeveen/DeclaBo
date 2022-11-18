package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.event.EventTest;
import ch.bolkhuis.declabo.user.FundUser;
import ch.bolkhuis.declabo.user.FundUserTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
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
        FundUser paidBy = FundUserTest.getTestCreditFundUser();
        Event event = EventTest.getTestEvent();
        String name = "name of submission";
        String remarks = "any remarks";
        Status status = Status.BEING_CREATED;
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, status);

        submission.setPaidBy(entityManager.persist(submission.getPaidBy()));
        submission.setEvent(entityManager.persist(submission.getEvent()));
        submission = entityManager.persist(submission);

        assertThat(submission).hasFieldOrPropertyWithValue("createdOn", createdOn);
        assertThat(submission).hasFieldOrPropertyWithValue("date", date);
        assertThat(submission).hasFieldOrPropertyWithValue("paidBy", paidBy);
        assertThat(submission).hasFieldOrPropertyWithValue("event", event);
        assertThat(submission).hasFieldOrPropertyWithValue("name", name);
        assertThat(submission).hasFieldOrPropertyWithValue("remarks", remarks);
        assertThat(submission).hasFieldOrPropertyWithValue("status", status);
    }

}
