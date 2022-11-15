package ch.bolkhuis.declabo.attachment;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentTest {

    @Test
    public void should_set_all_fields_on_construction() {
        LocalDate date = LocalDate.now();
        String path = "/home/declabo/uploads/randomstring/identifier.jpg";
        String notes = "Only the circled entries.";
        Attachment attachment = new Attachment(date, path, notes);

        assertThat(attachment).hasFieldOrPropertyWithValue("date", date);
        assertThat(attachment).hasFieldOrPropertyWithValue("path", path);
        assertThat(attachment).hasFieldOrPropertyWithValue("notes", notes);
    }

}
