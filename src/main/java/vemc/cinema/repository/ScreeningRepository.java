package vemc.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vemc.cinema.entity.Screening;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findAllByMovieId(Long movieId);

    List<Screening> findAllByCinemaIdAndMovieId(Long cinemaId, Long movieId);

    List<Screening> findScreeningByReservationsId(Long reservationId);
}
