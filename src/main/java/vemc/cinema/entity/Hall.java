package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amountOfFrontRowDiscounted;
    @ManyToOne(fetch = FetchType.EAGER)
    private Shows shows;
    @ManyToOne(fetch = FetchType.EAGER)
    private Seat seat;

}
