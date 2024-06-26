package vemc.cinema.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Seat;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto {
    private Long id;
    private Screening screening;
    private Seat seat;
    private boolean isCompleted = false;
    private Double price;
}
