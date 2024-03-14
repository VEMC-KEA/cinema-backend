package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Seat;

@Service
public class SeatService {
    public SeatHelperDto toHelperDto(Seat seat) {
        SeatHelperDto dto = new SeatHelperDto();
        dto.setRow_letter(seat.getRow_letter());
        dto.setNumber(seat.getNumber());
        return dto;
    }
}
