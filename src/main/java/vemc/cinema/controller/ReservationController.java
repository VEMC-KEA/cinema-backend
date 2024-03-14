package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.ScreeningDto;
import vemc.cinema.dto.helperdto.ReservationHelperDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.entity.Reservation;
import vemc.cinema.service.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        var reservations = this.reservationService.findAll();
        if (reservations != null) {
            return ResponseEntity.ok(reservations);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservationDto>> getAllReservationsById(@PathVariable Long id ) {
        var reservation = this.reservationService.findById(id);
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<Optional<ReservationTicketDto>> getAllTicketsByReservationId(@PathVariable Long id ) {
        var tickets = this.reservationService.findTicketsByReservationId(id);
        if (tickets.isPresent()) {
            return ResponseEntity.ok(tickets);
        }
        return ResponseEntity.noContent().build();
    }


}
