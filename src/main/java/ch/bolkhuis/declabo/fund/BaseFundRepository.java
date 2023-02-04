package ch.bolkhuis.declabo.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseFundRepository<T extends Fund> extends JpaRepository<T, Long> {
    boolean existsByFundName(String fundName);

    boolean existsByFundNameAndIdNot(String fundName, Long id);
}
