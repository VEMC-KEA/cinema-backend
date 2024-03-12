package vemc.cinema.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import vemc.cinema.entity.*;
import vemc.cinema.repository.*;

import java.time.LocalDate;

@Component
public class SetupCinema implements ApplicationRunner {
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final TicketRepository ticketRepository;
    private final HallRepository hallRepository;

    public SetupCinema(CinemaRepository cinemaRepository, MovieRepository movieRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository, TicketRepository ticketRepository, HallRepository hallRepository) {
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.ticketRepository = ticketRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public void run(ApplicationArguments args)  {
        generateCinemas();
    }

    private void generateCinemas() {
        // Create Cinemas
        Cinema cinema1 = new Cinema();
        cinema1.setName("Kino 1");
        cinema1.setReservationFee(10);
        cinema1.setGroupDiscount(0.7);
        cinema1.setMovieBasePrice(130);

        Cinema cinema2 = new Cinema();
        cinema2.setName("Kino 2");
        cinema2.setReservationFee(10);
        cinema2.setGroupDiscount(0.7);
        cinema2.setMovieBasePrice(130);

        cinemaRepository.save(cinema1);
        cinemaRepository.save(cinema2);

        // Create Movies
        Movie movie1 = new Movie();
        movie1.setRunTime(120);
        movie1.setIsClassic(true);
        movie1.setGenre("Action");
        movie1.setPg13(true);
        movieRepository.save(movie1);

        Movie movie2 = new Movie();
        movie2.setRunTime(150);
        movie2.setIsClassic(false);
        movie2.setGenre("Drama");
        movie2.setPg13(false);
        movieRepository.save(movie2);

        // Create Seats
        Seat seat1 = new Seat();
        seat1.setRowNum(1);
        seat1.setNumber(1);
        seatRepository.save(seat1);

        Seat seat2 = new Seat();
        seat2.setRowNum(1);
        seat2.setNumber(2);
        seatRepository.save(seat2);

        // Create Halls
        Hall hall1 = new Hall();
        hall1.setName("Hall 1");
        hall1.setAmountOfFrontRowDiscounted(0.5);
        hall1.setSeat(seat1);
        hall1 = hallRepository.save(hall1);

        Hall hall2 = new Hall();
        hall2.setName("Hall 2");
        hall2.setAmountOfFrontRowDiscounted(0.5);
        hall2.setSeat(seat2);
        hall2 = hallRepository.save(hall2);

        // Create Shows
        Screening screening1 = new Screening();
        screening1.set3d(true);
        screening1.setMovie(movie1);
        screening1.setHall(hall1);
        screening1.setDatetime(LocalDate.of(2012, 12, 12));
        screening1 = screeningRepository.save(screening1);

        Screening screening2 = new Screening();
        screening2.set3d(false);
        screening2.setMovie(movie2);
        screening2.setHall(hall2);
        screening2.setDatetime(LocalDate.of(2012, 12, 12));
        screening2 = screeningRepository.save(screening2);

        // Create Tickets
        Ticket ticket1 = new Ticket();
        ticket1.setScreening(screening1);
        ticket1.setSeat(seat1);
        ticket1.setCompleted(false);
        ticket1.setPrice(10.0);
        ticketRepository.save(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setScreening(screening2);
        ticket2.setSeat(seat2);
        ticket2.setCompleted(false);
        ticket2.setPrice(12.0);
        ticketRepository.save(ticket2);
    }
}
