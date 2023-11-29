package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @OneToMany
    private List<Accommodation> editedAccommodations;

}
