package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.Bookify.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

}
