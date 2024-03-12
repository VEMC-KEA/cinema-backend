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
        var Screening = this.screeningService.findAll();
        if(Screening != null){
            return ResponseEntity.ok(Screening);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningResponseDto> getAllScreeningsById(@PathVariable Long id){
        var Screenings = this.screeningService.findById(id);
        if(Screenings != null){
            return ResponseEntity.ok(Screenings);
        }
        return ResponseEntity.notFound().build();
    }

}
