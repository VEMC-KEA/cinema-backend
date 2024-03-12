package vemc.cinema.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import vemc.cinema.entity.*;
import vemc.cinema.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void run(ApplicationArguments args) {
        generateCinemas();
    }

    private void generateCinemas() {

        // Create Tickets
        List<Ticket> tickets = new ArrayList<>();

        Ticket ticket1 = new Ticket();
        ticket1.setScreening(screeningRepository.findById(1L).orElse(null));
        ticket1.setSeat(seatRepository.findById(1L).orElse(null));
        ticket1.setCompleted(false);
        ticket1.setPrice(10.0);
        ticketRepository.save(ticket1);
        tickets.add(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setScreening(screeningRepository.findById(2L).orElse(null));
        ticket2.setSeat(seatRepository.findById(2L).orElse(null));
        ticket2.setCompleted(false);
        ticket2.setPrice(12.0);
        ticketRepository.save(ticket2);
        tickets.add(ticket2);

        // Create Screenings
        Screening screening1 = new Screening();
        screening1.set3d(true);
        screening1.setMovie(movieRepository.findById(1L).orElse(null));
        screening1.setHall(hallRepository.findById(1L).orElse(null));
        screening1.setDatetime(LocalDate.of(2024, 3, 12));
        screening1.setTickets(tickets);
        screeningRepository.save(screening1);

        Screening screening2 = new Screening();
        screening2.set3d(false);
        screening2.setMovie(movieRepository.findById(2L).orElse(null));
        screening2.setHall(hallRepository.findById(2L).orElse(null));
        screening2.setDatetime(LocalDate.of(2024, 3, 12));
        screeningRepository.save(screening2);

        // Create seats
        Seat seat1 = new Seat();
        seat1.setRowNum(1);
        seat1.setNumber(1);
        seatRepository.save(seat1);

        Seat seat2 = new Seat();
        seat1.setRowNum(2);
        seat1.setNumber(2);
        seatRepository.save(seat2);

        // Create and save movies
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

        // Create and save halls
        List<Hall> halls = new ArrayList<>();

        Hall hall1 = new Hall();
        hall1.setName("Hall 1");
        hall1.setAmountOfFrontRowDiscounted(0.5);
        hall1.setSeat(seat1);
        hall1.setScreening(screening1);
        hallRepository.save(hall1);
        halls.add(hall1);

        Hall hall2 = new Hall();
        hall2.setName("Hall 2");
        hall2.setAmountOfFrontRowDiscounted(0.5);
        hall2.setSeat(seat2);
        hall2.setScreening(screening2);
        hallRepository.save(hall2);
        halls.add(hall2);

        // Create Cinemas
        Cinema cinema1 = new Cinema();
        cinema1.setName("Kino 1");
        cinema1.setReservationFee(10);
        cinema1.setGroupDiscount(0.7);
        cinema1.setMovieBasePrice(130);
        cinema1.setHall(halls);
        cinema1.getMovies().add(movie1);

        Cinema cinema2 = new Cinema();
        cinema2.setName("Kino 2");
        cinema2.setReservationFee(10);
        cinema2.setGroupDiscount(0.7);
        cinema2.setMovieBasePrice(130);
        cinema2.getMovies().add(movie2);

        cinemaRepository.save(cinema1);
        cinemaRepository.save(cinema2);
    }
}
