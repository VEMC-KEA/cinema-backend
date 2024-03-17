package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.ScreeningDto;
import vemc.cinema.dto.helperdto.*;
import vemc.cinema.entity.Reservation;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Seat;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.ReservationRepository;
import vemc.cinema.utils.PriceCalculator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;
    private final SeatService seatService;

    private final TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService, SeatService seatService, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.seatService = seatService;
        this.ticketService = ticketService;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setCompleted(false);

        ReservationScreeningHelperDto reservationScreeningHelperDto = reservationDto.getScreening();
        ScreeningDto screeningDto = screeningService.convertToScreeningDto(reservationScreeningHelperDto);
        Screening screening = screeningService.findScreeningById(screeningDto.getId());
        reservation.setScreening(screening);

        for (ReservationTicketHelperDto ticketDto : reservationDto.getTickets()) {

            Ticket ticket = new Ticket();

            SeatHelperDto seatHelperDto = new SeatHelperDto();
            seatHelperDto.setRow_letter(ticketDto.getRow_letter());
            seatHelperDto.setNumber(ticketDto.getNumber());

            Seat seat = seatService.createSeat(seatHelperDto);

            PriceCalculator.calculatePrice(ticket, screening, seat);

            ticket.setPrice(ticketDto.getPrice());

            reservation.getTickets().add(ticket);
        }


        double totalPrice = PriceCalculator.calculateTotalPrice(reservation);
        reservation.setTotalPrice(totalPrice);

        Reservation savedReservation = reservationRepository.save(reservation);

        return toDto(savedReservation);
    }

    public Optional<ReservationDto> findById(Long id) {
        return reservationRepository.findById(id).map(this::toDto);
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

    public ReservationTicketDto toDtoReservationTicket(Reservation reservation) {
        ReservationTicketDto dto = new ReservationTicketDto();
        List<ReservationTicketHelperDto> ticketDtos = ticketService.toHelperDtoList(reservation.getTickets());
        dto.setTickets(ticketDtos);
        return dto;
    }

    public ReservationDto toDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setScreening(screeningService.toHelperDtoScreening(reservation.getScreening()));
        dto.setIsCompleted(reservation.isCompleted());
        dto.setTickets(ticketService.toHelperDtoList(reservation.getTickets()));

        double totalTicketPrice = PriceCalculator.calculateTotalPrice(reservation);
        dto.setTotalPrice(totalTicketPrice);

        return dto;
    }
}
