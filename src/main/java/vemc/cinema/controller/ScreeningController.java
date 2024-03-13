package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vemc.cinema.dto.ScreeningResponseDto;
import vemc.cinema.service.ScreeningService;

import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService){
        this.screeningService = screeningService;
    }

    @GetMapping
    public ResponseEntity<List<ScreeningResponseDto>> getAllScreenings(){
        var screenings = this.screeningService.findAll();
        if(screenings != null){
            return ResponseEntity.ok(screenings);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningResponseDto> getAllScreeningsById(@PathVariable Long id){
        var screening = this.screeningService.findById(id);
        if(screening != null){
            return ResponseEntity.ok(screening);
        }
        return ResponseEntity.notFound().build();
    }

}
