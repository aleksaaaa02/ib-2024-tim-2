package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("SYS_ADMIN")
public class SystemAdmin extends User{

}
