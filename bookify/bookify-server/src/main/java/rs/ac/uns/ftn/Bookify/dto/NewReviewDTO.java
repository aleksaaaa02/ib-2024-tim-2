package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewReviewDTO {
    @NotEmpty
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
    private String comment;
    @Min(0)
    @Max(5)
    private int rate;
    @NotNull
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String guestId;
}
