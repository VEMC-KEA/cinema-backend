package vemc.cinema.dto.helperdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationScreeningHelperDto {
    private Long id;
    private boolean is3d;
    private CinemaHelperDto cinema;
    private MovieHelperDto movie;
    private HallHelperDto hall;
    private LocalDate date;
    private LocalTime time;
}

