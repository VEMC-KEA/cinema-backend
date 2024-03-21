package vemc.cinema.dto.helperdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
