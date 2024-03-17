package vemc.cinema.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.entity.Reservation;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;

    private final HallService hallService;

    private final TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService, HallService hallService, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.hallService = hallService;
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

    @Transactional
    public Optional<ReservationDto> deleteById(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            return Optional.empty();
        }
        Reservation reservation = reservationOptional.get();
        // Create a copy of the tickets list to avoid ConcurrentModificationException
        List<Ticket> tickets = new ArrayList<>(reservation.getTickets());
        // Delete the tickets, halls and screenings associated with the reservation
        for (Ticket ticket : tickets) {
            // Remove the ticket from the reservation's tickets list
            reservation.getTickets().remove(ticket);
            // Save and immediately flush the changes to the database
            reservationRepository.saveAndFlush(reservation);
            // Delete the Ticket entity
            ticketService.deleteById(ticket.getId());
            // Delete the Hall entities associated with the Screening of the ticket
            hallService.deleteByScreeningId(ticket.getScreening().getId());
            // Delete the Screening entity associated with the ticket
            screeningService.deleteByTicketId(ticket.getId());
        }
        // Now delete the reservation
        reservationRepository.deleteById(id);
        return Optional.of(toDto(reservation));
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
