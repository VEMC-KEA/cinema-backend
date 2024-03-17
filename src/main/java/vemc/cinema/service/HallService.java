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

    public List<Hall> findByScreeningId(Long screeningId) {
        return hallRepository.findByScreeningId(screeningId);
    }

    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    public void deleteByScreeningId(Long screeningId) {
        // Fetch the Hall entities that reference the Screening
        List<Hall> halls = hallRepository.findByScreeningId(screeningId);
        // Remove the reference to the Screening entity from each Hall entity
        for (Hall hall : halls) {
            hall.getScreening().removeIf(screening -> screening.getId().equals(screeningId));
            hallRepository.save(hall);
        }
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
