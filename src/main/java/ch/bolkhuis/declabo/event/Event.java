package ch.bolkhuis.declabo.event;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    protected Long id;
}
