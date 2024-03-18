package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean is3d;
    @ManyToOne(fetch = FetchType.EAGER)
    private Movie movie;
    @ManyToOne(fetch = FetchType.EAGER)
    private Hall hall;
    private LocalDate date;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Cinema cinema;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Reservation>   reservations = new ArrayList<>();
    private LocalTime time;
    private Boolean isCancelled = false;
}
