package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.MovieResponseDto;
import vemc.cinema.dto.TicketResponseDto;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<TicketResponseDto> findAll() {
        return ticketRepository.findAll().stream().map(this::toDto).toList();
    }

    public TicketResponseDto findById(Long id) {
        return ticketRepository.findById(id).map(this::toDto).orElse(null);
    }

    private TicketResponseDto toDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setCompleted(ticket.isCompleted());
        dto.setScreening(ticket.getScreening());
        dto.setSeat(ticket.getSeat());
        return dto;
    }
}
