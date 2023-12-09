package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDetailDTO;
import rs.ac.uns.ftn.Bookify.dto.FilterDTO;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAvailabilityRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IPriceListItemRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import java.util.Collection;
import java.util.Date;

@Service
public class AccommodationService implements IAccommodationService {
    @Autowired
    IAccommodationRepository accommodationRepository;

    @Autowired
    IImageService imageService;

    @Override
    public Collection<Accommodation> getAccommodationsForSearch(Integer persons, String location, Date begin, Date end) {
        return accommodationRepository.findByLocationAndGuestRange(location, persons, begin, end);
    }

    @Override
    public long countByLocationAndGuestRange(Integer persons, String location, Date begin, Date end) {
        return accommodationRepository.countByLocationAndGuestRange(location, persons, begin, end);
    }

    @Override
    public List<AccommodationBasicDTO> sortAccommodationBasicDTO(List<AccommodationBasicDTO> accommodations, String sort) {
        switch (sort){
            case "Name": Collections.sort(accommodations, Comparator.comparing(AccommodationBasicDTO::getName)); break;
            case "Lowest": Collections.sort(accommodations, Comparator.comparing(AccommodationBasicDTO::getTotalPrice)); break;
            case "Highest": Collections.sort(accommodations, Comparator.comparing(AccommodationBasicDTO::getTotalPrice).reversed()); break;
        }
        return accommodations;
    }

    @Override
    public List<AccommodationBasicDTO> setPrices(List<AccommodationBasicDTO> accommodationBasicDTO, Date begin, Date end, int persons) {
        for (AccommodationBasicDTO accommodation : accommodationBasicDTO) {
            if (accommodation.getPricePer() == PricePer.PERSON)
                accommodation.setTotalPrice((float) this.getTotalPrice(accommodation.getId(), begin, end) * persons);
            else
                accommodation.setTotalPrice((float) this.getTotalPrice(accommodation.getId(), begin, end));
            accommodation.setPriceOne((float) this.getOnePrice(accommodation.getId(), begin, end));
        }
        return accommodationBasicDTO;
    }

    @Override
    public List<Accommodation> getForFilter(List<Accommodation> accommodations, FilterDTO filter){
        List<Accommodation> accommodationFilter = new ArrayList<>();
        for (Accommodation accommodation : accommodations) {
            if (fitsAccommodationType(accommodation, filter.getTypes()) && fitsFilters(accommodation, filter.getFilters()))
                accommodationFilter.add(accommodation);
        }
        return accommodationFilter;
    }

    @Override
    public AccommodationDetailDTO getAccommodationDetails(Long id) {
        Accommodation a = this.accommodationRepository.findById(id).get();
        return new AccommodationDetailDTO(a.getId(), a.getName(), a.getDescription(), 0, a.getReviews(), a.getFilters(), a.getAddress(), null);
    }

    @Override
    public List<AccommodationBasicDTO> getForPriceRange(List<AccommodationBasicDTO> accommodations, FilterDTO filter) {
        List<AccommodationBasicDTO> accommodationFilter = new ArrayList<>();
        for (AccommodationBasicDTO accommodation : accommodations) {
            if (fitsPriceRange(accommodation, filter.getMinPrice(), filter.getMaxPrice()))
                accommodationFilter.add(accommodation);
        }
        return accommodationFilter;
    }

    private boolean fitsPriceRange(AccommodationBasicDTO accommodation, float minPrice, float maxPrice) {
        return (minPrice == -1 || accommodation.getTotalPrice() <= maxPrice && accommodation.getTotalPrice() > minPrice);
    }

    private boolean fitsAccommodationType(Accommodation accommodation, List<AccommodationType> type) {
        return type.contains(accommodation.getAccommodationType());
    }

    private boolean fitsFilters(Accommodation accommodation, List<Filter> filters) {
        return accommodation.getFilters().containsAll(filters);
    }

    @Override
    public double getTotalPrice(Long id, Date begin, Date end) {
        LocalDate beginL = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        double price = 0;
        while (!beginL.isEqual(endL)) {
            price += accommodationRepository.findPriceForDay(Date.from(beginL.atStartOfDay(ZoneId.systemDefault()).toInstant()), id).get();
            beginL = beginL.plusDays(1);
        }
        BigDecimal originalBigDecimal = BigDecimal.valueOf(price);
        BigDecimal roundedValue = originalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return roundedValue.floatValue();
    }

    @Override
    public double getOnePrice(Long id, Date begin, Date end) {
        LocalDate beginL = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        double price = 0;
        int days = 0;
        while (!beginL.isEqual(endL)) {
            price += accommodationRepository.findPriceForDay(Date.from(beginL.atStartOfDay(ZoneId.systemDefault()).toInstant()), id).get();
            days += 1;
            beginL = beginL.plusDays(1);
        }
        BigDecimal originalBigDecimal = BigDecimal.valueOf(price/days);
        BigDecimal roundedValue = originalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return roundedValue.floatValue();
    }

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
        if(!checkDatesPriceItem(accommodation, item)){
            return null;
        }
        accommodation.getPriceList().add(item);
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
        if(!checkDatesAvailability(accommodation, availability)){
            return null;
        }
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
        if (!checkDatesAvailability(accommodation, availability)) {
            return null;
        }
        accommodation.getAvailability().add(availabilityTemp);
        availabilityRepository.save(availability);
        return accommodationId;
    }
    public FileSystemResource getImages(Long id) {
        return imageService.find(id);
    }
}
