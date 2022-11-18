package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.event.EventTest;
import ch.bolkhuis.declabo.fund.CreditFund;
import ch.bolkhuis.declabo.user.FundUser;
import ch.bolkhuis.declabo.user.FundUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD")
public class SubmissionTest {

    // default values for the default submission. See #getDefaultSubmission()
    private final LocalDate createdOn = LocalDate.of(2022, 1, 1);
    private final LocalDate date = LocalDate.of(2021, 12, 24);
    private final FundUser paidBy = new FundUser("john@bolkhuis.ch", "John", "Smith", 404,
            new CreditFund("John Smith", 0L));
    private final Event event = new Event("Default Event", LocalDate.of(2021, 12, 24), "Super Epic Event.");
    private final String name = "Default Submission";
    private final String remarks = "Default remarks";
    private final Status status = Status.BEING_CREATED;

    @Test
    public void should_set_all_fields_on_construction() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, status);
        assertThat(submission).hasFieldOrPropertyWithValue("createdOn", createdOn);
        assertThat(submission).hasFieldOrPropertyWithValue("date", date);
        assertThat(submission).hasFieldOrPropertyWithValue("paidBy", paidBy);
        assertThat(submission).hasFieldOrPropertyWithValue("event", event);
        assertThat(submission).hasFieldOrPropertyWithValue("name", name);
        assertThat(submission).hasFieldOrPropertyWithValue("remarks", remarks);
        assertThat(submission).hasFieldOrPropertyWithValue("status", status);
    }

    @Test
    public void should_allow_modifications_when_being_created() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, Status.BEING_CREATED);

        assertDoesNotThrow(() -> submission.setDate(date));
        assertDoesNotThrow(() -> submission.setDate(date));
        assertDoesNotThrow(() -> submission.setPaidBy(paidBy));
        assertDoesNotThrow(() -> submission.setEvent(event));
        assertDoesNotThrow(() -> submission.setName(name));
        assertDoesNotThrow(() -> submission.setRemarks(remarks));
    }

    @Test
    public void should_allow_modifications_when_pending_except_for_created_on() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, Status.PENDING);

        assertThrows(IllegalStateException.class, () -> submission.setCreatedOn(createdOn));
        assertDoesNotThrow(() -> submission.setDate(date));
        assertDoesNotThrow(() -> submission.setPaidBy(paidBy));
        assertDoesNotThrow(() -> submission.setEvent(event));
        assertDoesNotThrow(() -> submission.setName(name));
        assertDoesNotThrow(() -> submission.setRemarks(remarks));
    }

    @Test
    public void should_reject_modifications_when_in_progress() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, Status.IN_PROGRESS);

        assertThrows(IllegalStateException.class, () -> submission.setCreatedOn(createdOn));
        assertThrows(IllegalStateException.class, () -> submission.setDate(date));
        assertThrows(IllegalStateException.class, () -> submission.setPaidBy(paidBy));
        assertThrows(IllegalStateException.class, () -> submission.setEvent(event));
        assertThrows(IllegalStateException.class, () -> submission.setName(name));
        assertThrows(IllegalStateException.class, () -> submission.setRemarks(remarks));
    }

    @Test
    public void should_reject_modifications_when_rejected() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, Status.REJECTED);

        assertThrows(IllegalStateException.class, () -> submission.setCreatedOn(createdOn));
        assertThrows(IllegalStateException.class, () -> submission.setDate(date));
        assertThrows(IllegalStateException.class, () -> submission.setPaidBy(paidBy));
        assertThrows(IllegalStateException.class, () -> submission.setEvent(event));
        assertThrows(IllegalStateException.class, () -> submission.setName(name));
        assertThrows(IllegalStateException.class, () -> submission.setRemarks(remarks));
    }

    @Test
    public void should_reject_modifications_when_processed() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, Status.PROCESSED);

        assertThrows(IllegalStateException.class, () -> submission.setCreatedOn(createdOn));
        assertThrows(IllegalStateException.class, () -> submission.setDate(date));
        assertThrows(IllegalStateException.class, () -> submission.setPaidBy(paidBy));
        assertThrows(IllegalStateException.class, () -> submission.setEvent(event));
        assertThrows(IllegalStateException.class, () -> submission.setName(name));
        assertThrows(IllegalStateException.class, () -> submission.setRemarks(remarks));
    }

    @Test
    public void should_reject_modifications_when_settled() {
        Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, Status.SETTLED);

        assertThrows(IllegalStateException.class, () -> submission.setCreatedOn(createdOn));
        assertThrows(IllegalStateException.class, () -> submission.setDate(date));
        assertThrows(IllegalStateException.class, () -> submission.setPaidBy(paidBy));
        assertThrows(IllegalStateException.class, () -> submission.setEvent(event));
        assertThrows(IllegalStateException.class, () -> submission.setName(name));
        assertThrows(IllegalStateException.class, () -> submission.setRemarks(remarks));
    }

    /**
     * When the current state is PENDING the state is allowed to change to the following states.
     *   - PENDING
     *   - IN_PROGRESS
     *   - REJECTED
     */
    @Test
    public void should_allow_state_changes_when_pending() {
        Status[] allowedChanges = {Status.PENDING, Status.IN_PROGRESS, Status.REJECTED};

        performStatusChange(allowedChanges, Status.PENDING);
    }

    /**
     * When the current state is IN_PROGRESS the state is allowed to change to the following states.
     *   - IN_PROGRESS
     *   - REJECTED
     *   - PROCESSED
     */
    @Test
    public void should_allow_state_changes_when_in_progress() {
        Status[] allowedChanges = {Status.IN_PROGRESS, Status.REJECTED, Status.PROCESSED};

        performStatusChange(allowedChanges, Status.IN_PROGRESS);
    }

    /**
     * When the current state is REJECTED the state is allowed to change to the following states.
     *   - REJECTED
     *   - IN_PROGRESS
     */
    @Test
    public void should_allow_state_changes_when_rejected() {
        Status[] allowedChanges = {Status.REJECTED, Status.IN_PROGRESS};

        performStatusChange(allowedChanges, Status.REJECTED);
    }

    /**
     * When the current state is PROCESSED the state is allowed to change to the following states.
     *   - PROCESSED
     *   - IN_PROGRESS (e.g. when the admin made a mistake and wants to make some changes)
     *   - SETTLED
     */
    @Test
    public void should_allow_state_changes_when_processed() {
        Status[] allowedChanges = {Status.PROCESSED, Status.IN_PROGRESS, Status.SETTLED};

        performStatusChange(allowedChanges, Status.PROCESSED);
    }

    /**
     * When the current state is SETTLED the state is not allowed to change to any other state.
     */
    @Test
    public void should_allow_state_changes_when_settled() {
        Status[] allowedChanges = {Status.SETTLED};

        performStatusChange(allowedChanges, Status.SETTLED);
    }

    private Submission getDefaultSubmission() {
        return new Submission(createdOn, date, paidBy, event, name, remarks, status);
    }

    private void performStatusChange(Status[] allowedChanges, Status initialStatus) {
        List<Status> allowedChangesList = List.of(allowedChanges);
        for (Status newStatus : Status.values()) {
            Submission submission = new Submission(createdOn, date, paidBy, event, name, remarks, initialStatus);
            if (allowedChangesList.contains(newStatus)) {
                assertDoesNotThrow(() -> submission.setStatus(newStatus));
            } else {
                assertThrows(IllegalStateException.class, () -> submission.setStatus(newStatus),
                        initialStatus + " to " + newStatus + " should not be possible.");
            }
        }
    }

    /**
     * Returns a Submission that can be used for testing.
     *
     * The status is set to {@code Status.BEING_CREATED} to allow for changing of the fields.
     *
     * @return a test Submission
     */
    public static Submission getTestSubmission_beingCreated() {
        return new Submission(LocalDate.now(), LocalDate.now(), FundUserTest.getTestCreditFundUser(),
                EventTest.getTestEvent(), "Test Submission", "No remarks",
                Status.BEING_CREATED);
    }

}
