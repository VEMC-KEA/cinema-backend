package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Hall;
import vemc.cinema.entity.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaResponseDto {
    private Long id;
    private String name;
    private Integer reservationFee;
    private Double groupDiscount;
    private Integer movieBasePrice;
    private List<Movie> movies = new ArrayList<>();
    private List<Hall> hall = new ArrayList<>();
}

