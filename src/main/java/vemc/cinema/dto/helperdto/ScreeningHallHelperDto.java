package vemc.cinema.dto.helperdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreeningHallHelperDto {
    private Long id;
    @Column(length = 2)
    private String row_letter;
    private Integer number;
}
