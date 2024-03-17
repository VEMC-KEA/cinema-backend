package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Seat;

@Service
public class SeatService {
    public SeatHelperDto toHelperDto(Seat seat) {
        SeatHelperDto dto = new SeatHelperDto();
        dto.setRow_letter(seat.getRowLetter());
        dto.setNumber(seat.getNumber());
        return dto;
    }

    public Seat createSeat(SeatHelperDto seatHelperDto) {
        Seat seat = new Seat();
        seat.setRowLetter(seatHelperDto.getRow_letter());
        seat.setNumber(seatHelperDto.getNumber());
        return seat;
    }
}
