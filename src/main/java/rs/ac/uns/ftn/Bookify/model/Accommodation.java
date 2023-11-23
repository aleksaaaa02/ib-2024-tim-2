package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private int minGuest;

    @Column(nullable = false)
    private int maxGuest;

    @Column(nullable = false)
    private int cancellationDeadline;

    @Column(nullable = false)
    private boolean accepted;

    @Column(nullable = false)
    private boolean manual = true;

    @OneToMany
    private Collection<PricelistItem> priceList;
    @OneToMany
    private Collection<Availability> availability;
    @OneToMany
    private Collection<Review> reviews;

    @ElementCollection(targetClass = Filter.class)
    @Enumerated(EnumType.STRING)
    private Collection<Filter> filters;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType accommodationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PricePer pricePer;

    @Embedded
    @Column(nullable = false)
    private Address address;

}
