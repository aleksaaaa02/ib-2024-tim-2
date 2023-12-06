package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.PriceListItemDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;

import java.util.Collection;

public interface IAccommodationService {
    public Accommodation save(Accommodation accommodation);
    public Long addPriceList(Long accommodationId, PricelistItem item);
    public Long addAvailability(Long accommodationId, Availability availability);
    public Collection<PricelistItem> getAccommodationPriceListItems(Long accommodationId);
}
