package vemc.cinema.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.ScreeningDto;
import vemc.cinema.service.ScreeningService;

import java.util.List;

@CrossOrigin(origins = "https://cinema-frontend-nine.vercel.app/")
@RestController
@RequestMapping("screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService){
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<List<ScreeningDto>> getAllScreenings(){
        var screenings = this.screeningService.findAll();
        if(screenings != null){
            return ResponseEntity.ok(screenings);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ScreeningDto> createScreening(@RequestBody ScreeningDto screeningDto) {
        ScreeningDto createdScreening = screeningService.createScreening(screeningDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdScreening);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDto> getAllScreeningsById(@PathVariable Long id){
        var screening = this.screeningService.findById(id);
        if(screening != null){
            return ResponseEntity.ok(screening);
        }
        return ResponseEntity.notFound().build();
    }

}
