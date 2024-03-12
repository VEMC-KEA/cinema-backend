package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Seat;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HallResponseDto {
    private Long id;
    private String name;
    private Double amountOfFrontRowDiscounted;
    private Screening screening;
    private List<Seat> seat = new ArrayList<>();

}

