package ch.bolkhuis.declabo.user;

public class UserNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    UserNotFoundException(Long id) {
        super("Could not find User with id " + id);
    }

}
