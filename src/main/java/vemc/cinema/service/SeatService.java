package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.SeatDto;
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

    public Seat toDto(SeatDto SeatDto){
        Seat seat = new Seat();
        seat.setRowLetter(SeatDto.getRowLetter());
        seat.setNumber(SeatDto.getNumber());
        return seat;
    }

    public Seat toEntity(SeatDto SeatDto){
        Seat seat = new Seat();
        seat.setRowLetter(SeatDto.getRowLetter());
        seat.setNumber(SeatDto.getNumber());
        return seat;
    }
}
