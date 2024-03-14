package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Seat;
import vemc.cinema.entity.Ticket;

@Service
public class SeatService {
    private final ScreeningService screeningService;

    public SeatService(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    public SeatHelperDto toHelperDto(Seat seat) {
        SeatHelperDto dto = new SeatHelperDto();
        dto.setRow_letter(seat.getRow_letter());
        dto.setNumber(seat.getNumber());
        return dto;
    }
}
