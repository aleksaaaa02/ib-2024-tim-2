package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.Active;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Notification;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean blocked;
    private String phone;
    private List<Notification> notifications;
    private Active active;
    private Address address;
}
