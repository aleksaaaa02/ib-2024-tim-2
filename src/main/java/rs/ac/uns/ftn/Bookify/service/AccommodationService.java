package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.PriceListItemDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAvailabilityRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IPriceListItemRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;

import java.util.Collection;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Autowired
    private IAvailabilityRepository availabilityRepository;

    @Autowired
    private IPriceListItemRepository priceListItemRepository;

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Long addPriceList(Long accommodationId, PricelistItem item) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.getPriceList().add(item);
        priceListItemRepository.save(item);
        accommodationRepository.save(accommodation);
        return accommodationId;
    }

    @Override
    public Long addAvailability(Long accommodationId, Availability availability) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.getAvailability().add(availability);
        availabilityRepository.save(availability);
        accommodationRepository.save(accommodation);
        return accommodationId;
    }

    @Override
    public Collection<PricelistItem> getAccommodationPriceListItems(Long accommodationId) {
        return accommodationRepository.getPriceListItems(accommodationId);
    }

    @Override
    public Boolean deletePriceListItem(Long accommodationId, Long priceListItemId) {
        PricelistItem item = priceListItemRepository.getReferenceById(priceListItemId);
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.getPriceList().removeIf(priceListItem -> priceListItem.getId().equals(priceListItemId));
        accommodationRepository.save(accommodation);
        priceListItemRepository.delete(item);
        return true;
    }

    @Override
    public Boolean deleteAvailabilityItem(Long accommodationId, Long availabilityId) {
        Availability item = availabilityRepository.getReferenceById(availabilityId);
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.getAvailability().removeIf(availability -> availability.getId().equals(availabilityId));
        accommodationRepository.save(accommodation);
        availabilityRepository.delete(item);
        return true;
    }

    @Override
    public void updatePriceListItem(PricelistItem item) {
        priceListItemRepository.save(item);
    }

    @Override
    public void updateAvailabilityItem(Availability availability) {
        availabilityRepository.save(availability);
    }
}
