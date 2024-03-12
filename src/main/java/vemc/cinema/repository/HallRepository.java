package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {
}
