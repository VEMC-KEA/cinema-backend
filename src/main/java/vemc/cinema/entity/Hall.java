package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Double amountOfFrontRowDiscounted;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Screening> screening = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Seat> seat = new ArrayList<>();
}
