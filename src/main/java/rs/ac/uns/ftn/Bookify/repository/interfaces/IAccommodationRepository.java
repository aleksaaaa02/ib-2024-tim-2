package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;

import java.util.Collection;
import java.util.List;

public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("select a.priceList from Accommodation a  join a.priceList where a.id=?1")
    public Collection<PricelistItem> getPriceListItems(Long accommodationId);
}
