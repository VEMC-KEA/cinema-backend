package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
}
