package ch.bolkhuis.declabo.fund;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Fund {

    private static final String idString = "Fund";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    protected String name;

    protected long balance;

    protected Fund() {}

    public Fund(String name, long balance) {
        this.name = Objects.requireNonNull(name);
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public abstract long debit(long amount);

    public abstract long credit(long amount);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fund fund = (Fund) o;
        return name.equals(fund.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "@" + getIdString() + "{name:" + name + "}";
    }

    /**
     * Returns the name of this class.
     *
     * It is used in the toString() method. This method eliminates code duplication by using the
     * same toString() signature for each child class.
     *
     * @return the name of this class
     */
    protected String getIdString() {
        return "Fund";
    }

}
