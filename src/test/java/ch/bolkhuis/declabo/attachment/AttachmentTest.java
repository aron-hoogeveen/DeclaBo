package ch.bolkhuis.declabo.attachment;

import ch.bolkhuis.declabo.submission.Submission;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentTest {

    @Test
    public void should_set_all_fields_on_construction() {
        LocalDate date = LocalDate.now();
        String path = "/home/declabo/uploads/randomstring/identifier.jpg";
        String notes = "Only the circled entries.";
        Submission submission = Submission.getTestSubmission();
        Attachment attachment = new Attachment(date, path, notes, submission);

        assertThat(attachment).hasFieldOrPropertyWithValue("uploadedOn", date);
        assertThat(attachment).hasFieldOrPropertyWithValue("path", path);
        assertThat(attachment).hasFieldOrPropertyWithValue("notes", notes);
    }

}
