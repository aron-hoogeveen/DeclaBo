package ch.bolkhuis.declabo.fund;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractFund implements Fund {

    private @Nullable Long id;
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
    public AbstractFund(@Nullable Long id, @NotNull String name, long balance) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.balance = balance;
    }

    @Override
    public @Nullable Long getId() {
        return this.id;
    }

    private void setId(@Nullable Long id) {
        this.id = id;
    }

    @Override
    public long getBalance() {
        return this.balance;
    }

    private void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    private void setName(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }
}
