package vemc.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vemc.cinema.dto.helperdto.HallHelperDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaDto {
    private Long id;
    private String name;
    private Integer reservationFee;
    private Double groupDiscount;
    private Integer movieBasePrice;
    private String imageUrl;
    private List<MovieHelperDto> movies = new ArrayList<>();
    private List<HallHelperDto> hall = new ArrayList<>();
}

