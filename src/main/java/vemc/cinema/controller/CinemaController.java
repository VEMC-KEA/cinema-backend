package vemc.cinema.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vemc.cinema.service.CinemaService;

@RestController
@RequestMapping("cinemas")
public class CinemaController {
    private CinemaService cinemaService;
}
