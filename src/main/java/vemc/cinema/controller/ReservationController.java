package vemc.cinema.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.service.ReservationService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://cinema-frontend-nine.vercel.app/")
@RestController
@RequestMapping("reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Get all reservations
     * @return List of all reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        var reservations = this.reservationService.findAll();
        if (reservations != null) {
            return ResponseEntity.ok(reservations);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Create a new reservation
     * @param reservationDto Reservation to create
     * @return Created reservation
     */
     @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationDto createdReservation = reservationService.createReservation(reservationDto);
        if (createdReservation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get reservation by id
     * @param id Id of reservation
     * @return Reservation with given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservationDto>> getAllReservationsById(@PathVariable Long id ) {
        var reservation = this.reservationService.findById(id);
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all tickets by reservation id
     * @param id Id of reservation
     * @return List of tickets with given reservation id
     */
    @GetMapping("/{id}/tickets")
    public ResponseEntity<Optional<ReservationTicketDto>> getAllTicketsByReservationId(@PathVariable Long id ) {
        var tickets = this.reservationService.findTicketsByReservationId(id);
        if (tickets.isPresent()) {
            return ResponseEntity.ok(tickets);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all tickets by reservation id and tickets id
     * @param reservationId Id of reservation
     * @param ticketsId Id of tickets
     * @return List of tickets with given reservation id and tickets id
     */
    @GetMapping("/{reservationId}/tickets/{ticketsId}")
    public ResponseEntity<Optional<ReservationTicketDto>> getAllTicketsByReservationId(@PathVariable Long reservationId, @PathVariable Long ticketsId){
        var ticket = this.reservationService.findOneTicketByReservationId(reservationId, ticketsId);
        if(ticket.isPresent()){
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Complete a reservation
     * @param id Id of reservation
     * @return Completed reservation
     */
    @DeleteMapping("/{id}/complete")
    public ResponseEntity<ReservationDto> completeReservation(@PathVariable Long id) {
        var reservation = this.reservationService.completeReservation(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.noContent().build();
    }
}
