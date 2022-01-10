package ch.bolkhuis.declabo.accounting;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A monetary accounting entity.
 */
public abstract class AbstractAccountingEntity implements AccountingEntity {

    protected final @NotNull String name;
    protected long balance;

    /**
     * Construct a new {@code AbstractAccountingEntity}. Set the initial balance.
     *
     * @param name the name
     * @param balance the initial balance
     * @throws IllegalArgumentException if {@code name} is not a valid name
     * @throws NullPointerException if {@code name} is null
     * @see #isValidName(String)
     */
    AbstractAccountingEntity(@NotNull String name, long balance) {
        Objects.requireNonNull(name, "Parameter 'name' must not be null");

        if (!isValidName(name)) {
            throw new IllegalArgumentException("Parameter 'name' is not a valid name");
        }

        this.name = name;
        this.balance = balance;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    /**
     * Returns whether the provided string is a valid name. A String is valid if it is non-blank.
     *
     * @param str the string
     * @return {@code true} if the string is a valid name, {@code false} otherwise
     */
    public static boolean isValidName(String str) {
        if (str == null) {
            return false;
        }

        return !str.isBlank();
    }

    /**
     * Returns whether the provided object is equal to this {@code AbstractAccountingEntity}.
     * An {@code AbstractAccountingEntity} is considered equal if it has the same {@code name}.
     *
     * @param o the object
     * @return {@code true} if the object is equal to this {@code AbstractAccountingEntity},
     *     {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAccountingEntity that = (AbstractAccountingEntity) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
