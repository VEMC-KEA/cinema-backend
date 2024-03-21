package vemc.cinema.dto.helperdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReservationTicketHelperDto {
    private Long id;
    private Long seatId;
    private String rowLetter;
    private Integer number;
    private Double price;
}
