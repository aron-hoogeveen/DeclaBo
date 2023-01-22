package ch.bolkhuis.declabo.fund;

public class FundNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    FundNotFoundException(Long id) {
        super("Could not find Fund with id " + id);
    }

}
