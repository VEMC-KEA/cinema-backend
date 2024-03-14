package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vemc.cinema.dto.TicketDto;
import vemc.cinema.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>>getAllTickets(){
        var tickets = this.ticketService.findAll();
        if(tickets != null){
            return ResponseEntity.ok(tickets);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto>getTicketById(@PathVariable Long id){
        var ticket = this.ticketService.findById(id);
            if(ticket != null){
                return ResponseEntity.ok(ticket);
            }
        return ResponseEntity.notFound().build();
    }
}
