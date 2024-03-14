package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
