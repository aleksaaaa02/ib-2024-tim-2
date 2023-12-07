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
import java.util.Date;

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
        if(!checkDatesPriceItem(accommodation, item)){
            return null;
        }
        priceListItemRepository.save(item);
        accommodationRepository.save(accommodation);
        return accommodationId;
    }

    private boolean checkDatesPriceItem(Accommodation accommodation, PricelistItem item) {
        Collection<PricelistItem> pricelistItemList = accommodation.getPriceList();
        for (PricelistItem pricelistItem : pricelistItemList) {
            if (dateCheck(item.getStartDate(), pricelistItem.getStartDate(), pricelistItem.getEndDate()) ||
                    dateCheck(item.getEndDate(), pricelistItem.getStartDate(), pricelistItem.getEndDate()) ||
                    dateRangeContains(item.getStartDate(), item.getEndDate(), pricelistItem.getStartDate(), pricelistItem.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDatesAvailability(Accommodation accommodation, Availability availability){
        Collection<Availability> availabilities = accommodation.getAvailability();
        for (Availability availabilityItem : availabilities){
            if (dateCheck(availability.getStartDate(), availabilityItem.getStartDate(), availabilityItem.getEndDate()) ||
                    dateCheck(availability.getEndDate(), availabilityItem.getStartDate(), availabilityItem.getEndDate()) ||
                    dateRangeContains(availability.getStartDate(), availability.getEndDate(), availabilityItem.getStartDate(), availabilityItem.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    private static boolean dateCheck(Date itemDate, Date startDate, Date endDate) {
        return itemDate.compareTo(startDate) >= 0 && itemDate.compareTo(endDate) <= 0;
    }

    private static boolean dateRangeContains(Date itemStartDate, Date itemEndDate, Date startDate, Date endDate) {
        return itemStartDate.compareTo(startDate) <= 0 && itemEndDate.compareTo(endDate) >= 0;
    }

    @Override
    public Long addAvailability(Long accommodationId, Availability availability) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        accommodation.getAvailability().add(availability);
        if(!checkDatesAvailability(accommodation, availability)){
            return null;
        }
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
    public Long updatePriceListItem(Long accommodationId, PricelistItem item) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        PricelistItem pricelistItem = priceListItemRepository.getReferenceById(item.getId());
        accommodation.getPriceList().remove(pricelistItem);
        if(!checkDatesPriceItem(accommodation, item)){
            return null;
        }
        accommodation.getPriceList().add(pricelistItem);
        priceListItemRepository.save(item);
        return accommodationId;
    }

    @Override
    public Long updateAvailabilityItem(Long accommodationId, Availability availability) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        Availability availabilityTemp = availabilityRepository.getReferenceById(availability.getId());
        accommodation.getAvailability().remove(availabilityTemp);
        if(!checkDatesAvailability(accommodation, availability)){
            return null;
        }
        accommodation.getAvailability().add(availabilityTemp);
        availabilityRepository.save(availability);
        return accommodationId;
    }
}
