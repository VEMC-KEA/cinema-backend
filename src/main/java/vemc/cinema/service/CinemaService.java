package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.*;
import vemc.cinema.dto.helperdto.CinemaHelperDto;
import vemc.cinema.dto.helperdto.HallHelperDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;
import vemc.cinema.entity.*;
import vemc.cinema.repository.CinemaRepository;
import vemc.cinema.repository.HallRepository;
import vemc.cinema.repository.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public CinemaService(CinemaRepository cinemaRepository, MovieRepository movieRepository, HallRepository hallRepository) {
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }

    public List<CinemaDto> findAll() {
        return cinemaRepository.findAll().stream().map(this::toDtoCinema).toList();
    }

    public CinemaDto createCinema(CinemaDto cinemaDto) {
        Cinema cinema = new Cinema();
        cinema.setName(cinemaDto.getName());
        cinema.setImageUrl(cinemaDto.getImageUrl());
        cinema.setReservationFee(cinemaDto.getReservationFee());
        cinema.setGroupDiscount(cinemaDto.getGroupDiscount());
        cinema.setMovieBasePrice(cinemaDto.getMovieBasePrice());

        List<Movie> movies = new ArrayList<>();
        for (MovieHelperDto movieDto : cinemaDto.getMovies()) {
            Optional<Movie> movieOptional = movieRepository.findById(movieDto.getId());
            movieOptional.ifPresent(movies::add);
        }
        cinema.setMovies(movies);

        List<Hall> halls = new ArrayList<>();
        for (HallHelperDto hallDto : cinemaDto.getHall()) {
            Optional<Hall> hallOptional = hallRepository.findById(hallDto.getId());
            hallOptional.ifPresent(halls::add);
        }
        cinema.setHall(halls);

        Cinema savedCinema = cinemaRepository.save(cinema);

        return toDtoCinema(savedCinema);
    }

    public CinemaDto findById(Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        return cinemaOptional.map(this::toDtoCinema).orElse(null);
    }

    public List<HallDto> getHallsByCinemaId(Long cinemaId) {
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

    public CinemaDto updateCinema(Long id, CinemaDto cinemaDto) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);

        if (cinemaOptional.isEmpty()) {
            return null;
        }

        Cinema cinema = cinemaOptional.get();
        cinema.setName(cinemaDto.getName());
        cinema.setImageUrl(cinemaDto.getImageUrl());
        cinema.setReservationFee(cinemaDto.getReservationFee());
        cinema.setGroupDiscount(cinemaDto.getGroupDiscount());
        cinema.setMovieBasePrice(cinemaDto.getMovieBasePrice());

        List<Movie> movies = new ArrayList<>();
        for (MovieHelperDto movieDto : cinemaDto.getMovies()) {
            Optional<Movie> movieOptional = movieRepository.findById(movieDto.getId());
            movieOptional.ifPresent(movies::add);
        }
        cinema.setMovies(movies);

        List<Hall> halls = new ArrayList<>();
        for (HallHelperDto hallDto : cinemaDto.getHall()) {
            Optional<Hall> hallOptional = hallRepository.findById(hallDto.getId());
            hallOptional.ifPresent(halls::add);
        }
        cinema.setHall(halls);
        Cinema updatedCinema = cinemaRepository.save(cinema);

        return toDtoCinema(updatedCinema);
    }

    public void deleteCinema(Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);

        if (cinemaOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            cinemaRepository.delete(cinema);
        } else {
            throw new RuntimeException("Cinema with ID " + id + " not found");
        }
    }

    public HallDto createHall(Long cinemaId, HallDto hallDto) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + cinemaId));

        Hall hall = new Hall();
        hall.setNumber(hallDto.getNumber());
        hall.setAmountOfFrontRowDiscounted(hallDto.getAmountOfFrontRowDiscounted());

        Hall savedHall = hallRepository.save(hall);

        cinema.getHall().add(savedHall);
        cinemaRepository.save(cinema);

        return toDtoHall(savedHall);
    }

    public HallDto getHallsByIdByCinemaId(Long cinemaId, Long hallId) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if(cinemaOptional.isEmpty()) {
            return null;
        }

        Cinema cinema = cinemaOptional.get();
        Optional<Hall> hallOptional = cinema.getHall().stream().filter(hall -> hall.getId().equals(hallId)).findFirst();
        return hallOptional.map(this::toDtoHall).orElse(null);
    }

    public List<SeatDto> getSeatsByHallsIdByCinemaId(Long cinemaId, Long hallId) {
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

    public SeatDto getSeatByIdByHallsIdByCinemaId(Long cinemaId, Long hallId, Long seatId) {
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

    public List<MovieDto> getMovieByCinemaId (Long id) {
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

    private CinemaDto toDtoCinema(Cinema cinema) {
        CinemaDto dto = new CinemaDto();
        dto.setId(cinema.getId());
        dto.setImageUrl(cinema.getImageUrl());
        dto.setName(cinema.getName());

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

    private HallDto toDtoHall(Hall hall) {
        HallDto dto = new HallDto();
        dto.setId(hall.getId());
        dto.setNumber(hall.getNumber());
        dto.setAmountOfFrontRowDiscounted(hall.getAmountOfFrontRowDiscounted());
        dto.setSeats(hall.getSeat());
        return dto;
    }

    private SeatDto toDtoSeat(Seat seat) {
        SeatDto dto = new SeatDto();
        dto.setId(seat.getId());
        dto.setNumber(seat.getNumber());
        dto.setRow_letter(seat.getRow_letter());
        return dto;
    }

    private MovieDto toDtoMovie(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setRunTime(movie.getRunTime());
        dto.setIsClassic(movie.getIsClassic());
        dto.setGenre(movie.getGenre());
        dto.setPg13(movie.getPg13());
        return dto;
    }

    public CinemaHelperDto toHelperDto(Cinema cinema) {
        CinemaHelperDto dto = new CinemaHelperDto();
        dto.setId(cinema.getId());
        dto.setName(cinema.getName());
        return dto;
    }
}

