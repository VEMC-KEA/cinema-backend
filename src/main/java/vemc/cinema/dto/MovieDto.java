package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto {
    private Long id;
    private Integer runTime;
    private Boolean isClassic;
    private String genre;
    private Boolean pg13;
    private String title;
    private String imageUrl;
}
