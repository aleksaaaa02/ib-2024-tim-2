package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String name;
    private String description;
    private int minGuest;
    private int maxGuest;
    private int cancellationDeadline;
    private boolean manual = true;
    private List<PricelistItem> priceList;
    private List<Availability> availability;
    private List<Filter> filters;
    private AccommodationType accommodationType;
    private PricePer pricePer;
    private Address address;
    private Owner owner;
    //picture
}
