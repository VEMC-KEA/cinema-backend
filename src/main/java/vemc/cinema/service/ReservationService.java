package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.TicketHelperDto;
import vemc.cinema.entity.Reservation;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.ReservationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;

    private final TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.ticketService = ticketService;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDto> findById(Long id) {
        return reservationRepository.findById(id).map(this::toDto);
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservationRepository.save(reservation);
        return toDto(reservation);
    }

    public Optional<ReservationTicketDto> findTicketsByReservationId(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            return Optional.empty();
        }
        Reservation reservation = reservationOptional.get();
        return Optional.of(toDtoReservationTicket(reservation));
    }

    public Optional<ReservationTicketDto> findOneTicketByReservationId(Long id, Long ticketId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            return Optional.empty();
        }
        Reservation reservation = reservationOptional.get();
        Ticket ticket = reservation.getTickets().stream()
                .filter(t -> t.getId().equals(ticketId))
                .findFirst()
                .orElse(null);
        if (ticket == null) {
            return Optional.empty();
        }
        ReservationTicketHelperDto ticketDto = ticketService.toHelperDto(ticket);
        ReservationTicketDto reservationTicketDto = new ReservationTicketDto();
        reservationTicketDto.setTickets(Collections.singletonList(ticketDto));
        return Optional.of(reservationTicketDto);
    }

    public ReservationDto createReservation(ReservationTicketHelperDto reservationDto) {
        double totalPrice = calculateTotalPrice(
                Collections.singletonList(reservationDto)
        );
        Reservation reservation = new Reservation();
        reservationRepository.save(reservation);
        return toDto(reservation);
    }

    private double calculateTotalPrice(List<ReservationTicketHelperDto> tickets) {
        double totalPrice = 0.0;
        for (ReservationTicketHelperDto ticketDto : tickets) {
            double ticketPrice = calculateTicketPrice(ticketDto);
            totalPrice += ticketPrice;
        }
        return totalPrice;
    }

    private double calculateTicketPrice(ReservationTicketHelperDto ticketDto) {

        String rowLetter = ticketDto.getSeat().getRow_letter();
        int seatNumber = ticketDto.getSeat().getNumber();
        boolean is3dMovie = ticketDto.getScreening().is3d();
        boolean isClassicMovie = ticketDto.getScreening().getMovie().getIsClassic();
        int movieRuntime = ticketDto.getScreening().getMovie().getRunTime();
        double basePrice = calculateBasePrice(isClassicMovie, movieRuntime);
        double seatDiscount = calculateSeatDiscount(rowLetter, seatNumber);
        double movieTypeSurcharge = calculateMovieTypeSurcharge(is3dMovie, movieRuntime);
        return basePrice - seatDiscount + movieTypeSurcharge;
    }

    private double calculateBasePrice(boolean isClassicMovie, int movieRuntime) {
        double basePrice = isClassicMovie ? 8.0 : 10.0;
        if (movieRuntime > 170) {
            basePrice += 2.0;
        }
        return basePrice;
    }

    private double calculateSeatDiscount(String rowLetter, int seatNumber) {
        double seatDiscount = 0.0;
        if (rowLetter.equals("A") && seatNumber <= 2) {
            seatDiscount = 2.0;
        }
        return seatDiscount;
    }

    private double calculateMovieTypeSurcharge(boolean is3dMovie, int movieRuntime) {
        double movieTypeSurcharge = 0.0;
        if (is3dMovie) {
            movieTypeSurcharge += 1.5;
        }
        if (movieRuntime > 170) {
            movieTypeSurcharge += 1.0;
        }
        return movieTypeSurcharge;
    }

    public ReservationTicketDto toDtoReservationTicket(Reservation reservation) {
        ReservationTicketDto dto = new ReservationTicketDto();
        List<ReservationTicketHelperDto> ticketDtos = ticketService.toHelperDtoList(reservation.getTickets());
        dto.setTickets(ticketDtos);
        return dto;
    }

    public ReservationDto toDto (Reservation reservation){
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setScreening(screeningService.toHelperDtoScreening(reservation.getScreening()));
        dto.setIsCompleted(reservation.isCompleted());
        dto.setTickets(ticketService.toHelperDtoList(reservation.getTickets()));
        return dto;
    }
}
