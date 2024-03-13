package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.MovieResponseDto;
import vemc.cinema.entity.Movie;
import vemc.cinema.repository.MovieRepository;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieResponseDto> findAll() {
        return movieRepository.findAll().stream().map(this::toDto).toList();
    }

    public MovieResponseDto findById(Long id) {
        return movieRepository.findById(id).map(this::toDto).orElse(null);
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
}
