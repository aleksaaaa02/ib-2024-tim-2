package rs.ac.uns.ftn.Bookify.dto;

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
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String firstName;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String lastName;
    private boolean blocked;
    @NotNull
    @NotEmpty
    private String phone;
    @NotNull
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
