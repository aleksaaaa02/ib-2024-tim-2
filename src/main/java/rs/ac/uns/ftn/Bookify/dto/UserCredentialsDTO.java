package rs.ac.uns.ftn.Bookify.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDTO {
    private String email;
    private String password;
}
