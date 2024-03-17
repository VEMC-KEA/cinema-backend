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

    /**
     * This method returns all cinemas in the database
     * @return a list of all cinemas in the database
     */
    public List<CinemaDto> findAll() {
        return cinemaRepository.findAll().stream().map(this::toDtoCinema).toList();
    }

    /**
     * This method creates a new cinema in the database
     * @param cinemaDto the cinema to be created
     * @return the created cinema
     */
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

    /**
     * This method returns a cinema by its id
     * @param id the id of the cinema
     * @return the cinema with the given id
     */
    public CinemaDto findById(Long id) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        return cinemaOptional.map(this::toDtoCinema).orElse(null);
    }

    /**
     * This method returns all halls in a cinema by the cinema's id
     * @param cinemaId the id of the cinema
     * @return a list of all halls in the cinema
     */
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

    /**
     * This method updates a cinema by its id
     * @param id the id of the cinema
     * @param cinemaDto the updated cinema
     * @return the updated cinema
     */
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

    /**
     * This method deletes a cinema by its id
     * @param id the id of the cinema
     */
    public void deleteCinema(Long id) {
        //error with foreign key constraint when deleting hall
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);

        if (cinemaOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            cinemaRepository.delete(cinema);
        } else {
            throw new RuntimeException("Cinema with ID " + id + " not found");
        }
    }

    /**
     * This method returns a hall by its id and the cinema's id
     * @param cinemaId the id of the cinema
     * @param hallId the id of the hall
     * @return the cinema with the given id
     */
    public HallDto getHallsByIdByCinemaId(Long cinemaId, Long hallId) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if(cinemaOptional.isEmpty()) {
            return null;
        }

        Cinema cinema = cinemaOptional.get();
        Optional<Hall> hallOptional = cinema.getHall().stream().filter(hall -> hall.getId().equals(hallId)).findFirst();
        return hallOptional.map(this::toDtoHall).orElse(null);
    }

    /**
     * This method creates a new hall in the database
     * @param cinemaId the id of the cinema
     * @param hallDto the hall to be created
     * @return the created hall
     */
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

    /**
     * This method returns all seats in a hall by the cinema's id and the hall's id
     * @param cinemaId the id of the cinema
     * @param hallId the id of the hall
     * @return a list of all seats in the hall
     */
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

    /**
     * This method updates a hall by its id and the cinema's id
     * @param cinemaId the id of the cinema
     * @param hallId the id of the hall
     * @param hallDto the updated hall
     * @return the updated hall
     */
    public HallDto updateHall(Long cinemaId, Long hallId, HallDto hallDto) {
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new NoSuchElementException("Cinema not found with id: " + cinemaId));

        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall not found with id: " + hallId));

        hall.setNumber(hallDto.getNumber());
        hall.setAmountOfFrontRowDiscounted(hallDto.getAmountOfFrontRowDiscounted());

        Hall updatedHall = hallRepository.save(hall);

        return toDtoHall(updatedHall);
    }

    /**
     * This method deletes a hall by its id and the cinema's id
     * @param cinemaId the id of the cinema
     * @param hallId the id of the hall
     */
    public void deleteHall(Long cinemaId, Long hallId) {
        //error with foreign key constraint when deleting hall
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new NoSuchElementException("Cinema not found with id: " + cinemaId));

        Hall hall = cinema.getHall().stream()
                .filter(h -> h.getId().equals(hallId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Hall not found with id: " + hallId + " in cinema with id: " + cinemaId));

        cinema.getHall().remove(hall);

        hallRepository.delete(hall);
    }

    /**
     * This method creates a new seat in the database
     * @param cinemaId the id of the cinema
     * @param hallId the id of the hall
     * @param seatId the id of the seat
     * @return the created seat
     */
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

    /**
     * This method gets a movie by the cinema's id
     * @param id the id of the cinema
     * @return a list of all movies in the cinema
     */
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

    /**
     * This method toDtoCinema converts a cinema to a cinemaDto
     * @param cinema the cinema to be converted
     * @return the converted cinema
     */
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

    /**
     * This method toDtoHall converts a hall to a hallDto
     * @param hall the hall to be converted
     * @return the converted hall
     */
    private HallDto toDtoHall(Hall hall) {
        HallDto dto = new HallDto();
        dto.setId(hall.getId());
        dto.setNumber(hall.getNumber());
        dto.setAmountOfFrontRowDiscounted(hall.getAmountOfFrontRowDiscounted());
        dto.setSeats(hall.getSeat());
        return dto;
    }

    /**
     * This method toDtoSeat converts a seat to a seatDto
     * @param seat the seat to be converted
     * @return the converted seat
     */
    private SeatDto toDtoSeat(Seat seat) {
        SeatDto dto = new SeatDto();
        dto.setId(seat.getId());
        dto.setNumber(seat.getNumber());
        dto.setRow_letter(seat.getRowLetter());
        return dto;
    }

    /**
     * This method toDtoMovie converts a movie to a movieDto
     * @param movie the movie to be converted
     * @return the converted movie
     */
    private MovieDto toDtoMovie(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setRunTime(movie.getRunTime());
        dto.setIsClassic(movie.getIsClassic());
        dto.setGenre(movie.getGenre());
        dto.setPg13(movie.getPg13());
        return dto;
    }

    /**
     * This method toHelperDto converts a cinema to a cinemaHelperDto
     * @param cinema the cinema to be converted
     * @return the converted cinema
     */
    public CinemaHelperDto toHelperDto(Cinema cinema) {
        CinemaHelperDto dto = new CinemaHelperDto();
        dto.setId(cinema.getId());
        dto.setName(cinema.getName());
        return dto;
    }
}