package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.dto.helperdto.HallScreeningHelperDto;
import vemc.cinema.entity.Screening;
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
    private List<Screening> screening = new ArrayList<>();
    // lavet om til en Screening List i stedet for en HallScreeningHelperDto
    private List<Seat> seat = new ArrayList<>();

}

