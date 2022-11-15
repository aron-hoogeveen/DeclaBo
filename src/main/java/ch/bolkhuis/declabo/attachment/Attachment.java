package ch.bolkhuis.declabo.attachment;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Attachment {

    @Id
    @GeneratedValue
    protected Long id;

    @NonNull
    @Column(nullable = false)
    protected LocalDate uploadedOn;

    /**
     * Path to the file on the local filesystem.
     */
    @NonNull
    @Column(nullable = false)
    protected String path;

    protected String notes;

    protected Attachment() {}

    public Attachment(@NonNull LocalDate uploadedOn, @NonNull String path, String notes) {
        this.uploadedOn = Objects.requireNonNull(uploadedOn);
        this.path = Objects.requireNonNull(path);
        this.notes = notes;
    }

}
