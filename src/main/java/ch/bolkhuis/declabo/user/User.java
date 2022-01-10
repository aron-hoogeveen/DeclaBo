package ch.bolkhuis.declabo.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Class {@code User} describes properties of user accounts.
 *
 * <p>The user class have the following properties:
 * <ul>
 *     <li>Id;</li>
 *     <li>Email address;</li>
 *     <li>Name;</li>
 *     <li>Surname;</li>
 *     <li>Nickname.</li>
 * </ul>
 */
public class User {

    private @NotNull Long id;
    private @NotNull String email;
    private @NotNull String name;
    private @NotNull String surname;
    private @Nullable String nickname;

    private User() {}

    /**
     * Constructs a new User.
     *
     * @param id the id, must not be null
     * @param email the email address, must not be null
     * @param name the name, must not be null
     * @param surname the surname, must not be null
     * @param nickname the nickname
     * @throws NullPointerException if {@code id}, {@code email}, {@code name}, or
     *     {@code surname} is null
     * @throws IllegalArgumentException if {@code name}, {@code surname} is not a valid name, or if
     *     {@code nickname} is non-null and not a valid name
     */
    public User(long id, @NotNull String email, @NotNull String name, @NotNull String surname,
                @Nullable String nickname) {
        Objects.requireNonNull(email, "A user must have an email");
        Objects.requireNonNull(name, "A user must have a name");
        Objects.requireNonNull(surname, "A user must have a surname");

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Argument 'email' is not a valid email address");
        }
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Argument 'name' is not a valid name");
        }
        if (!isValidName(surname)) {
            throw new IllegalArgumentException("Argument 'surname' is not a valid name");
        }
        if (nickname != null && !isValidName(nickname)) {
            throw new IllegalArgumentException("Argument 'nickname' is not a valid name");
        }

        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
    }

    private void setId(long id) {
        this.id = id;
    }

    public long getId() {
        assert id != null;
        return id;
    }

    private void setEmail(@NotNull String email) {
        Objects.requireNonNull(email, "Parameter 'email' must not be null");
        this.email = email;
    }

    public @NotNull String getEmail() {
        assert email != null;
        return email;
    }

    private void setName(@NotNull String name) {
        Objects.requireNonNull(name, "Parameter 'name' must not be null");
        this.name = name;
    }

    public @NotNull String getName() {
        assert name != null;
        return name;
    }

    private void setSurname(@NotNull String surname) {
        Objects.requireNonNull(surname, "Parameter 'surname' must not be null");
        this.surname = surname;
    }

    public @NotNull String getSurname() {
        assert surname != null;
        return surname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public @Nullable String getNickname() {
        return nickname;
    }

    /**
     * Returns whether the provided String is a valid name.
     *
     * <p>A name is valid when it is not blank.
     *
     * @param str the String to check
     * @return {@code true} is the name if valid, {@code false} otherwise
     */
    public static boolean isValidName(String str) {
        if (str == null) {
            return false;
        }

        return !str.isBlank();
    }

    /**
     * Returns whether the provided String is a valid email. The checks for a valid email address
     * are very lenient and most likely not enough to catch invalid email addresses. The service
     * that will use this User class must make sure it does proper validation (e.g. by sending an
     * activation mail).
     *
     * @param str the String to check
     * @return {@code true} if the email is non-null and non-blank, {@code false} otherwise
     */
    public static boolean isValidEmail(String str) {
        if (str == null) {
            return false;
        }

        return !str.isBlank();
    }

    /**
     * Returns whether the provided Object is considered to be equal to this {@code User}.
     * Users are considered equal when they have the same {@code email}, {@code name},
     * {@code surname}, and {@code nickname}.
     *
     * @param o the object to compare to
     * @return {@code true} if the object is considered equal to this User, {@code false}
     *     otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!email.equals(user.email)) return false;
        if (!name.equals(user.name)) return false;
        if (!surname.equals(user.surname)) return false;
        return Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{"
                + "email='" + email + '\''
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", nickname='" + nickname + '\''
                + '}';
    }

    /**
     * Returns a {@code User} builder.
     *
     * @return a Builder for a {@code User}
     */
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Returns a {@code User} builder. The fields of the encapsulated user are set to equal the
     * fields of the provided {@code user}. A shallow copy is made.
     *
     * @param user the user to copy the fields from
     * @return a Builder for a {@code User}
     * @throws NullPointerException if {@code user} is null
     */
    public static @NotNull Builder builder(@NotNull User user) {
        Objects.requireNonNull(user, "The user must not be null");
        return new Builder(user);
    }

    /**
     * Builder class for a {@link User} object.
     */
    public static class Builder {

        private transient @NotNull User user;

        private Builder() {
            this.user = new User();
        }

        private Builder(User user) {
            this.user = new User();
            this.user.id = user.id;
            this.user.email = user.email;
            this.user.name = user.name;
            this.user.surname = user.surname;
            this.user.nickname = user.nickname;
        }

        public Builder setId(long id) {
            user.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            user.email = email;
            return this;
        }

        public Builder setName(String name) {
            user.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            user.surname = surname;
            return this;
        }

        public Builder setNickname(String nickname) {
            user.nickname = nickname;
            return this;
        }

        /**
         * Validates the provided user.
         *
         * <p>A user is considered valid if:
         * <ul>
         *     <li>{@code id} is set;</li>
         *     <li>{@code email} is set;</li>
         *     <li>{@code email} is a valid email;</li>
         *     <li>{@code name} is set;</li>
         *     <li>{@code name} is a valid name;</li>
         *     <li>{@code surname} is set;</li>
         *     <li>{@code surname} is a valid name;</li>
         *     <li>if {@code nickname} is set, it is a valid name.</li>
         * </ul>
         *
         * @param user the User to validate
         * @throws IllegalStateException if the state of the embedded user is illegal
         */
        private void validateUser(User user) {
            if (user.id == null) {
                throw new IllegalStateException("Field 'id' is not set");
            }
            if (user.email == null) {
                throw new IllegalStateException("Field 'email' is not set");
            }
            if (!isValidEmail(user.email)) {
                throw new IllegalStateException("Field 'email' is not a valid email");
            }
            if (user.name == null) {
                throw new IllegalStateException("Field 'name' is not set");
            }
            if (!isValidName(user.name)) {
                throw new IllegalStateException("Field 'name' is not a valid name");
            }
            if (user.surname == null) {
                throw new IllegalStateException("Field 'surname' is not set");
            }
            if (!isValidName(user.surname)) {
                throw new IllegalStateException("Field 'surname' is not a valid name");
            }
            if (user.nickname != null && !isValidName(user.nickname)) {
                throw new IllegalStateException("Field 'nickname' is not a valid name");
            }
        }

        /**
         * Builds the user that is encapsulated by this {@code Builder}. If the building succeeds
         * the encapsulated user is reset, so that this builder can be reused.
         *
         * @return the encapsulated user
         * @throws IllegalStateException if the user is in an illegal state
         */
        public @NotNull User build() {
            validateUser(user);
            User buildUser = user;
            user = new User();
            return buildUser;
        }
    }
}
