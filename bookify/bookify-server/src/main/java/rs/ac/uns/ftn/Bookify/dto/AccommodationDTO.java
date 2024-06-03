package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {
    private Long id;
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String name;
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String description;
    @Min(0)
    private int minGuest;
    @Min(0)
    private int maxGuest;
    @Min(0)
    private int cancellationDeadline;
    private boolean manual = true;
    private List<Filter> filters;
    @NotNull
    private AccommodationType accommodationType;
    @NotNull
    private PricePer pricePer;
    @NotNull
    private Address address;
}
