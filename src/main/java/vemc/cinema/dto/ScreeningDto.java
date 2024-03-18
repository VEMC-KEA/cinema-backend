package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.dto.helperdto.CinemaHelperDto;
import vemc.cinema.dto.helperdto.HallHelperDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;
import vemc.cinema.dto.helperdto.TicketHelperDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningDto {
    private Long id;
    private boolean is3d;
    private CinemaHelperDto cinema;
    private MovieHelperDto movie;
    private HallDto hall;
    private LocalDate date;
    private LocalTime time;
    private List<TicketHelperDto> tickets = new ArrayList<>();
    private Boolean isCancelled = false;
}
