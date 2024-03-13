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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningResponseDto {
    private Long id;
    private boolean is3d;
    private CinemaHelperDto cinema;
    private MovieHelperDto movie;
    private List<HallHelperDto> hall = new ArrayList<>();
    private LocalDate datetime;
    private List<TicketHelperDto> tickets = new ArrayList<>();
}
