package rs.ac.uns.ftn.Bookify.repository.interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.model.*;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a FROM Accommodation a " +
            "JOIN a.address ad " +
            "JOIN a.availability av " +
            "WHERE a.maxGuest >= :persons " +
            "AND a.minGuest <= :persons " +
            "AND (av.startDate <= :begin " +
            "AND av.endDate >= :begin " +
            "AND av.startDate <= :end " +
            "AND av.endDate >= :end) " +
            "AND (LOWER(ad.city) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.address) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.country) LIKE LOWER(CONCAT('%', :location, '%'))) ")
    Collection<Accommodation> findByLocationAndGuestRange(
            @Param("location") String location,
            @Param("persons") int persons,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end);

    @Query("select a.priceList from Accommodation a join a.priceList where a.id=?1")
    public Collection<PricelistItem> getPriceListItems(Long accommodationId);

    @Query("select pl from Accommodation a " +
            "join a.priceList pl " +
            "where a.id = :accommodationId " +
            "and ((pl.startDate <= :startDate " +
            "and pl.endDate >= :startDate) " +
            "or (pl.startDate <= :endDate " +
            "and pl.endDate >= :endDate) " +
            "or (pl.startDate >= :startDate " +
            "and pl.endDate <= :endDate))")
    public Collection<PricelistItem> getPriceListItemsOverlapsWith(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("select av from Accommodation a " +
            "join a.availability av " +
            "where a.id = :accommodationId " +
            "and ((av.startDate <= :startDate " +
            "and av.endDate >= :startDate) " +
            "or (av.startDate <= :endDate " +
            "and av.endDate >= :endDate) " +
            "or (av.startDate >= :startDate " +
            "and av.endDate <= :endDate))")
    public Collection<Availability> getAvailabilityItemsOverlapsWith(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("select a.availability from Accommodation a  join a.availability where a.id=?1")
    public Collection<Availability> getAvailabilities(Long accommodationId);

    @Query("SELECT COUNT(a) FROM Accommodation a " +
            "JOIN a.address ad " +
            "JOIN a.availability av " +
            "WHERE a.maxGuest >= :persons " +
            "AND a.minGuest <= :persons " +
            "AND av.startDate <= :begin " +
            "AND av.endDate >= :begin " +
            "AND av.startDate <= :end " +
            "AND av.endDate >= :end " +
            "AND (LOWER(ad.city) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.address) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.country) LIKE LOWER(CONCAT('%', :location, '%'))) ")
    long countByLocationAndGuestRange(
            @Param("location") String location,
            @Param("persons") int persons,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end);

    @Query("SELECT p.price " +
            "FROM Accommodation a " +
            "JOIN a.priceList p " +
            "WHERE p.startDate <= :date AND p.endDate >= :date " +
            "AND a.id = :accommodationId")
    Optional<Double> findPriceForDay(
            @Param("date") LocalDate date,
            @Param("accommodationId") Long accommodationId);

    @Query("SELECT AVG(r.rate) FROM Accommodation a JOIN a.reviews r WHERE a.id = :accommodationId")
    Float getAverageReviewByAccommodationId(@Param("accommodationId") Long accommodationId);

    @Query("SELECT o.accommodations FROM Owner o WHERE o.id =:ownerId ")
    List<Accommodation> getOwnerAccommodation(Long ownerId);

    @Query("SELECT COUNT(a) FROM Accommodation a " +
            "JOIN a.availability av " +
            "WHERE av.startDate <= :begin " +
            "AND av.endDate >= :begin " +
            "AND av.startDate <= :end " +
            "AND av.endDate >= :end " +
            "AND a.id = :accommodationId")
    long checkIfAccommodationAvailable(@Param("accommodationId") Long accommodationId,
                                          @Param("begin") LocalDate begin,
                                          @Param("end") LocalDate end);

    @Query("SELECT COUNT(a) FROM Accommodation a " +
            "WHERE a.id = :accommodationId " +
            "AND a.minGuest <= :persons " +
            "AND a.maxGuest >= :persons")
    long checkPersons(@Param("accommodationId") Long accommodationId,
                                       @Param("persons") int persons);

}
