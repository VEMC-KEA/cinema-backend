package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.MovieDto;
import vemc.cinema.dto.helperdto.CinemaHelperDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;
import vemc.cinema.entity.Cinema;
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

    public List<MovieDto> findAll() {
        return movieRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<MovieDto> findById(Long id) {
        return movieRepository.findById(id).map(this::toDto);
    }

    public MovieDto save(MovieDto movie) {
        return toDto(movieRepository.save(toEntity(movie)));
    }

    public Optional<MovieDto> updateIfExist(Long id, MovieDto movie) {
        if (movieRepository.existsById(id)) {
            Movie entity = toEntity(movie);
            entity.setId(id);
            return Optional.of(toDto(movieRepository.save(entity)));
        }
        return Optional.empty();
    }

    public Optional<MovieDto> deleteById(Long id) {
        Optional<MovieDto> existingMovie = findById(id);
        movieRepository.deleteById(id);
        return existingMovie;
    }

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

    public MovieHelperDto toHelperDto(Movie movie) {
        MovieHelperDto dto = new MovieHelperDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        return dto;
    }

}

