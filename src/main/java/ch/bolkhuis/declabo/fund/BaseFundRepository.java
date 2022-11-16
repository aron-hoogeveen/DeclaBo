package ch.bolkhuis.declabo.fund;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseFundRepository<T extends Fund> extends JpaRepository<T, Long> {
}
