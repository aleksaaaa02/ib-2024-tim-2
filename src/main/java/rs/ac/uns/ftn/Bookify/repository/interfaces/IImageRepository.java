package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.Bookify.model.Image;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT a.images FROM Accommodation a WHERE a.id = :accommodationId")
    List<Image> findImagePathsByAccommodationId(Long accommodationId);
}
