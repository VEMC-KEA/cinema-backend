package vemc.cinema.dto.helperdto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Seat;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketHelperDto {
    private Long id;
    private Seat seat;
    private boolean isCompleted = false;
    private Double price;
}
