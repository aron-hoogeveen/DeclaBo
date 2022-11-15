package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.fund.Fund;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    protected Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Fund debtor;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Fund creditor;

    protected long amount;

    @NotNull
    @Column(nullable = false)
    protected LocalDate date;

    @NotNull
    @Column(nullable = false)
    protected String description;

    protected Transaction() {}

    public Transaction(Fund debtor, Fund creditor, long amount, LocalDate date, String description) {
        this.debtor = Objects.requireNonNull(debtor);
        this.creditor = Objects.requireNonNull(creditor);

        if (amount <= 0L) throw new IllegalArgumentException("Amount must not be zero or negative");

        this.amount = amount;
        this.date = Objects.requireNonNull(date);
        this.description = Objects.requireNonNull(description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Fund getDebtor() {
        return debtor;
    }

    public void setDebtor(@NotNull Fund debtor) {
        this.debtor = Objects.requireNonNull(debtor);
    }

    public @NotNull Fund getCreditor() {
        return creditor;
    }

    public void setCreditor(@NotNull Fund creditor) {
        this.creditor = Objects.requireNonNull(creditor);
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        if (amount <= 0L) throw new IllegalArgumentException("Amount must not be zero or negative");

        this.amount = amount;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "@Transaction{debtor:" + debtor + ", creditor:" + creditor + ", date:" + date +
                ", description:" + description + "}";
    }

}
