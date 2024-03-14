package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.helperdto.HallHelperDto;
import vemc.cinema.entity.Hall;
import vemc.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HallService  {
    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<HallHelperDto> toHelperDto (Hall hall) {
        List<HallHelperDto> hallList = new ArrayList<>();

        HallHelperDto dto = new HallHelperDto();
        dto.setId(hall.getId());
        dto.setNumber(hall.getNumber());
        hallList.add(dto);
        return hallList;
    }
}
