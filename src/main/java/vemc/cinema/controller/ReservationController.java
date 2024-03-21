package vemc.cinema.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.helperdto.PostReservationDto;
import vemc.cinema.dto.helperdto.PostTicketDto;
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
     * Post initial reservation
     * @param postReservationDto postReservation dto
     * @return ReservationDto created reservation
     */
     @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody PostReservationDto postReservationDto) {
        ReservationDto createdReservation = reservationService.createReservation(postReservationDto);
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
     * Post ticket by reservation id
     * @param id Id of reservation
     * @param postTicketDto Ticket to post
     * @return Posted ticket
     */
    @PostMapping("/{id}/tickets")
    public ResponseEntity<ReservationDto> postTicketByReservationId(@PathVariable Long id, @RequestBody PostTicketDto postTicketDto) {
        var ticket = this.reservationService.postTicketByReservationId(id, postTicketDto);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Update tickets by reservation id
     * @param id Id of reservation
     * @param postTicketDto Ticket to update
     * @return Updated ticket
     */
    @PutMapping("/{id}/tickets")
    public ResponseEntity<ReservationDto> updateTicketsByReservationId(@PathVariable Long id, @RequestBody PostTicketDto postTicketDto) {
        var ticket = this.reservationService.updateTicketsByReservationId(id, postTicketDto);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
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
     * Delete reservation by id
     * @param id Id of reservation
     * @return Deleted reservation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationDto> deleteReservation(@PathVariable Long id){
        var reservation = this.reservationService.deleteByReservationId(id);
        if(reservation != null){
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.noContent().build();
    }

    /**
        * Delete ticket by reservation id and tickets id
        * @param reservationId Id of reservation
        * @param ticketsId Id of tickets
        * @return Deleted ticket
     */
    @DeleteMapping("/{reservationId}/tickets/{ticketsId}")
    public ResponseEntity<ReservationDto> deleteTicketByReservationId(@PathVariable Long reservationId, @PathVariable Long ticketsId){
        var ticket = this.reservationService.deleteTicketByReservationId(reservationId, ticketsId);
        if(ticket != null){
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Complete a reservation
     * @param id Id of reservation
     * @return Completed reservation
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ReservationDto> completeReservation(@PathVariable Long id) {
        var reservation = this.reservationService.completeReservation(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.noContent().build();
    }
}
