package ch.bolkhuis.declabo.user;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // we want null constraints
@Table(name = "custom_user", uniqueConstraints =
    @UniqueConstraint(columnNames = {"name", "surname"})
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    protected String email;

    @NonNull
    @Column(nullable = false, updatable = false)
    protected String name;

    @NonNull
    @Column(nullable = false, updatable = false)
    protected String surname;

    @Column(unique = true)
    protected int room;

    /** Default constructor. Should not be used. Is here only for Spring */
    protected User() {}

    /**
     * Constructs a new User.
     */
    public User(String email, String name, String surname, int room) {
        if (room < 0) throw new IllegalArgumentException("Room must be a positive number");

        this.email = Objects.requireNonNull(email);
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = Objects.requireNonNull(email);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @NonNull
    public String getSurname() {
        return surname;
    }

    public void setSurname(@NonNull String surname) {
        this.surname = Objects.requireNonNull(surname);
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && surname.equals(user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return "@" + getIdString() + "{name:" + name + "surname:" + surname + "}";
    }

    protected String getIdString() {
        return "User";
    }

}
