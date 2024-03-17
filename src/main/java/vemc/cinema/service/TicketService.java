package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Ticket;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final SeatService seatService;

    public TicketService(SeatService seatService) {
        this.seatService = seatService;
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
        dto.setRow_letter(seatDto.getRow_letter());
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

}
