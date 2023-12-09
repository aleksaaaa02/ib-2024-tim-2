package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.Bookify.dto.AccommodationDetailDTO;
import rs.ac.uns.ftn.Bookify.dto.UserDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.Collection;


import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    Collection<Accommodation> findByLocationAndGuestRange(
            @Param("location") String location,
            @Param("persons") int persons,
            @Param("begin") Date begin,
            @Param("end") Date end);

    @Query("select a.priceList from Accommodation a  join a.priceList where a.id=?1")
    public Collection<PricelistItem> getPriceListItems(Long accommodationId);

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
            @Param("begin") Date begin,
            @Param("end") Date end);

    @Query("SELECT p.price " +
            "FROM Accommodation a " +
            "JOIN a.priceList p " +
            "WHERE p.startDate <= :date AND p.endDate >= :date " +
            "AND a.id = :accommodationId")
    Optional<Double> findPriceForDay(
            @Param("date") Date date,
            @Param("accommodationId") Long accommodationId);
}
