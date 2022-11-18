package ch.bolkhuis.declabo.submission;

public enum Status {
    BEING_CREATED(0),
    PENDING(1),
    IN_PROGRESS(2), // the submission has been uploaded, but nothing has been done with it
    REJECTED(3), // the admin has rejected this submission. Contact him/her why and how to fix it
    PROCESSED(4), // the admin has processed this submission.
    SETTLED(5); // the submission has been settled in a Huischrekening

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
