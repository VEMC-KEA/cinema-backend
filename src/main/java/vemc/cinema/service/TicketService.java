package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.SeatDto;
import vemc.cinema.dto.TicketDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Seat;
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

    public TicketDto saveTicket(TicketDto ticketDto) {
        Ticket ticket = toEntity(ticketDto);
        ticketRepository.save(ticket);
        return toDto(ticket);
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


    public TicketDto toDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        SeatDto seatDto = new SeatDto();
        seatDto.setId(ticket.getSeat().getId());
        dto.setSeat(seatService.toDto(seatDto));
        return dto;
    }

    public Ticket toEntity(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setPrice(ticketDto.getPrice());
        SeatDto seatDto = new SeatDto();
        seatDto.setId(ticketDto.getSeat().getId());
        ticket.setSeat(seatService.toEntity(seatDto));
        return ticket;
    }
}
