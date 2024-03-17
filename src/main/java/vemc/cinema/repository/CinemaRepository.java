package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Long>{
}
