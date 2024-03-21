package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.*;
import vemc.cinema.dto.helperdto.*;
import vemc.cinema.entity.*;
import vemc.cinema.repository.ScreeningRepository;
import vemc.cinema.utils.PriceCalculator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final HallService hallService;

    public ScreeningService(ScreeningRepository screeningRepository, CinemaService cinemaService, MovieService movieService, HallService hallService){
        this.screeningRepository = screeningRepository;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    /**
     * This method is used to convert a Screening object to a ScreeningDto object
     * @return ScreeningDto object
     */
    public List<ScreeningDto> findAll() {
        return screeningRepository.findAll().stream().map(this::toDto).toList();
    }

    public List<ScreeningDto> findMovieById(Long movieId) {
        return screeningRepository.findAllByMovieId(movieId).stream().map(this::toDto).toList();
    }

    public List<ScreeningDto> findAllByCinemaIdAndMovieId(Long cinemaId, Long movieId) {
        return screeningRepository.findAllByCinemaIdAndMovieId(cinemaId, movieId).stream().map(this::toDto).toList();
    }

    /**
     * This method is used to convert a Screening object to a ScreeningDto object
     * @param screeningDto ScreeningDto object
     * @return ScreeningDto object
     */
    public ScreeningDto createScreening(ScreeningDto screeningDto) {
        Screening screening = new Screening();
        screening.set3d(screeningDto.is3d());
        Cinema cinema = new Cinema();
        cinema.setId(screeningDto.getCinema().getId());
        screening.setCinema(cinema);
        Movie movie = new Movie();
        movie.setId(screeningDto.getMovie().getId());
        screening.setMovie(movie);
        Hall hall = new Hall();
        hall.setId(screeningDto.getHall().getId());
        screening.setHall(hall);
        screening.setDate(screeningDto.getDate());
        screening.setTime(screeningDto.getTime());
        screeningRepository.save(screening);
        return toDto(screening);
    }

    public void addReservation(Reservation reservation, Long screeningId){
        var screeningToUpdate = screeningRepository.findById(screeningId);
        if(screeningToUpdate.isPresent()){
            var screening = screeningToUpdate.get();
            screening.getReservations().add(reservation);
            screeningRepository.save(screening);
        }
    }

    /**
     * This method is used to convert a Screening object to a ScreeningDto object
     * @param id screening id
     * @return ScreeningDto object
     */
    public ScreeningDto findById(Long id) {
        return screeningRepository.findById(id).map(this::toDto).orElse(null);
    }

    /**
     * This method is used to convert a Screening object to a ScreeningDto object
     * @param id screening id
     * @return ScreeningDto object
     */
    public ScreeningDto cancelScreening(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found with id: " + id));
        screening.setIsCancelled(true);
        Screening updatedScreening = screeningRepository.save(screening);
        return toDto(updatedScreening);
    }

//    public void removeReservation(Reservation reservation, Long screeningId){
//        var screeningToUpdate = screeningRepository.findById(screeningId);
//        if(screeningToUpdate.isPresent()){
//            var screening = screeningToUpdate.get();
//            screening.getReservations().remove(reservation);
//            screeningRepository.save(screening);
//        }
//    }

    public void removeReservation(Reservation reservation){
        Screening screening = reservation.getScreening();
        screening.getReservations().remove(reservation);
        screeningRepository.save(screening);
    }

    /**
     * This method is used to convert a Screening object to a ScreeningDto object
     * @param screening Screening object
     * @return ScreeningDto object
     */
    public ReservationScreeningHelperDto toHelperDtoScreening(Screening screening) {
        ReservationScreeningHelperDto dto = new ReservationScreeningHelperDto();
        dto.set3d(screening.is3d());
        dto.setId(screening.getId());
        dto.setCinema(cinemaService.toHelperDto(screening.getCinema()));
        dto.setMovie(movieService.toHelperDto(screening.getMovie()));
        dto.setHall(hallService.toHelperDto(screening.getHall()));
        dto.setDate(screening.getDate());
        dto.setTime(screening.getTime());
        return dto;
    }

    /**
     * This method is used to convert a Screening object to a ScreeningDto object
     * @param screening Screening object
     * @return ScreeningDto object
     */
    public ScreeningDto toDto(Screening screening) {
        ScreeningDto dto = new ScreeningDto();
        dto.setId(screening.getId());
        dto.set3d(screening.is3d());
        dto.setIsCancelled(screening.getIsCancelled());
        dto.setTime(screening.getTime());
        dto.setDate(screening.getDate());

        CinemaHelperDto cinemaDto = new CinemaHelperDto();
        cinemaDto.setId(screening.getCinema().getId());
        cinemaDto.setName(screening.getCinema().getName());

        dto.setCinema(cinemaDto);

        Movie movie = screening.getMovie();
        MovieHelperDto movieDto = new MovieHelperDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        dto.setMovie(movieDto);

        Hall hall = screening.getHall();
        HallDto hallDto = new HallDto();
        hallDto.setId(hall.getId());
        hallDto.setNumber(hall.getNumber());
        hallDto.setSeats(hall.getSeat());
        dto.setHall(hallDto);

        var reservationDtos = new ArrayList<ReservationTicketDto>();

        for(var reservation : screening.getReservations()){
            var reservationDto = new ReservationTicketDto();
            var ticketDtos = new ArrayList<ReservationTicketHelperDto>();
            for(var ticket : reservation.getTickets()){

                LocalDateTime now = LocalDateTime.now();

                Duration duration = Duration.between(reservation.getReservationTime(), now);

                if (duration.toMinutes() < 15 || reservation.isCompleted()) {
                    var ticketDto = new ReservationTicketHelperDto();
                    ticketDto.setId(ticket.getId());
                    ticketDto.setPrice(ticket.getPrice());
                    ticketDto.setRowLetter(ticket.getSeat().getRowLetter());
                    ticketDto.setNumber(ticket.getSeat().getNumber());
                    ticketDto.setSeatId(ticket.getSeat().getId());
                    ticketDtos.add(ticketDto);
                }
            }
            reservationDto.setTickets(ticketDtos);
            reservationDtos.add(reservationDto);
        }

        dto.setReservations(reservationDtos);

        return dto;
    }

    public Screening findByIdScreeningDto(Long id) {
        return screeningRepository.findById(id).orElse(null);
    }
}