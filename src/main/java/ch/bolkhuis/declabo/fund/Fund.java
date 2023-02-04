package ch.bolkhuis.declabo.fund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "funds", itemRelation = "fund")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Fund {

    private static final String idString = "Fund";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotBlank(message = "Name should not be blank")
    @Column(nullable = false, unique = true)
    protected String fundName;

    protected long balance;

    protected Fund() {}

    public Fund(String fundName, long balance) {
        this.fundName = Objects.requireNonNull(fundName);
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getFundName() {
        return fundName;
    }

    public void setFundName(@NotNull String fundName) {
        this.fundName = Objects.requireNonNull(fundName);
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
        return fundName.equals(fund.fundName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fundName);
    }

    @Override
    public String toString() {
        return "@" + getIdString() + "{name:" + fundName + "}";
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

    /**
     * Returns a String that is unique for this class. It is used only for serialization.
     *
     * @return a String that represents this class
     */
    public abstract String getType();

}
