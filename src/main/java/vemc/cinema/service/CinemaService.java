package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.CinemaResponseDto;
import vemc.cinema.dto.HallResponseDto;
import vemc.cinema.entity.Cinema;
import vemc.cinema.entity.Hall;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Seat;
import vemc.cinema.repository.CinemaRepository;
import vemc.cinema.repository.HallRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;

    public CinemaService(CinemaRepository cinemaRepository, HallRepository hallRepository) {
        this.cinemaRepository = cinemaRepository;
        this.hallRepository = hallRepository;
    }

    public List<CinemaResponseDto> findAll() {
        return cinemaRepository.findAll().stream().map(this::toDto).toList();
    }
    public CinemaResponseDto findById(Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        return cinemaOptional.map(this::toDto).orElse(null);
    }

    public List<HallResponseDto> getHallsByCinemaId(Long cinemaId) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if (cinemaOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Cinema cinema = cinemaOptional.get();
        List<Hall> halls = cinema.getHall();
        if (halls == null || halls.isEmpty()) {
            return Collections.emptyList();
        }
        return halls.stream()
                .map(this::toDtoHall)
                .collect(Collectors.toList());
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
    private HallResponseDto toDtoHall(Hall hall) {
        HallResponseDto dto = new HallResponseDto();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setAmountOfFrontRowDiscounted(hall.getAmountOfFrontRowDiscounted());
        dto.setSeat(hall.getSeat());
        dto.setScreening(hall.getScreening());
        return dto;
    }
}

