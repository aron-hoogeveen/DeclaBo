package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.user.FundUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubmissionTest {

    @Test
    public void should_set_all_fields_on_construction() {
        LocalDate createdOn = LocalDate.now();
        LocalDate date = LocalDate.now();
        FundUser payedBy = FundUser.getTestUser();
        Event event = Event.getTestEvent();
        String name = "My submission";
        String remarks = "Not very remarkable...";
        boolean processed = false;
        boolean settled = false;

        Submission submission = new Submission(createdOn, date, payedBy, event, name, remarks, processed, settled);
        assertThat(submission).hasFieldOrPropertyWithValue("createdOn", createdOn);
        assertThat(submission).hasFieldOrPropertyWithValue("date", date);
        assertThat(submission).hasFieldOrPropertyWithValue("payedBy", payedBy);
        assertThat(submission).hasFieldOrPropertyWithValue("event", event);
        assertThat(submission).hasFieldOrPropertyWithValue("name", name);
        assertThat(submission).hasFieldOrPropertyWithValue("remarks", remarks);
        assertThat(submission).hasFieldOrPropertyWithValue("processed", processed);
        assertThat(submission).hasFieldOrPropertyWithValue("settled", settled);
    }

    @Test
    public void should_not_be_settled_when_not_processed() {
        Submission submission = Submission.getTestSubmission();
        submission.setProcessed(false);
        assertThrows(IllegalStateException.class, () -> submission.setSettled(true));
    }

    @Test
    public void should_reject_processed_state_change_if_settled() {
        Submission submission = Submission.getTestSubmission();
        submission.setProcessed(true);
        submission.setSettled(true);
        assertThrows(IllegalStateException.class, () -> submission.setProcessed(false));
        assertThrows(IllegalStateException.class, () -> submission.setProcessed(true));
    }

    @Test
    public void should_reject_field_changes_when_settled() {
        Submission submission = Submission.getTestSubmission();
        submission.setProcessed(true);
        submission.setSettled(true);
        assertThrows(IllegalStateException.class, () -> submission.setId(1L));
        assertThrows(IllegalStateException.class, () -> submission.setCreatedOn(LocalDate.now()));
        assertThrows(IllegalStateException.class, () -> submission.setDate(LocalDate.now()));
        assertThrows(IllegalStateException.class, () -> submission.setPayedBy(FundUser.getTestUser()));
        assertThrows(IllegalStateException.class, () -> submission.setEvent(Event.getTestEvent()));
        assertThrows(IllegalStateException.class, () -> submission.setName("new name"));
        assertThrows(IllegalStateException.class, () -> submission.setRemarks("new remark"));
        assertThrows(IllegalStateException.class, () -> submission.setProcessed(false));
        assertDoesNotThrow(() -> submission.setSettled(false)); // it is allowed to change its state back
    }

}
