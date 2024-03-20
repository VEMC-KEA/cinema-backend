package vemc.cinema.service;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.*;
import vemc.cinema.entity.Reservation;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Seat;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.ReservationRepository;
import vemc.cinema.utils.PriceCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;
    private final TicketService ticketService;
    private final SeatService seatService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService, TicketService ticketService, SeatService seatService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.ticketService = ticketService;
        this.seatService = seatService;
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

    /**
     * creates a reservation on a screening id
     * @param postReservationDto object
     * @return ReservationDto object
     */

    public ReservationDto createReservation(PostReservationDto postReservationDto) {
        var reservation = new Reservation();
        var screening = screeningService.findByIdScreeningDto(postReservationDto.getScreeningId());
        reservation.setScreening(screening);
        var savedReservation = reservationRepository.save(reservation);
        screeningService.addReservation(savedReservation, screening.getId());
        return toDto(savedReservation);
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
     * This method is used to complete a reservation
     * @param id reservation id
     * @return ReservationDto object
     */
    public ReservationDto completeReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            return null;
        }
        Reservation reservation = reservationOptional.get();
        reservation.setCompleted(true);
        reservationRepository.save(reservation);
        return toDto(reservation);
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

    /**
     * This method is used to add tickets to a reservation
     * @param id reservation id
     * @param postTicketDto PostTicketDto object
     * @return ReservationDto object
     */
    public ReservationDto postTicketByReservationId(Long id, PostTicketDto postTicketDto) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            Long screeningId = reservation.getScreening().getId();
            setTickets(postTicketDto, reservation, screeningId);
            reservation = reservationRepository.save(reservation);
            return toDto(reservation);
        }
        return null;
    }

    /**
     * This method is used to update tickets by reservation id
     * @param id reservation id
     * @param postTicketDto PostTicketDto object
     * @return ReservationDto object
     */
    public ReservationDto updateTicketsByReservationId(Long id, PostTicketDto postTicketDto) {
        Optional<Reservation> reservationToUpdate = reservationRepository.findById(id);
        if (reservationToUpdate.isPresent()) {
            Reservation reservation = reservationToUpdate.get();
            Long screeningId = reservation.getScreening().getId();
            var tickets = new ArrayList<Ticket>();
            for(var seatId : postTicketDto.getSeatIds()){
                var newTicket = new Ticket();
                var seatOptional = seatService.findById(seatId);
                if(seatOptional.isPresent()){
                    var seat = seatOptional.get();
                    newTicket.setSeat(seat);
                    var savedTicket = ticketService.saveTicket(newTicket, screeningId);
                    tickets.add(savedTicket);
                }
            }
            reservation.setTickets(tickets);
            return toDto(reservationRepository.save(reservation));
        }
        return null;
    }

    /**
     * This method is used to set tickets
     * @param postTicketDto PostTicketDto object
     * @param reservation Reservation object
     * @param screeningId screening id
     */
    private void setTickets(PostTicketDto postTicketDto, Reservation reservation, Long screeningId) {
        for (Long seatId: postTicketDto.getSeatIds()) {
            Optional<Seat> seat = seatService.findById(seatId);
            if (seat.isPresent()) {
                var ticket = new Ticket();
                ticket.setSeat(seat.get());
                ticket.setReservation(reservation);
                ticket.setScreening(reservation.getScreening());
                ticket = ticketService.saveTicket(ticket, screeningId);
                reservation.getTickets().add(ticket);
            }
        }
    }

    /**
     * This method is used to delete a ticket by reservation id and ticket id
     * @param reservationId reservation id
     * @param ticketsId ticket id
     * @return ReservationDto object
     */
    public ReservationDto deleteTicketByReservationId(Long reservationId, Long ticketsId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            List<Ticket> tickets = reservation.getTickets();
            tickets.removeIf(t -> t.getId().equals(ticketsId));
            reservation.setTickets(tickets);
            reservationRepository.save(reservation);
            ticketService.deleteById(ticketsId);
            return toDto(reservation);
        }
        return null;
    }
}