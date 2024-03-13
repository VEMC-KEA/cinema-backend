package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Hall> hall = new ArrayList<>();
    private LocalDate datetime;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Cinema cinema;
}
