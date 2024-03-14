package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.TicketDto;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketDto> findAll() {
        return ticketRepository.findAll().stream().map(this::toDto).toList();
    }

    public TicketDto findById(Long id) {
        return ticketRepository.findById(id).map(this::toDto).orElse(null);
    }

    private TicketDto toDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setCompleted(ticket.isCompleted());
        dto.setScreening(ticket.getScreening());
        dto.setSeat(ticket.getSeat());
        return dto;
    }
}
