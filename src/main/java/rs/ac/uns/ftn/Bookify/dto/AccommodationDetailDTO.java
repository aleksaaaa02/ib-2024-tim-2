package rs.ac.uns.ftn.Bookify.dto;

import lombok.*;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.model.Rating;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDetailDTO {
    private Long id;
    private String name;
    private String description;
    private List<PricelistItem> priceList;
    private List<Availability> availability;
    private List<Rating> ratings;
    private List<Filter> filters;
    private Address address;
    //picture

    private Long idOwner;
    private String firstName;
    private String lastName;
    private String phone;
    private float avgRating;
    //picture
}
