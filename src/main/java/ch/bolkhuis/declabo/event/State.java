package ch.bolkhuis.declabo.event;

public enum State {
    OPEN(0), // open for submissions
    CLOSED(1), // closed for submissions
    SETTLED(2); // all the costs have been settled

    private final int value;

    State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
