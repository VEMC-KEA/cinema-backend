package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.CinemaDto;
import vemc.cinema.dto.HallDto;
import vemc.cinema.dto.MovieDto;
import vemc.cinema.dto.SeatDto;
import vemc.cinema.service.CinemaService;

import java.util.List;

@CrossOrigin(origins = "https://cinema-frontend-nine.vercel.app/")
@RestController
@RequestMapping("cinemas")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }

   @GetMapping
    public ResponseEntity<List<CinemaDto>> getAllCinemas(){
        var cinemas = this.cinemaService.findAll();
        if(cinemas != null){
            return ResponseEntity.ok(cinemas);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CinemaDto> createCinema(@RequestBody CinemaDto cinemaDto) {
        CinemaDto createdCinema = cinemaService.createCinema(cinemaDto);
        return ResponseEntity.ok(createdCinema);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDto> getCinemaById(@PathVariable Long id){
        var cinema = this.cinemaService.findById(id);
        if(cinema != null){
            return ResponseEntity.ok(cinema);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaDto> updateCinema(@PathVariable Long id, @RequestBody CinemaDto cinemaDto) {
        CinemaDto updatedCinema = cinemaService.updateCinema(id, cinemaDto);
        return ResponseEntity.ok(updatedCinema);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/halls")
    public ResponseEntity<List<HallDto>> getHallsByCinemaId(@PathVariable Long id){
        var halls = this.cinemaService.getHallsByCinemaId(id);
        if(halls != null){
            return ResponseEntity.ok(halls);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/halls")
    public ResponseEntity<HallDto> createHall(@PathVariable Long id, @RequestBody HallDto hallDto) {
        HallDto createdHall = cinemaService.createHall(id, hallDto);
        return ResponseEntity.ok(createdHall);
    }

    @GetMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<HallDto> getHallsByIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId){
        var hall = this.cinemaService.getHallsByIdByCinemaId(cinemaId, hallId);
        if(hall != null){
            return ResponseEntity.ok(hall);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<HallDto> updateHall(@PathVariable Long cinemaId, @PathVariable Long hallId, @RequestBody HallDto hallDto) {
        HallDto updatedHall = cinemaService.updateHall(cinemaId, hallId, hallDto);
        return ResponseEntity.ok(updatedHall);
    }

    @DeleteMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long cinemaId, @PathVariable Long hallId) {
        cinemaService.deleteHall(cinemaId, hallId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cinemaId}/halls/{hallId}/seats")
    public ResponseEntity <List<SeatDto>> getSeatsByHallsIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId){
        var seats = this.cinemaService.getSeatsByHallsIdByCinemaId(cinemaId, hallId);
        if(seats != null){
            return ResponseEntity.ok(seats);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cinemaId}/halls/{hallId}/seats/{seatId}")
    public ResponseEntity <SeatDto> getSeatByIdByHallsIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId, @PathVariable Long seatId){
        var seat = this.cinemaService.getSeatByIdByHallsIdByCinemaId(cinemaId, hallId, seatId);
        if(seat != null){
            return ResponseEntity.ok(seat);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/movies")
    public  ResponseEntity<List<MovieDto>> getMovieByCinemaId(@PathVariable Long id) {
        var movies = this.cinemaService.getMovieByCinemaId(id);
        if (movies != null) {
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.noContent().build();
    }

}
