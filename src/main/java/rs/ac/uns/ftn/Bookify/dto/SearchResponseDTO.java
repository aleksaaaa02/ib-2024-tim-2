package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDTO {
    List<AccommodationBasicDTO> accommodations;
    int results;
    float minPrice;
    float maxPrice;
}
