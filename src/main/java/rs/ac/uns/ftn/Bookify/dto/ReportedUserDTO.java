package rs.ac.uns.ftn.Bookify.dto;

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
    private String reason;
    private Date created;
    private Long reportedUser;
    private Long createdBy;
}



