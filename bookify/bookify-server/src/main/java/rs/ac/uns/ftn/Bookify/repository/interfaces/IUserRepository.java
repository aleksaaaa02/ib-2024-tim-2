package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.Bookify.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Owner findByAccommodations_Id(Long accommodationId);

    @Query("SELECT o FROM Owner o WHERE o.uid=:ownerId")
    Owner findOwnerById(@Param("ownerId") String ownerId);

    @Query("SELECT g FROM Guest g WHERE g.uid=:guestId")
    Guest findGuestById(@Param("guestId") String guestId);

    @Query("SELECT AVG(r.rate) FROM Owner o JOIN o.reviews r WHERE o.uid = :ownerId")
    Float getAverageReviewByOwnerId(@Param("ownerId") String ownerId);

    User findByEmail(String email);

    @Query("SELECT o FROM Owner o")
    List<Owner> findAllOwners();

    @Query("SELECT o FROM Owner o JOIN o.accommodations a WHERE a.id = :accommodationId")
    Owner getOwner(Long accommodationId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO users_favorites (guest_id, favorites_id) VALUES (:userId, :accommodationId)")
    void addFavoriteToUser(@Param("userId") String userId, @Param("accommodationId") Long accommodationId);

    @Query(value = "SELECT COUNT(*) FROM users_favorites WHERE guest_id = :guestId AND favorites_id = :accommodationId", nativeQuery = true)
    int checkIfInFavorites(@Param("guestId") String guestId, @Param("accommodationId") Long accommodationId);

    @Query("SELECT o FROM Owner o WHERE :review MEMBER OF o.reviews")
    Owner getOwnerByReview(Review review);

    Boolean existsByUid(String uid);

    Optional<User> findUserByUid(String userUid);
}
