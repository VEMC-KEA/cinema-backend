package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vemc.cinema.dto.CinemaResponseDto;
import vemc.cinema.dto.HallResponseDto;
import vemc.cinema.dto.SeatResponseDto;
import vemc.cinema.service.CinemaService;

import java.util.List;

@RestController
@RequestMapping("cinemas")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public ResponseEntity<List<CinemaResponseDto>> getAllCinemas(){
        var cinemas = this.cinemaService.findAll();
        if(cinemas != null){
            return ResponseEntity.ok(cinemas);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaResponseDto> getCinemaById(@PathVariable Long id){
        var cinema = this.cinemaService.findById(id);
        if(cinema != null){
            return ResponseEntity.ok(cinema);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/halls")
    public ResponseEntity<List<HallResponseDto>> getHallsByCinemaId(@PathVariable Long id){
        var halls = this.cinemaService.getHallsByCinemaId(id);
        if(halls != null){
            return ResponseEntity.ok(halls);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<HallResponseDto> getHallsByIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId){
        var hall = this.cinemaService.getHallsByIdByCinemaId(cinemaId, hallId);
        if(hall != null){
            return ResponseEntity.ok(hall);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cinemaId}/halls/{hallId}/seats")
    public ResponseEntity <List<SeatResponseDto>> getSeatsByHallsIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId){
        var seats = this.cinemaService.getSeatsByHallsIdByCinemaId(cinemaId, hallId);
        if(seats != null){
            return ResponseEntity.ok(seats);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cinemaId}/halls/{hallId}/seats/{seatId}")
    public ResponseEntity <SeatResponseDto> getSeatByIdByHallsIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId, @PathVariable Long seatId){
        var seat = this.cinemaService.getSeatByIdByHallsIdByCinemaId(cinemaId, hallId, seatId);
        if(seat != null){
            return ResponseEntity.ok(seat);
        }
        return ResponseEntity.noContent().build();
    }
}
