package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

import java.util.Date;
import java.util.List;

public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a FROM Accommodation a " +
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
    Page<Accommodation> findByLocationAndGuestRange(
            @Param("location") String location,
            @Param("persons") int persons,
            @Param("begin") Date begin,
            @Param("end") Date end,
            Pageable pageable);

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
            @Param("begin") Date begin,
            @Param("end") Date end);
}
