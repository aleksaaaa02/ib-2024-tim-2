package rs.ac.uns.ftn.Bookify.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private String uid;

    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @Size(min = 3, max = 50)
    @Pattern(message = "Not valid",regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
    private String firstName;
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9!?,.\\-\\s]*$")
    private String lastName;
    private boolean blocked;
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;
    @Valid
    private Address address;
    private Long imageId;
    public UserDetailDTO(User user){
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.blocked = user.isBlocked();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        if(user.getProfileImage() != null) {
            this.imageId = user.getProfileImage().getId();
        }
    }
}
