package ch.bolkhuis.declabo.fund;

import org.jetbrains.annotations.NotNull;

public class CreditFund extends AbstractFund {

    public CreditFund(Long id, @NotNull String name, long balance) {
        super(id, name, balance);
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    @Override
    public long debit(long amount) {
        if (amount < 0L) {
            throw new IllegalArgumentException("Cannot debit a negative amount");
        }
        if (Long.MIN_VALUE + amount > this.balance) {
            throw new ArithmeticException("Debiting '" + amount + "' will result in an underflow");
        }
        this.balance -= amount;
        return this.balance;
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    @Override
    public long credit(long amount) {
        if (amount < 0L) {
            throw new IllegalArgumentException("Cannot credit a negative amount");
        }
        if (Long.MAX_VALUE - amount < this.balance) {
            throw new ArithmeticException("Crediting '" + amount + "' will result in an overflow");
        }
        this.balance += amount;
        return this.balance;
    }
}
