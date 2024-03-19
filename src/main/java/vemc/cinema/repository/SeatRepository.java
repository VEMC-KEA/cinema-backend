package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
