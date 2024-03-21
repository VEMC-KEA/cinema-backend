package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.MovieDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;
import vemc.cinema.entity.Movie;
import vemc.cinema.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * this is method is used to find all movies
     * @return MovieDto object
     */
    public List<MovieDto> findAll() {
        return movieRepository.findAll().stream().map(this::toDto).toList();
    }

    /**
     * This method is used to find a movie by id
     * @param id movie id
     * @return MovieDto object
     */
    public Optional<MovieDto> findById(Long id) {
        return movieRepository.findById(id).map(this::toDto);
    }

    /**
     * This method is used to save a movie
     * @param movie MovieDto object
     * @return MovieDto object
     */
    public MovieDto save(MovieDto movie) {
        return toDto(movieRepository.save(toEntity(movie)));
    }

    /**
     * This method is used to update a movie if it exists
     * @param id movie id
     * @param movie MovieDto object
     * @return MovieDto object
     */
    public Optional<MovieDto> updateIfExist(Long id, MovieDto movie) {
        if (movieRepository.existsById(id)) {
            Movie entity = toEntity(movie);
            entity.setId(id);
            return Optional.of(toDto(movieRepository.save(entity)));
        }
        return Optional.empty();
    }

    /**
     * This method is used to delete a movie by id
     * @param id movie id
     * @return MovieDto object
     */
    public Optional<MovieDto> deleteById(Long id) {
        Optional<MovieDto> existingMovie = findById(id);
        movieRepository.deleteById(id);
        return existingMovie;
    }

    /**
     * This method is used to convert a Movie object to a MovieDto object
     * @param movie Movie object
     * @return MovieDto object
     */
    private MovieDto toDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setRunTime(movie.getRunTime());
        dto.setIsClassic(movie.getIsClassic());
        dto.setPg13(movie.getPg13());
        dto.setImageUrl(movie.getImageUrl());
        return dto;
    }

    /**
     * This method is used to convert a MovieDto object to a Movie object
     * @param movie MovieDto object
     * @return Movie object
     */
    public Movie toEntity (MovieDto movie) {
        Movie entity = new Movie();
        entity.setId(movie.getId());
        entity.setTitle(movie.getTitle());
        entity.setGenre(movie.getGenre());
        entity.setRunTime(movie.getRunTime());
        entity.setIsClassic(movie.getIsClassic());
        entity.setPg13(movie.getPg13());
        entity.setImageUrl(movie.getImageUrl());
        return entity;
    }

    /**
     * This method is used to convert a Movie object to a MovieHelperDto object
     * @param movie Movie object
     * @return MovieHelperDto object
     */
    public MovieHelperDto toHelperDto(Movie movie) {
        MovieHelperDto dto = new MovieHelperDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        return dto;
    }
}

