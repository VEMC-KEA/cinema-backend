package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
