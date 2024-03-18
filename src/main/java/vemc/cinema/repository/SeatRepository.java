package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByNumber(Integer seatNumber);
}
