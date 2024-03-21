package vemc.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.CinemaDto;
import vemc.cinema.dto.HallDto;
import vemc.cinema.dto.MovieDto;
import vemc.cinema.dto.SeatDto;
import vemc.cinema.service.CinemaService;

import java.util.List;

@RestController
@RequestMapping("cinemas")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }


    /**
     * Get all cinemas
     * @return List of all cinemas
     */
    @Operation (summary = "Get all cinemas", description = "# Get a list of all cinemas")
   @GetMapping
    public ResponseEntity<List<CinemaDto>> getAllCinemas(){
        var cinemas = this.cinemaService.findAll();
        if(cinemas != null){
            return ResponseEntity.ok(cinemas);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Create a new cinema
     * @param cinemaDto Cinema to create
     * @return Created cinema
     */
    @Operation (summary = "Create a new cinema", description = "# Create a new cinema")
    @PostMapping
    public ResponseEntity<CinemaDto> createCinema(@RequestBody CinemaDto cinemaDto) {
        CinemaDto createdCinema = cinemaService.createCinema(cinemaDto);
        return ResponseEntity.ok(createdCinema);
    }

    /**
     * Get cinema by id
     * @param id Id of cinema
     * @return Cinema with given id
     */
    @Operation (summary = "Get cinema by id", description = "# Get a cinema by id")
    @GetMapping("/{id}")
    public ResponseEntity<CinemaDto> getCinemaById(@PathVariable Long id){
        var cinema = this.cinemaService.findById(id);
        if(cinema != null){
            return ResponseEntity.ok(cinema);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Update cinema
     * @param id Id of cinema
     * @param cinemaDto Cinema to update
     * @return Updated cinema
     */
    @Operation (summary = "Update cinema", description = "# Update a cinema")
    @PutMapping("/{id}")
    public ResponseEntity<CinemaDto> updateCinema(@PathVariable Long id, @RequestBody CinemaDto cinemaDto) {
        CinemaDto updatedCinema = cinemaService.updateCinema(id, cinemaDto);
        return ResponseEntity.ok(updatedCinema);
    }

    /**
     * Delete cinema
     * @param id Id of cinema
     * @return No content
     */
    @Operation (summary = "Delete cinema", description = "# Delete a cinema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all halls of a cinema
     * @param id Id of cinema
     * @return List of all halls of a cinema
     */
    @Operation (summary = "Get all halls of a cinema", description = "# Get a list of all halls of a cinema")
    @GetMapping("/{id}/halls")
    public ResponseEntity<List<HallDto>> getHallsByCinemaId(@PathVariable Long id){
        var halls = this.cinemaService.getHallsByCinemaId(id);
        if(halls != null){
            return ResponseEntity.ok(halls);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Create a new hall
     * @param id Id of cinema
     * @param hallDto Hall to create
     * @return Created hall
     */
    @Operation (summary = "Create a new hall", description = "# Create a new hall")
    @PostMapping("/{id}/halls")
    public ResponseEntity<HallDto> createHall(@PathVariable Long id, @RequestBody HallDto hallDto) {
        HallDto createdHall = cinemaService.createHall(id, hallDto);
        return ResponseEntity.ok(createdHall);
    }

    /**
     * Get hall by id
     * @param cinemaId Id of cinema
     * @param hallId Id of hall
     * @return Hall with given id
     */
    @Operation (summary = "Get hall by id", description = "# Get a hall by id")
    @GetMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<HallDto> getHallsByIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId){
        var hall = this.cinemaService.getHallsByIdByCinemaId(cinemaId, hallId);
        if(hall != null){
            return ResponseEntity.ok(hall);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Update hall
     * @param cinemaId Id of cinema
     * @param hallId Id of hall
     * @param hallDto Hall to update
     * @return Updated hall
     */
    @Operation (summary = "Update hall", description = "# Update a hall")
    @PutMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<HallDto> updateHall(@PathVariable Long cinemaId, @PathVariable Long hallId, @RequestBody HallDto hallDto) {
        HallDto updatedHall = cinemaService.updateHall(cinemaId, hallId, hallDto);
        return ResponseEntity.ok(updatedHall);
    }

    /**
     * Delete hall
     * @param cinemaId Id of cinema
     * @param hallId Id of hall
     * @return No content
     */
    @Operation (summary = "Delete hall", description = "# Delete a hall via cinemaId and hallId")
    @DeleteMapping("/{cinemaId}/halls/{hallId}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long cinemaId, @PathVariable Long hallId) {
        cinemaService.deleteHall(cinemaId, hallId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all seats of a hall
     * @param cinemaId Id of cinema
     * @param hallId Id of hall
     * @return List of all seats of a hall
     */
    @Operation (summary = "Get all seats of a hall", description = "# Get a list of all seats of a hall")
    @GetMapping("/{cinemaId}/halls/{hallId}/seats")
    public ResponseEntity <List<SeatDto>> getSeatsByHallsIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId){
        var seats = this.cinemaService.getSeatsByHallsIdByCinemaId(cinemaId, hallId);
        if(seats != null){
            return ResponseEntity.ok(seats);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Get specific seat by id
     * @param cinemaId id of cinema
     * @param hallId id of hall
     * @param seatId id of seat
     * @return Seat with given id
     */
    @Operation (summary = "Get seat by id", description = "# Get a seat by id")
    @GetMapping("/{cinemaId}/halls/{hallId}/seats/{seatId}")
    public ResponseEntity <SeatDto> getSeatByIdByHallsIdByCinemaId(@PathVariable Long cinemaId, @PathVariable Long hallId, @PathVariable Long seatId){
        var seat = this.cinemaService.getSeatByIdByHallsIdByCinemaId(cinemaId, hallId, seatId);
        if(seat != null){
            return ResponseEntity.ok(seat);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all movies of a cinema
     * @param id Id of cinema
     * @return List of all movies of a cinema
     */
    @Operation (summary = "Get all movies of a cinema", description = "# Get a list of all movies of a cinema")
    @GetMapping("/{id}/movies")
    public  ResponseEntity<List<MovieDto>> getMovieByCinemaId(@PathVariable Long id) {
        var movies = this.cinemaService.getMovieByCinemaId(id);
        if (movies != null) {
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.noContent().build();
    }
}
