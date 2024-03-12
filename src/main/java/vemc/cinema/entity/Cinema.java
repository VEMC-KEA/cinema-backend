package vemc.cinema.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer reservationFee;
    private Double groupDiscount;
    private Integer movieBasePrice;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Hall hall;
}
