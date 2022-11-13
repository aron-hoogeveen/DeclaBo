package ch.bolkhuis.declabo.user;

import ch.bolkhuis.declabo.fund.CreditFund;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class FundUser extends User {

    @NonNull
    @OneToOne(optional = false)
    @JoinColumn(nullable = false, name = "fund_id")
    protected CreditFund fund;

    @Column(name = "fund_id", insertable = false, updatable = false)
    protected Long fundId; // prevents join operations when selecting on fund_id

    /** Default constructor. Should not be used. Is here only for Spring */
    protected FundUser() {}

    public FundUser(String email, String name, String surname, int room, @NonNull CreditFund fund) {
        super(email, name, surname, room);
        this.fund = Objects.requireNonNull(fund);
    }

    @NonNull
    public CreditFund getFund() {
        return fund;
    }

    public void setFund(@NonNull CreditFund fund) {
        this.fund = Objects.requireNonNull(fund);
    }

    @Override
    protected String getIdString() {
        return "FundUser";
    }

}
