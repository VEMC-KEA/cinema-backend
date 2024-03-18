package vemc.cinema.dto.helperdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.dto.SeatDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostReservationDto {
    private Long screeningId;
    private List<TicketHelperDto> tickets;
}
