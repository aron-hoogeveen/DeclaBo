package ch.bolkhuis.declabo.user;

import ch.bolkhuis.declabo.fund.Fund;
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
}