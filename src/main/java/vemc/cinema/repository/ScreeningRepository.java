package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Screening;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
