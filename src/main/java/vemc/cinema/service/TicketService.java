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

    public List<ReservationTicketHelperDto> toHelperDtoList(List<Ticket> tickets){
        return tickets.stream()
                .map(this::toReservationHelperDto)
                .collect(Collectors.toList());
    }

   /* public ReservationTicketHelperDto toReservationHelperDto(Ticket ticket){
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        dto.setSeat(seatService.toHelperDto(ticket.getSeat()));
        dto.setPrice(ticket.getPrice());
        return dto;
    }*/

    public ReservationTicketHelperDto toReservationHelperDto(Ticket ticket){
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        SeatHelperDto seatDto = seatService.toHelperDto(ticket.getSeat());
        dto.setRow_letter(seatDto.getRow_letter());
        dto.setNumber(seatDto.getNumber());
        dto.setPrice(ticket.getPrice());
        return dto;
    }

    public ReservationTicketHelperDto toHelperDto(Ticket ticket) {
        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
        //dto.setSeat(seatService.toHelperDto(ticket.getSeat()));
        dto.setPrice(ticket.getPrice());
        return dto;
    }

}
