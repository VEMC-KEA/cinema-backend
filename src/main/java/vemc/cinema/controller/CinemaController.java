package vemc.cinema.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vemc.cinema.dto.CinemaResponseDto;
import vemc.cinema.service.CinemaService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

}
