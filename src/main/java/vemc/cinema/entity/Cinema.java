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
@Entity(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer reservationFee;
    private String imageUrl;
    private Double groupDiscount;
    private Integer movieBasePrice;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Movie> movies = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Hall> hall = new ArrayList<>();
}
