package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Seat;

@Service
public class SeatService {
    /**
     * This method is used to convert a Seat object to a SeatHelperDto object
     * @param seat Seat object
     * @return SeatHelperDto object
     */
    public SeatHelperDto toHelperDto(Seat seat) {
        SeatHelperDto dto = new SeatHelperDto();
        dto.setRowLetter(seat.getRowLetter());
        dto.setNumber(seat.getNumber());
        return dto;
    }
}
