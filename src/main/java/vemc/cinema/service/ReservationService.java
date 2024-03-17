package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.*;
import vemc.cinema.entity.Reservation;
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

    private final TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.ticketService = ticketService;
    }

    /**
     * This method finds all reservations
     * @return list of ReservationDto objects
     */
    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();

        reservationRepository.save(reservation);

        return toDto(reservation);
    }

    /**
     * This method finds a reservation by id
     * @param id reservation id
     * @return ReservationDto object
     */
    public Optional<ReservationDto> findById(Long id) {
        return reservationRepository.findById(id).map(this::toDto);
    }

    /**
     * This method finds all tickets by reservation id
     * @param id reservation id
     * @return ReservationTicketDto object
     */
    public Optional<ReservationTicketDto> findTicketsByReservationId(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            return Optional.empty();
        }
        Reservation reservation = reservationOptional.get();
        return Optional.of(toDtoReservationTicket(reservation));
    }

    /**
     * This method finds a ticket by reservation id and ticket id
     * @param id reservation id
     * @param ticketId ticket id
     * @return ReservationTicketDto object
     */
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

    /**
     * This method is used to convert a Reservation object to a ReservationTicketDto object
     * @param reservation Reservation object
     * @return ReservationTicketDto object
     */
    public ReservationTicketDto toDtoReservationTicket(Reservation reservation) {
        ReservationTicketDto dto = new ReservationTicketDto();
        List<ReservationTicketHelperDto> ticketDtos = ticketService.toHelperDtoList(reservation.getTickets());
        dto.setTickets(ticketDtos);
        return dto;
    }

    /**
     * This method is used to convert a Reservation object to a ReservationDto object
     * @param reservation Reservation object
     * @return ReservationDto object
     */
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