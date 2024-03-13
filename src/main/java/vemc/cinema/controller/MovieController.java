package vemc.cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vemc.cinema.dto.MovieResponseDto;
import vemc.cinema.dto.ScreeningResponseDto;
import vemc.cinema.service.MovieService;


import java.util.List;

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
    public ResponseEntity<MovieResponseDto> getAllMoviesById(@PathVariable Long id){
        var movie = this.MovieService.findById(id);
        if(movie != null){
            return ResponseEntity.ok(movie);
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
