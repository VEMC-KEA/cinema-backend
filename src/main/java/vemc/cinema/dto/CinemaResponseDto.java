package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Hall;
import vemc.cinema.entity.Movie;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // remove?
public class CinemaResponseDto {
    private Long id;
    private String name;
    private Integer reservationFee;
    private Double groupDiscount;
    private Double movieBasePrice;
    private Set<Movie> movies = new HashSet<>();
    private Hall hall;
}

