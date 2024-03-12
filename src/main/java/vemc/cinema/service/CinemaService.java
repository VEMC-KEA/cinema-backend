package vemc.cinema.service;

import org.springframework.stereotype.Service;
import vemc.cinema.repository.CinemaRepository;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }


}
