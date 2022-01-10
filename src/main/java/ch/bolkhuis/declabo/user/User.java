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

    private final long id;
    private final @NotNull String email;
    private final @NotNull String name;
    private final @NotNull String surname;
    private final @Nullable String nickname;

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

    public long getId() {
        return id;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getSurname() {
        return surname;
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
}
