package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationInsertDTO {
    private String name;
    private String description;
    private int minGuest;
    private int maxGuest;
    private int cancellationDeadline;
    private boolean manual = true;
    private List<Filter> filters;
    private AccommodationType accommodationType;
    private PricePer pricePer;
    private Address address;
}
