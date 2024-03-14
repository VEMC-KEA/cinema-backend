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
public class HallScreeningHelperDto {
    private Long id;
    private MovieHelperDto movieHelperDto;
    private List<ScreeningHallHelperDto> screeningHallHelperDto = new ArrayList<>();
    private List<TicketScreeningHelperDto> ticketScreeningHelperDtos = new ArrayList<>();

}
