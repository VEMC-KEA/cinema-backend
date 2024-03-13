package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.dto.*;
import vemc.cinema.dto.helperdto.CinemaHelperDto;
import vemc.cinema.dto.helperdto.HallHelperDto;
import vemc.cinema.dto.helperdto.MovieHelperDto;
import vemc.cinema.dto.helperdto.TicketHelperDto;
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

        CinemaHelperDto cinemaDto = new CinemaHelperDto();
        cinemaDto.setId(screening.getCinema().getId());
        cinemaDto.setName(screening.getCinema().getName());

        dto.setCinema(cinemaDto);

        MovieHelperDto movieDto = new MovieHelperDto();
        movieDto.setId(screening.getMovie().getId());
        movieDto.setTitle(screening.getMovie().getTitle());

        dto.setMovie(movieDto);

        List<HallHelperDto> hallDtos = screening.getHall().stream().map(hall -> {
            HallHelperDto hallDto = new HallHelperDto();
            hallDto.setId(hall.getId());
            hallDto.setNumber(hall.getNumber());
            return hallDto;
        }).toList();
        dto.setHall(hallDtos);

        List<TicketHelperDto> ticketDtos = screening.getTickets().stream().map(ticket -> {
            TicketHelperDto ticketDto = new TicketHelperDto();
            ticketDto.setId(ticket.getId());
            ticketDto.setSeat(ticket.getSeat());
            ticketDto.setPrice(ticket.getPrice());
            return ticketDto;
        }).toList();
        dto.setTickets(ticketDtos);

        return dto;
    }

}