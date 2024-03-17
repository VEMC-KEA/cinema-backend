package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.*;
import vemc.cinema.dto.helperdto.*;
import vemc.cinema.entity.*;
import vemc.cinema.repository.ScreeningRepository;
import vemc.cinema.repository.TicketRepository;
import vemc.cinema.utils.PriceCalculator;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final TicketRepository ticketRepository;

    private final HallService hallService;

    public ScreeningService(ScreeningRepository screeningRepository, CinemaService cinemaService, MovieService movieService, TicketRepository ticketRepository, HallService hallService){
        this.screeningRepository = screeningRepository;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.ticketRepository = ticketRepository;
        this.hallService = hallService;
    }

    public List<ScreeningDto> findAll() {
        return screeningRepository.findAll().stream().map(this::toDto).toList();
    }

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
        hall.setId(screeningDto.getHall().get(0).getId());
        screening.setHall(hall);
        screening.setDate(screeningDto.getDate());
        screening.setTime(screeningDto.getTime());
        screeningRepository.save(screening);
        return toDto(screening);
    }


    public ScreeningDto findById(Long id) {
        return screeningRepository.findById(id).map(this::toDto).orElse(null);
    }

    public ScreeningDto updateScreening(Long id, ScreeningDto screeningDto) {
        Screening screening = screeningRepository.findById(id).orElse(null);
        if(screening != null){
            screening.set3d(screeningDto.is3d());
            Cinema cinema = new Cinema();
            cinema.setId(screeningDto.getCinema().getId());
            screening.setCinema(cinema);
            Movie movie = new Movie();
            movie.setId(screeningDto.getMovie().getId());
            screening.setMovie(movie);
            Hall hall = new Hall();
            hall.setId(screeningDto.getHall().get(0).getId());
            screening.setHall(hall);
            screening.setDate(screeningDto.getDate());
            screening.setTime(screeningDto.getTime());
            screeningRepository.save(screening);
            return toDto(screening);
        }
        return null;
    }

    public void deleteScreening(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found with id: " + id));
        screeningRepository.delete(screening);
    }

    public ReservationScreeningHelperDto toHelperDtoScreening(Screening screening) {
        ReservationScreeningHelperDto dto = new ReservationScreeningHelperDto();
        dto.set3d(screening.is3d());
        dto.setCinema(cinemaService.toHelperDto(screening.getCinema()));
        dto.setMovie(movieService.toHelperDto(screening.getMovie()));
        dto.setHall(hallService.toHelperDto(screening.getHall()));
        dto.setDate(screening.getDate());
        dto.setTime(screening.getTime());
        return dto;
    }

    public ScreeningDto toDto(Screening screening) {
        ScreeningDto dto = new ScreeningDto();
        dto.setId(screening.getId());
        dto.set3d(screening.is3d());

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
        HallHelperDto hallDto = new HallHelperDto();
        hallDto.setId(hall.getId());
        hallDto.setNumber(hall.getNumber());
        dto.setHall(List.of(hallDto));

        List<TicketHelperDto> ticketDtos = screening.getTickets().stream().map(ticket -> {
            TicketHelperDto ticketDto = new TicketHelperDto();
            ticketDto.setId(ticket.getId());
            ticketDto.setSeat(ticket.getSeat());
            ticketDto.setPrice(ticket.getPrice());
            return ticketDto;
        }).toList();
        dto.setTickets(ticketDtos);

        return dto;
    }

    public ScreeningDto convertToScreeningDto(ReservationScreeningHelperDto reservationScreeningHelperDto) {
        ScreeningDto screeningDto = new ScreeningDto();
        screeningDto.set3d(reservationScreeningHelperDto.is3d());
        screeningDto.setCinema(reservationScreeningHelperDto.getCinema());
        screeningDto.setMovie(reservationScreeningHelperDto.getMovie());
        screeningDto.setHall(reservationScreeningHelperDto.getHall());
        screeningDto.setDate(reservationScreeningHelperDto.getDate());
        screeningDto.setTime(reservationScreeningHelperDto.getTime());
        return screeningDto;
    }

    public Screening findScreeningById(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening not found with id: " + id));
    }

    public Screening convertToScreening(ScreeningDto screeningDto) {
        Screening screening = new Screening();
        screening.set3d(screeningDto.is3d());

        Cinema cinema = new Cinema();
        cinema.setId(screeningDto.getCinema().getId());
        screening.setCinema(cinema);

        Movie movie = new Movie();
        movie.setId(screeningDto.getMovie().getId());
        screening.setMovie(movie);

        Hall hall = new Hall();
        hall.setId(screeningDto.getHall().get(0).getId());
        screening.setHall(hall);

        screening.setDate(screeningDto.getDate());
        screening.setTime(screeningDto.getTime());

        return screening;
    }




}