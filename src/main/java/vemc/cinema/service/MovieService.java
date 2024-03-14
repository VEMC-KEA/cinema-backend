package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.MovieResponseDto;
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

    public List<MovieResponseDto> findAll() {
        return movieRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<MovieResponseDto> findById(Long id) {
        return movieRepository.findById(id).map(this::toDto);
    }

    public MovieResponseDto save(MovieResponseDto movie) {
        return toDto(movieRepository.save(toEntity(movie)));
    }

    public Optional<MovieResponseDto> updateIfExist(Long id, MovieResponseDto movie) {
        if (movieRepository.existsById(id)) {
            Movie entity = toEntity(movie);
            entity.setId(id);
            return Optional.of(toDto(movieRepository.save(entity)));
        }
        return Optional.empty();
    }

    public Optional<MovieResponseDto> deleteById(Long id) {
        Optional<MovieResponseDto> existingMovie = findById(id);
        movieRepository.deleteById(id);
        return existingMovie;
    }

    private MovieResponseDto toDto(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setRunTime(movie.getRunTime());
        dto.setIsClassic(movie.getIsClassic());
        dto.setPg13(movie.getPg13());
        return dto;
    }

    public Movie toEntity (MovieResponseDto movie) {
        Movie entity = new Movie();
        entity.setId(movie.getId());
        entity.setTitle(movie.getTitle());
        entity.setGenre(movie.getGenre());
        entity.setRunTime(movie.getRunTime());
        entity.setIsClassic(movie.getIsClassic());
        entity.setPg13(movie.getPg13());
        return entity;
    }

}

