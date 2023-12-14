package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Owner findByAccommodations_Id(Long accommodationId);

    @Query("SELECT o FROM Owner o WHERE o.id=:ownerId")
    Owner findOwnerById(@Param("ownerId") Long ownerId);

    @Query("SELECT AVG(r.rate) FROM Owner o JOIN o.reviews r WHERE o.id = :ownerId")
    Float getAverageReviewByOwnerId(@Param("ownerId") Long ownerId);

    User findByEmail(String email);

    @Query("select u from User u where u.active.hashToken = :uuid")
    User findByHashToken(String uuid);

    @Modifying
    @Query("delete from User u where u.id= :userId")
    void deleteUser(Long userId);
}
