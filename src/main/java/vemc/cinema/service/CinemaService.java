package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.CinemaResponseDto;
import vemc.cinema.entity.Cinema;
import vemc.cinema.repository.CinemaRepository;

import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public List<CinemaResponseDto> findAll() {
        return cinemaRepository.findAll().stream().map(this::toDto).toList();
    }


    private CinemaResponseDto toDto(Cinema cinema) {
        CinemaResponseDto dto = new CinemaResponseDto();
        dto.setId(cinema.getId());
        dto.setName(cinema.getName());
        dto.setReservationFee(cinema.getReservationFee());
        dto.setGroupDiscount(cinema.getGroupDiscount());
        dto.setMovieBasePrice(cinema.getMovieBasePrice());
        dto.setMovies(cinema.getMovies());
        dto.setHall(cinema.getHall());
        return dto;
    }
}
