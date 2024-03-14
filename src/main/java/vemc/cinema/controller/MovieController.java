package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.MovieResponseDto;
import vemc.cinema.dto.ScreeningResponseDto;
import vemc.cinema.service.MovieService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService MovieService;

    public MovieController(MovieService movieService) {
        MovieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies(){
        var movies = this.MovieService.findAll();
        if(movies != null){
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MovieResponseDto>> getAllMoviesById(@PathVariable Long id){
        var movie = this.MovieService.findById(id);
        if(movie.isPresent()){
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<MovieResponseDto> create(@RequestBody MovieResponseDto movie){
        if(movie.getTitle() == null){
            return null;
        }
        return ResponseEntity.ok(MovieService.save(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> update(@RequestBody MovieResponseDto movie, @PathVariable("id") Long id) {
        return ResponseEntity.of(MovieService.updateIfExist(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieResponseDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.of(MovieService.deleteById(id));
    }


}


/*

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
*/
