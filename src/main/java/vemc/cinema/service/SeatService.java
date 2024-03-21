package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.SeatHelperDto;
import vemc.cinema.entity.Seat;
import vemc.cinema.repository.SeatRepository;

import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    /**
     * find seat by id
     * @param Id seat id
     * @return Seat
     */
    public Optional<Seat> findById(Long Id) {
        return seatRepository.findById(Id);
    }

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
