package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.ScreeningResponseDto;
import vemc.cinema.entity.Screening;
import vemc.cinema.repository.ScreeningRepository;

import java.util.List;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository){
        this.screeningRepository = screeningRepository;
    }

    public List<ScreeningResponseDto> findAll() {
        return screeningRepository.findAll().stream().map(this::toDto).toList();
    }

    public ScreeningResponseDto findById(Long id) {
        return screeningRepository.findById(id).map(this::toDto).orElse(null);
    }

    public ScreeningResponseDto toDto(Screening screening) {
        ScreeningResponseDto dto = new ScreeningResponseDto();
        dto.setId(screening.getId());
        dto.set3d(screening.is3d());
        dto.setMovie(screening.getMovie());
        dto.setHall(screening.getHall());
        dto.setDatetime(screening.getDatetime());
        dto.setTickets(screening.getTickets());
        return dto;
    }

}