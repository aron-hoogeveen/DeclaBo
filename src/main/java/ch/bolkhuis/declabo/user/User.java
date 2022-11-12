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
@Table(uniqueConstraints =
    @UniqueConstraint(columnNames = {"name", "surname"})
)
public class User {

    private static final String idString = "User";

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

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getSurname() {
        return surname;
    }

    public int getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && name.equals(user.name) && surname.equals(user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, surname);
    }

    @Override
    public String toString() {
        return "@" + getIdString() + "{name:" + name + "surname:" + surname + "}";
    }

    protected String getIdString() {
        return "User";
    }

}
