package vemc.cinema.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.dto.helperdto.ReservationHelperDto;
import vemc.cinema.dto.helperdto.ReservationScreeningHelperDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.entity.Screening;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto {
    private Long id;
    private ReservationScreeningHelperDto screening;
    private List<ReservationTicketHelperDto> tickets = new ArrayList<>();
    private Boolean isCompleted;
}
