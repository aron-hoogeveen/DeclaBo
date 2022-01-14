package ch.bolkhuis.declabo.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Class {@code SimpleUser} describes properties of user accounts.
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
public class SimpleUser implements User {

    private Long id;
    private @NotNull String email;
    private @NotNull String name;
    private @NotNull String surname;
    private @Nullable String nickname;

    private SimpleUser() {}

    /**
     * Constructs a new SimpleUser.
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
    public SimpleUser(Long id, @NotNull String email, @NotNull String name, @NotNull String surname,
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
        return id;
    }

    private void setEmail(@NotNull String email) {
        Objects.requireNonNull(email, "Parameter 'email' must not be null");
        this.email = email;
    }

    public @NotNull String getEmail() {
        return email;
    }

    private void setName(@NotNull String name) {
        Objects.requireNonNull(name, "Parameter 'name' must not be null");
        this.name = name;
    }

    public @NotNull String getName() {
        return name;
    }

    private void setSurname(@NotNull String surname) {
        Objects.requireNonNull(surname, "Parameter 'surname' must not be null");
        this.surname = surname;
    }

    public @NotNull String getSurname() {
        return surname;
    }

    private void setNickname(@Nullable String nickname) {
        this.nickname = nickname;
    }

    public @NotNull Optional<String> getNickname() {
        return Optional.ofNullable(this.nickname);
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
     * that will use this SimpleUser class must make sure it does proper validation (e.g. by sending
     * an activation mail).
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
     * Returns whether the provided Object is considered to be equal to this {@code SimpleUser}.
     * Users are considered equal when they have the same {@code email}, {@code name},
     * {@code surname}, and {@code nickname}.
     *
     * @param o the object to compare to
     * @return {@code true} if the object is considered equal to this SimpleUser, {@code false}
     *     otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleUser simpleUser = (SimpleUser) o;

        if (!email.equals(simpleUser.email)) return false;
        if (!name.equals(simpleUser.name)) return false;
        if (!surname.equals(simpleUser.surname)) return false;
        return Objects.equals(nickname, simpleUser.nickname);
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
        return "SimpleUser{"
                + "email='" + email + '\''
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", nickname='" + nickname + '\''
                + '}';
    }
}
