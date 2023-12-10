package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.Bookify.dto.UserDetailDTO;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("select new rs.ac.uns.ftn.Bookify.dto.UserDetailDTO(u) from User u where u.id=:accountId")
    public UserDetailDTO findUserAccount(Long accountId);

    Owner findByAccommodations_Id(Long accommodationId);

    @Query("SELECT AVG(r.rate) FROM Owner o JOIN o.reviews r WHERE o.id = :ownerId")
    Float getAverageReviewByOwnerId(@Param("ownerId") Long ownerId);
}
