package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.fund.Fund;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

//@Entity
public class Transaction {

    @Id
    @GeneratedValue
    protected Long id;

    protected Fund debtor;

    protected Fund creditor;

    protected long amount;

    protected LocalDate date;

    protected String description;

}
