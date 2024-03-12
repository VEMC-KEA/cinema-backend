package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Hall;
import vemc.cinema.entity.Movie;
import vemc.cinema.entity.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningResponseDto {
    private Long id;
    private boolean is3d;
    private Movie movie;
    private List<Hall> hall = new ArrayList<>();
    private LocalDate datetime;
    private List<Ticket> tickets = new ArrayList<>();
}