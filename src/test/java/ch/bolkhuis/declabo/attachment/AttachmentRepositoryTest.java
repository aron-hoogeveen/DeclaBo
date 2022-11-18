package ch.bolkhuis.declabo.attachment;

import ch.bolkhuis.declabo.submission.Submission;
import ch.bolkhuis.declabo.submission.SubmissionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
@DataJpaTest
public class AttachmentRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AttachmentRepository repository;

    @Test
    public void should_find_no_attachments_if_repository_is_empty() {
        Iterable<Attachment> attachments = repository.findAll();

        assertThat(attachments).isEmpty();
    }

    @Test
    public void should_store_an_attachment() {
        LocalDate date = LocalDate.now();
        String path = "/home/path/to/file.jpg";
        String notes = "some notes";

        Submission submission = SubmissionTest.getTestSubmission_beingCreated(); // FIXME remove coupling between test classes and object classes
        submission.setPaidBy(entityManager.persist(submission.getPaidBy()));
        submission.setEvent(entityManager.persist(submission.getEvent()));
        submission = entityManager.persist(submission);

        Attachment attachment = repository.saveAndFlush(new Attachment(date, path, notes, submission));

        assertThat(attachment).hasFieldOrPropertyWithValue("uploadedOn", date);
        assertThat(attachment).hasFieldOrPropertyWithValue("path", path);
        assertThat(attachment).hasFieldOrPropertyWithValue("notes", notes);
    }

    @Test
    public void should_find_all_attachments() {
        LocalDate date = LocalDate.now();
        String notes = "some notes";

        Submission submission = SubmissionTest.getTestSubmission_beingCreated();
        submission.setPaidBy(entityManager.persist(submission.getPaidBy()));
        submission.setEvent(entityManager.persist(submission.getEvent()));
        submission = entityManager.persist(submission);

        Attachment a1 = new Attachment(date, "path/a1.pdf", notes, submission);
        Attachment a2 = new Attachment(date, "path/a2.jpg", notes, submission);

        repository.saveAllAndFlush(List.of(a1, a2));

        Iterable<Attachment> attachments = repository.findAll();

        assertThat(attachments).hasSize(2).contains(a1, a2);
    }

}
