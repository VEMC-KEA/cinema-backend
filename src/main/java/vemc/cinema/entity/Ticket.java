package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import vemc.cinema.utils.PriceCalculator;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Screening screening;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Seat seat;
    private Double price;

    public Double getPrice() {
        if (price == null) {
            PriceCalculator.calculatePrice(this, screening, seat);
        }
        return price;
    }
}
