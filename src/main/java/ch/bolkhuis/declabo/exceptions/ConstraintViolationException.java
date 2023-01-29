package ch.bolkhuis.declabo.exceptions;

import java.util.Map;
import java.util.Objects;

public class ConstraintViolationException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    private final Map<String, String> errors;

    /**
     * Construct a new ConstraintViolationException.
     *
     * @param errors A map that maps an error message for each constraint violation
     */
    public ConstraintViolationException(Map<String, String> errors) {
        this.errors = Map.copyOf(Objects.requireNonNull(errors)); // ensure unmodifiability
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
