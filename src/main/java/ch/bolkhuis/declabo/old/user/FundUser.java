package ch.bolkhuis.declabo.old.user;

import ch.bolkhuis.declabo.old.fund.Fund;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FundUser extends SimpleUser {

    private @NotNull Fund fund;

    /**
     * Constructs a new SimpleUser.
     *
     * @param id       the id, must not be null
     * @param email    the email address, must not be null
     * @param name     the name, must not be null
     * @param surname  the surname, must not be null
     * @param nickname the nickname
     * @throws NullPointerException     if {@code id}, {@code email}, {@code name}, or
     *                                  {@code surname} is null
     * @throws IllegalArgumentException if {@code name}, {@code surname} is not a valid name, or if
     *                                  {@code nickname} is non-null and not a valid name
     */
    public FundUser(
            Long id,
            @NotNull String email,
            @NotNull String name,
            @NotNull String surname,
            @Nullable String nickname,
            @NotNull Fund fund
    ) {
        super(id, email, name, surname, nickname);

        this.fund = Objects.requireNonNull(fund);
    }

    /**
     * Gets the {@code Fund} of this {@code FundUser}.
     *
     * @return the fund
     */
    public @NotNull Fund getFund() {
        return this.fund;
    }

    private void setFund(@NotNull Fund fund) {
        this.fund = Objects.requireNonNull(fund);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FundUser fundUser = (FundUser) o;

        return fund.equals(fundUser.fund);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + fund.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FundUser{"
                + "name=" + getName()
                + '}';
    }
}
