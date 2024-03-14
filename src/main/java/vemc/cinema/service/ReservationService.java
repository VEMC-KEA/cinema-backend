package vemc.cinema.service;

import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;
import vemc.cinema.dto.MovieDto;
import vemc.cinema.dto.ReservationDto;
import vemc.cinema.dto.ReservationTicketDto;
import vemc.cinema.dto.ScreeningDto;
import vemc.cinema.dto.helperdto.ReservationHelperDto;
import vemc.cinema.dto.helperdto.ReservationScreeningHelperDto;
import vemc.cinema.dto.helperdto.ReservationTicketHelperDto;
import vemc.cinema.dto.helperdto.ScreeningHelperDto;
import vemc.cinema.entity.Cinema;
import vemc.cinema.entity.Reservation;
import vemc.cinema.entity.Screening;
import vemc.cinema.entity.Ticket;
import vemc.cinema.repository.ReservationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScreeningService screeningService;

    private final TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository, ScreeningService screeningService, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.screeningService = screeningService;
        this.ticketService = ticketService;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDto> findById(Long id) {
        return reservationRepository.findById(id).map(this::toDto);
    }

  /*  public Optional<ReservationDto> findAllTicketsByReservationId(Long id) {
        Optional<Reservation> reservationOptional = ReservationRepository.findById(cinemaId);
        if (reservationOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Reservation reservation = reservationOptional.get();
        List<Ticket> reservationOptional.getTicket();
        



        return tickets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }*/


    public Optional<ReservationTicketDto> findTicketsByReservationId(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isEmpty()) {
            return Optional.empty();
        }
        Reservation reservation = reservationOptional.get();
        return Optional.of(toDtoReservationTicket(reservation));
    }


    public ReservationTicketDto toDtoReservationTicket(Reservation reservation) {
        ReservationTicketDto dto = new ReservationTicketDto();
        List<ReservationTicketHelperDto> ticketDtos = ticketService.toHelperDtoList(reservation.getTickets());
        dto.setTickets(ticketDtos);
        return dto;
    }



    public ReservationDto toDto (Reservation reservation){
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setScreening(screeningService.toHelperDtoScreening(reservation.getScreening()));
        dto.setIsCompleted(reservation.isCompleted());
        dto.setTickets(ticketService.toHelperDtoList(reservation.getTickets()));
        return dto;
    }

}
