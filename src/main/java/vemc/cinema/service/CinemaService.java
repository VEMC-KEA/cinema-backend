package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.*;
import vemc.cinema.dto.helperdto.HallHelperDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;
import vemc.cinema.entity.*;
import vemc.cinema.repository.CinemaRepository;
import vemc.cinema.repository.HallRepository;
import vemc.cinema.repository.SeatRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;

    public CinemaService(CinemaRepository cinemaRepository, HallRepository hallRepository, SeatRepository seatRepository) {
        this.cinemaRepository = cinemaRepository;
        this.hallRepository = hallRepository;
        this.seatRepository = seatRepository;
    }

    public List<CinemaResponseDto> findAll() {
        return cinemaRepository.findAll().stream().map(this::toDtoCinema).toList();
    }
    public CinemaResponseDto findById(Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        return cinemaOptional.map(this::toDtoCinema).orElse(null);
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

    public HallResponseDto getHallsByIdByCinemaId(Long cinemaId, Long hallId) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if(cinemaOptional.isEmpty()) {
            return null;
        }

        Cinema cinema = cinemaOptional.get();
        Optional<Hall> hallOptional = cinema.getHall().stream().filter(hall -> hall.getId().equals(hallId)).findFirst();
        return hallOptional.map(this::toDtoHall).orElse(null);
    }

    public List<SeatResponseDto> getSeatsByHallsIdByCinemaId(Long cinemaId, Long hallId) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);

        if (cinemaOptional.isEmpty()) {
            return Collections.emptyList();
        }

        Cinema cinema = cinemaOptional.get();
        Optional<Hall> hallOptional = cinema.getHall().stream().filter(hall -> hall.getId().equals(hallId)).findFirst();

        if (hallOptional.isEmpty()) {
            return Collections.emptyList();
        }

        Hall hall = hallOptional.get();
        List<Seat> seats = hall.getSeat();

        return seats.stream()
                .map(this::toDtoSeat)
                .collect(Collectors.toList());
    }

    public SeatResponseDto getSeatByIdByHallsIdByCinemaId(Long cinemaId, Long hallId, Long seatId) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);

        if (cinemaOptional.isEmpty()) {
            return null;
        }

        Cinema cinema = cinemaOptional.get();
        Optional<Hall> hallOptional = cinema.getHall().stream().filter(hall -> hall.getId().equals(hallId)).findFirst();

        if (hallOptional.isEmpty()) {
            return null;
        }

        Hall hall = hallOptional.get();
        Optional<Seat> seatOptional = hall.getSeat().stream().filter(seat -> seat.getId().equals(seatId)).findFirst();

        return seatOptional.map(this::toDtoSeat).orElse(null);
    }

    public List<MovieResponseDto> getMovieByCinemaId (Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);

        if (cinemaOptional.isEmpty()) {
            return null;
        }

        Cinema cinema = cinemaOptional.get();
        List<Movie> movie = cinema.getMovies();
        if (movie == null || movie.isEmpty()) {
            return Collections.emptyList();
        }


        return movie.stream()
                .map(this::toDtoMovie)
                .collect(Collectors.toList());
    }

    private CinemaResponseDto toDtoCinema(Cinema cinema) {
        CinemaResponseDto dto = new CinemaResponseDto();
        dto.setId(cinema.getId());

        List<MovieHelperDto> movieDtos = cinema.getMovies().stream()
                .map(movie -> {
                    MovieHelperDto movieDto = new MovieHelperDto();
                    movieDto.setId(movie.getId());
                    movieDto.setTitle(movie.getTitle());
                    return movieDto;
                })
                .collect(Collectors.toList());
        dto.setMovies(movieDtos);

        List<HallHelperDto> hallDtos = cinema.getHall().stream()
                .map(hall -> {
                    HallHelperDto hallDto = new HallHelperDto();
                    hallDto.setId(hall.getId());
                    hallDto.setNumber(hall.getNumber());
                    return hallDto;
                })
                .collect(Collectors.toList());
        dto.setHall(hallDtos);

        return dto;
    }

    private HallResponseDto toDtoHall(Hall hall) {
        HallResponseDto dto = new HallResponseDto();
        dto.setId(hall.getId());
        dto.setNumber(hall.getNumber());
        dto.setAmountOfFrontRowDiscounted(hall.getAmountOfFrontRowDiscounted());
        dto.setSeat(hall.getSeat());
        dto.setScreening(hall.getScreening());
        return dto;
    }

    private SeatResponseDto toDtoSeat(Seat seat) {
        SeatResponseDto dto = new SeatResponseDto();
        dto.setId(seat.getId());
        dto.setNumber(seat.getNumber());
        dto.setRow_letter(seat.getRow_letter());
        return dto;
    }

    private MovieResponseDto toDtoMovie(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setRunTime(movie.getRunTime());
        dto.setIsClassic(movie.getIsClassic());
        dto.setGenre(movie.getGenre());
        dto.setPg13(movie.getPg13());
        return dto;
    }


}

