package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Seat;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HallDto {
    private Long id;
    private Integer number;
    private Double amountOfFrontRowDiscounted;
    private List<Seat> seats = new ArrayList<>();
}

