package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.TicketHelperDto;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    private final SeatService seatService;

    public TicketService(TicketRepository ticketRepository, SeatService seatService) {
        this.ticketRepository = ticketRepository;
        this.seatService = seatService;
    }

//    public ReservationTicketHelperDto toHelperDto(Ticket ticket){
//        ReservationTicketHelperDto dto = new ReservationTicketHelperDto();
//        dto.setSeat(ticket.getSeat());
//        dto.setPrice(ticket.getPrice());
//        return dto;
//    }
//
////    public List<ReservationTicketHelperDto> toHelperDtoList(List<Ticket> tickets){
////        return tickets.stream()
////                .map(this::toHelperDto)
////                .collect(Collectors.toList()).reversed();
////    }

    public List<ReservationTicketHelperDto> toHelperDtoList(List<Ticket> tickets){
        return tickets.stream()
                .map(this::toReservationHelperDto)
                .collect(Collectors.toList());
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



}
