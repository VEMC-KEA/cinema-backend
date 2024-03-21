package vemc.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vemc.cinema.dto.MovieDto;
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

    /**
     * Get all movies
     * @return List of all movies
     */
    @Operation(summary = "Get all movies", description = "# Get a list of all movies")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        var movies = this.MovieService.findAll();
        if(movies != null){
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Get movie by id
     * @param id Id of movie
     * @return Movie with given id
     */
    @Operation(summary = "Get movie by id", description = "# Get a movie by id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<MovieDto>> getAllMoviesById(@PathVariable Long id){
        var movie = this.MovieService.findById(id);
        if(movie.isPresent()){
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Create a new movie
     * @param movie Movie to create
     * @return Created movie
     */
    @Operation(summary = "Create a new movie", description = "# Create a new movie")
    @PostMapping()
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto movie){
        if(movie.getTitle() == null){
            return null;
        }
        return ResponseEntity.ok(MovieService.save(movie));
    }

    /**
     * Update movie
     * @param id Id of movie
     * @param movie Movie to update
     * @return Updated movie
     */
    @Operation(summary = "Update movie", description = "# Update a movie")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@RequestBody MovieDto movie, @PathVariable("id") Long id) {
        return ResponseEntity.of(MovieService.updateIfExist(id, movie));
    }

    /**
     * Delete movie
     * @param id Id of movie
     * @return Deleted movie
     */
    @Operation(summary = "Delete movie", description = "# Delete a movie")
    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.of(MovieService.deleteById(id));
    }
}

