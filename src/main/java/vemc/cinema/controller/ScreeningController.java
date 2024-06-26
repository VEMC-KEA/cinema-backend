package vemc.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.ScreeningDto;
import vemc.cinema.service.ScreeningService;

import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService){
        this.screeningService = screeningService;
    }

    /**
     * Get all screenings
     * @return List of all screenings
     */
    @Operation(summary = "Get all screenings", description = "# Get a list of all screenings")
    @GetMapping
    public ResponseEntity<List<ScreeningDto>> getAllScreenings(@RequestParam(required = false) Long movieId, @RequestParam(required = false) Long cinemaId){
        if (movieId != null && cinemaId != null) {
            var screenings = this.screeningService.findAllByCinemaIdAndMovieId(cinemaId, movieId);
            if (screenings != null) {
                return ResponseEntity.ok(screenings);
            }
            return ResponseEntity.notFound().build();
        }

        if (movieId != null) {
            var screenings = this.screeningService.findMovieById(movieId);
            if (screenings != null) {
                return ResponseEntity.ok(screenings);
            }
            return ResponseEntity.notFound().build();
        }

        var screenings = this.screeningService.findAll();
        if (screenings != null) {
            return ResponseEntity.ok(screenings);
        }
        return ResponseEntity.notFound().build();

    }

    /**
     * Create a new screening
     * @param screeningDto Screening to create
     * @return Created screening
     */
    @Operation(summary = "Create a new screening", description = "# Create a new screening")
    @PostMapping
    public ResponseEntity<ScreeningDto> createScreening(@RequestBody ScreeningDto screeningDto) {
        ScreeningDto createdScreening = screeningService.createScreening(screeningDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdScreening);
    }

    /**
     * Get screening by id
     * @param id Id of screening
     * @return Screening with given id
     */
    @Operation(summary = "Get screening by id", description = "# Get a screening by id")
    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDto> getScreeningById(@PathVariable Long id){
        var screening = this.screeningService.findById(id);
        if(screening != null){
            return ResponseEntity.ok(screening);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cancel a screening
     * @param id Id of screening
     * @return Cancelled screening
     */
    @Operation(summary = "Cancel a screening", description = "# Cancel a screening")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<ScreeningDto> cancelScreening(@PathVariable Long id) {
        ScreeningDto cancelledScreening = screeningService.cancelScreening(id);
        if (cancelledScreening != null) {
            return ResponseEntity.ok(cancelledScreening);
        }
        return ResponseEntity.notFound().build();
    }
}
