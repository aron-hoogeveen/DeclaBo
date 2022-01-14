package ch.bolkhuis.declabo.fund;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractFund implements Fund {

    private long id;
    private @NotNull String name;
    protected long balance;

    protected AbstractFund() {
        this.name = "";
    }

    /**
     * Constructs a new {@code AbstractFund}.
     *
     * @param id the id
     * @param name the name, must be non-null
     * @param balance the balance
     * @throws NullPointerException if {@code name} is null
     */
    public AbstractFund(long id, @NotNull String name, long balance) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.balance = balance;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public long getBalance() {
        return this.balance;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }
}
