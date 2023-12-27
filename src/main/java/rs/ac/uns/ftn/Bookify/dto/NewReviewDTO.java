package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewReviewDTO {
    private String comment;
    private int rate;
    private Long guestId;
}
