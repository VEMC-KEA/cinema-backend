package vemc.cinema.dto.helperdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Hall;
import vemc.cinema.entity.Movie;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningHelperDto {
    private Long id;
    private boolean is3d;
    private Movie movie;
    private Hall hall;
    private LocalDate datetime;
}
