package vemc.cinema.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Screening screening;
    @OneToOne(fetch = FetchType.EAGER)
    private Seat seat;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Reservation reservation;
}

