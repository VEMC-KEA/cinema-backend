package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<List<Ticket>> findAllByReservationId(Long id);

    Optional<Ticket> findFirstByReservationIdAndSeatId(Long reservationId, Long seatId);
}
