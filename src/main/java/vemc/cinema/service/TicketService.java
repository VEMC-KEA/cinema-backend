package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.TicketDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final SeatService seatService;
    private final TicketRepository ticketRepository;

    public TicketService(SeatService seatService, TicketRepository ticketRepository) {
        this.seatService = seatService;
        this.ticketRepository = ticketRepository;
    }

    public List<ReservationTicketHelperDto> toHelperDtoList(List<Ticket> tickets){
        return tickets.stream()
                .map(this::toReservationHelperDto)
                .collect(Collectors.toList());
    }

    public void deleteByScreeningId(Long screeningId) {
        // Fetch the Ticket entities that reference the Screening
        List<Ticket> tickets = ticketRepository.findByScreeningId(screeningId);
        // Delete each Ticket entity
        for (Ticket ticket : tickets) {
            ticketRepository.delete(ticket);
        }
    }

    public Optional<TicketDto> deleteById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            return Optional.empty();
        }
        Ticket ticket = ticketOptional.get();
        // Remove the association between the Ticket and the Reservation
        ticket.setReservation(null);
        ticket.setScreening(null);
        ticketRepository.save(ticket);
        // Now delete the ticket
        ticketRepository.deleteById(id);
        return Optional.of(toDto(ticket));
    }

    private Optional<TicketDto> findById(Long id) {
        return ticketRepository.findById(id).map(this::toDto);
    }

    public ReservationTicketHelperDto toReservationHelperDto(Ticket ticket){
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        dto.setSeat(seatService.toHelperDto(ticket.getSeat()));
        dto.setPrice(ticket.getPrice());
        return dto;
    }

    public ReservationTicketHelperDto toHelperDto(Ticket ticket) {
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        dto.setSeat(seatService.toHelperDto(ticket.getSeat()));
        dto.setPrice(ticket.getPrice());
        return dto;
    }

    public TicketDto toDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setSeat(ticket.getSeat());
        return dto;
    }

}
