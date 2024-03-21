package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.ScreeningRepository;
import vemc.cinema.repository.TicketRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final SeatService seatService;
    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;

    public TicketService(SeatService seatService, TicketRepository ticketRepository, ScreeningRepository screeningRepository) {
        this.seatService = seatService;
        this.ticketRepository = ticketRepository;
        this.screeningRepository = screeningRepository;
    }

    /**
     * This method is used to convert a list of Ticket objects to a list of ReservationTicketHelperDto objects
     * @param tickets list of Ticket objects
     * @return list of ReservationTicketHelperDto objects
     */
    public List<ReservationTicketHelperDto> toHelperDtoList(List<Ticket> tickets){
        return tickets.stream()
                .map(this::toReservationHelperDto)
                .collect(Collectors.toList());
    }

    /**
     * This method is used to convert a Ticket object to a ReservationTicketHelperDto object
     * @param ticket Ticket object
     * @return ReservationTicketHelperDto object
     */
    public ReservationTicketHelperDto toReservationHelperDto(Ticket ticket){
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        SeatHelperDto seatDto = seatService.toHelperDto(ticket.getSeat());
        dto.setRowLetter(seatDto.getRowLetter());
        dto.setNumber(seatDto.getNumber());
        dto.setPrice(ticket.getPrice());
        dto.setId(ticket.getId());
        return dto;
    }

    /**
     * This method is used to convert a Ticket object to a ReservationTicketHelperDto object
     * @param ticket Ticket object
     * @return ReservationTicketHelperDto object
     */
    public ReservationTicketHelperDto toHelperDto(Ticket ticket) {
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        dto.setPrice(ticket.getPrice());
        dto.setId(ticket.getId());
        dto.setSeatId(ticket.getSeat().getId());
        dto.setRowLetter(ticket.getSeat().getRowLetter());
        dto.setNumber(ticket.getSeat().getNumber());
        return dto;
    }

    /**
     * This method is used to find a Ticket by its id
     * @param id The id of the Ticket to find
     * @return An Optional containing the Ticket if found, otherwise an empty Optional
     */
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    /**
     * This method is used to save a Ticket on a Screening
     * @param ticket The Ticket to save
     * @param screeningId The id of the Screening the Ticket is for
     * @return The saved Ticket
     */
    public Ticket saveTicket(Ticket ticket, Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new IllegalArgumentException("Screening not found"));
        ticket.setScreening(screening);
        return ticketRepository.save(ticket);
    }


    /**
     * This method is used to delete a Ticket by its id
     * @param id The id of the Ticket to delete
     */
    public void deleteById(Long id) {
        Optional<Ticket> existingTicket = findById(id);
        existingTicket.ifPresent(ticket -> ticketRepository.deleteById(ticket.getId()));
    }
}
