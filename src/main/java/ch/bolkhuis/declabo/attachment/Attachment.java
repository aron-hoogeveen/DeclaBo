package ch.bolkhuis.declabo.attachment;

import ch.bolkhuis.declabo.submission.Submission;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @NonNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "submission_id")
    protected Submission submission;

    @Column(name = "submission_id", insertable = false, updatable = false)
    protected Long submissionId;

    protected Attachment() {}

    public Attachment(@NonNull LocalDate uploadedOn, @NonNull String path, String notes,
                      @NonNull Submission submission) {
        this.uploadedOn = Objects.requireNonNull(uploadedOn);
        this.path = Objects.requireNonNull(path);
        this.notes = notes;
        this.submission = Objects.requireNonNull(submission);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public LocalDate getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(@NonNull LocalDate uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @NonNull
    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(@NonNull Submission submission) {
        this.submission = submission;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "@Attachment{path:" + path + "}";
    }

}
