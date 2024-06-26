package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReservationDTO {
    private String uid;
    private String firstName;
    private String lastName;
    private double avgRating;
    private int cancellationTimes;
}
