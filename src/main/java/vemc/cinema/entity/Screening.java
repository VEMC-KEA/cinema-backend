package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate datetime;

}
