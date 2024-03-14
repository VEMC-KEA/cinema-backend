package vemc.cinema.dto.helperdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationScreeningHelperDto {
    private boolean is3d;
    private CinemaHelperDto cinema;
    private MovieHelperDto movie;
    private List<HallHelperDto> hall = new ArrayList<>();
    private LocalDate datetime;
}

