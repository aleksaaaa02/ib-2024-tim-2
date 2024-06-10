package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private String uid;
    private String firstName;
    private String lastName;
    private String phone;
    private float avgRating;
    private Long imageId;
}
