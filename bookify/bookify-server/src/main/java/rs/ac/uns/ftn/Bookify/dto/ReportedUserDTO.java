package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUserDTO {

    private Long id;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String reason;
    private Date created;
    @NotNull
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String reportedUser;
    @NotNull
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String createdBy;
}



