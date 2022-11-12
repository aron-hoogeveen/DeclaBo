package ch.bolkhuis.declabo.old.user;

import java.util.Optional;

public interface User {

    /**
     * Gets the id of this {@code User}.
     *
     * @return the id
     */
    Long getId();

    /**
     * Gets the email of this {@code User}.
     *
     * @return the email
     */
    String getEmail();

    /**
     * Gets the name of this {@code User}.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the surname of this {@code User}.
     *
     * @return the surname
     */
    String getSurname();

    /**
     * Gets the nickname of this {@code User} if it has one.
     *
     * @return the surname
     */
    Optional<String> getNickname();
}
