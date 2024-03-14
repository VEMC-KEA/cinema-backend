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

    @PostMapping
    public ResponseEntity<MovieResponseDto> create(@RequestBody MovieResponseDto movie){
        var savedMovie = this.MovieService.save(movie);
        if(savedMovie != null){
            return ResponseEntity.ok(savedMovie);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> update(@PathVariable Long id, @RequestBody MovieResponseDto movie){
        var updatedMovie = this.MovieService.updateIfExist(id, movie);
        if(updatedMovie.isPresent()){
            return ResponseEntity.ok(updatedMovie.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieResponseDto> delete(@PathVariable Long id){
        var deletedMovie = this.MovieService.deleteById(id);
        if(deletedMovie.isPresent()){
            return ResponseEntity.ok(deletedMovie.get());
        }
        return ResponseEntity.notFound().build();
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
