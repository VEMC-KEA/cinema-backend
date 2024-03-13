package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.*;
import vemc.cinema.entity.Screening;
import vemc.cinema.repository.ScreeningRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//    public ScreeningResponseDto toDto(Screening screening) {
//        ScreeningResponseDto dto = new ScreeningResponseDto();
//        dto.setId(screening.getId());
//        dto.set3d(screening.is3d());
//        dto.setMovie(screening.getMovie());
//        dto.setHall(screening.getHall());
//        dto.setDatetime(screening.getDatetime());
//        dto.setTickets(screening.getTickets());
//        return dto;
//    }

    public ScreeningResponseDto toDto(Screening screening) {
        ScreeningResponseDto dto = new ScreeningResponseDto();
        dto.setId(screening.getId());
        dto.set3d(screening.is3d());

        CinemaDto cinemaDto = new CinemaDto();
        cinemaDto.setId(screening.getCinema().getId());
        cinemaDto.setName(screening.getCinema().getName());

        dto.setCinema(cinemaDto);

        MovieDto movieDto = new MovieDto();
        movieDto.setId(screening.getMovie().getId());
        movieDto.setTitle(screening.getMovie().getTitle());

        dto.setMovie(movieDto);

        List<HallDto> hallDtos = screening.getHall().stream().map(hall -> {
            HallDto hallDto = new HallDto();
            hallDto.setId(hall.getId());
            hallDto.setNumber(hall.getNumber());
            return hallDto;
        }).toList();
        dto.setHall(hallDtos);

        List<TicketDto> ticketDtos = screening.getTickets().stream().map(ticket -> {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setId(ticket.getId());
            ticketDto.setSeat(ticket.getSeat());
            ticketDto.setPrice(ticket.getPrice());
            return ticketDto;
        }).toList();
        dto.setTickets(ticketDtos);

        return dto;
    }

}