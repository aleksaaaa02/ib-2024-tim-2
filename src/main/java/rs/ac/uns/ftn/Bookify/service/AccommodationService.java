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
    public Collection<Accommodation> getAccommodationsForSearch(Integer persons, String location, LocalDate begin, LocalDate end) {
        return accommodationRepository.findByLocationAndGuestRange(location, persons, begin, end);
    }

    @Override
    public long countByLocationAndGuestRange(Integer persons, String location, LocalDate begin, LocalDate end) {
        return accommodationRepository.countByLocationAndGuestRange(location, persons, begin, end);
    }

    @Override
    public List<AccommodationBasicDTO> sortAccommodationBasicDTO(List<AccommodationBasicDTO> accommodations, String sort) {
        switch (sort) {
            case "Name":
                Collections.sort(accommodations, Comparator.comparing(AccommodationBasicDTO::getName));
                break;
            case "Lowest":
                Collections.sort(accommodations, Comparator.comparing(AccommodationBasicDTO::getTotalPrice));
                break;
            case "Highest":
                Collections.sort(accommodations, Comparator.comparing(AccommodationBasicDTO::getTotalPrice).reversed());
                break;
        }
        return accommodations;
    }

    @Override
    public List<AccommodationBasicDTO> setPrices(List<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons) {
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
    public List<Accommodation> getForFilter(List<Accommodation> accommodations, FilterDTO filter) {
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
    public double getTotalPrice(Long id, LocalDate begin, LocalDate end) {
        double price = 0;
        while (!begin.isEqual(end)) {
            price += accommodationRepository.findPriceForDay(begin, id).get();
            begin = begin.plusDays(1);
        }
        BigDecimal originalBigDecimal = BigDecimal.valueOf(price);
        BigDecimal roundedValue = originalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return roundedValue.floatValue();
    }

    @Override
    public double getOnePrice(Long id, LocalDate begin, LocalDate end) {
        double price = 0;
        int days = 0;
        while (!begin.isEqual(end)) {
            price += accommodationRepository.findPriceForDay(begin, id).get();
            days += 1;
            begin = begin.plusDays(1);
        }
        BigDecimal originalBigDecimal = BigDecimal.valueOf(price / days);
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
        this.savePriceListItem(accommodationId, item);
        return accommodationId;
    }

    private boolean checkDatesAvailability(Accommodation accommodation, Availability availability) {
        Collection<Availability> availabilities = accommodation.getAvailability();
        for (Availability availabilityItem : availabilities) {
            if (dateCheck(availability.getStartDate(), availabilityItem.getStartDate(), availabilityItem.getEndDate()) ||
                    dateCheck(availability.getEndDate(), availabilityItem.getStartDate(), availabilityItem.getEndDate()) ||
                    dateRangeContains(availability.getStartDate(), availability.getEndDate(), availabilityItem.getStartDate(), availabilityItem.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    private static boolean dateCheck(LocalDate itemDate, LocalDate startDate, LocalDate endDate) {
        return !itemDate.isBefore(startDate) && !itemDate.isAfter(endDate);
    }

    private static boolean dateRangeContains(LocalDate itemStartDate, LocalDate itemEndDate, LocalDate startDate, LocalDate endDate) {
        return !itemStartDate.isAfter(startDate) && !itemEndDate.isBefore(endDate);
    }

    @Override
    public Long addAvailability(Long accommodationId, Availability availability) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
//        if (!checkDatesAvailability(accommodation, availability)) {
//            return null;
//        }
        accommodation.getAvailability().add(availability);
        availabilityRepository.save(availability);
        accommodationRepository.save(accommodation);
        mergeAvailabilityIntervals(accommodationId);
        return accommodationId;
    }

    @Override
    public Collection<PricelistItem> getAccommodationPriceListItems(Long accommodationId) {
        return accommodationRepository.getPriceListItems(accommodationId);
    }

    @Override
    public Collection<PricelistItem> savePriceListItem(Long accommodationId, PricelistItem item) {
        PricelistItem newPricelistItem = new PricelistItem();
        LocalDate start = item.getStartDate();
        LocalDate end = item.getEndDate();
        newPricelistItem.setStartDate(start);
        newPricelistItem.setEndDate(end);
        newPricelistItem.setPrice(item.getPrice());

        Accommodation accommodation = trimOverlapingIntervals(accommodationId, start, end);

        accommodation.getPriceList().add(newPricelistItem);
        priceListItemRepository.save(newPricelistItem);
        accommodationRepository.save(accommodation);
        mergePricelistIntervals(accommodationId);
        return accommodationRepository.getPriceListItems(accommodationId);
    }

    private Accommodation trimOverlapingIntervals(Long accommodationId, LocalDate start, LocalDate end) {
        Collection<PricelistItem> forTrimming = accommodationRepository.getPriceListItemsOverlapsWith(accommodationId, start, end);
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        for (PricelistItem pli : forTrimming) {
            LocalDate pliStart = pli.getStartDate();
            LocalDate pliEnd = pli.getEndDate();
            if (pliStart.isBefore(start) && !pliEnd.isAfter(end)) {
                //  ___(___|____)____|______
                pli.setEndDate(start.minusDays(1));
                priceListItemRepository.save(pli);
            } else if (pliEnd.isAfter(end) && !pliStart.isBefore(start)) {
                //  ______|____(____|___)___
                pli.setStartDate(end.plusDays(1));
                priceListItemRepository.save(pli);
            } else if (!start.isAfter(pliStart) && !end.isBefore(pliEnd)) {
                //  ______|__(____)__|______
                accommodation.getPriceList().removeIf(priceListItem -> priceListItem.getId().equals(pli.getId()));
                accommodationRepository.save(accommodation);
                priceListItemRepository.delete(pli);
            } else {
                //  ___(___|________|___)___
                PricelistItem splittedPricelistItem = new PricelistItem();
                splittedPricelistItem.setStartDate(end.plusDays(1));
                splittedPricelistItem.setEndDate(pliEnd);
                splittedPricelistItem.setPrice(pli.getPrice());
                pli.setEndDate(start.minusDays(1));
                accommodation.getPriceList().add(splittedPricelistItem);
                priceListItemRepository.save(splittedPricelistItem);
                priceListItemRepository.save(pli);
                accommodationRepository.save(accommodation);
            }
        }
        return accommodation;
    }

    @Override
    public void mergePricelistIntervals(Long accommodationId) {
        ArrayList<PricelistItem> pricelistItems = (ArrayList<PricelistItem>) accommodationRepository.getPriceListItems(accommodationId);

        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);

        Comparator<PricelistItem> startDateComparator = Comparator.comparing(PricelistItem::getStartDate);
        pricelistItems.sort(startDateComparator);
        PricelistItem current;
        PricelistItem next;

        for (int j = 0; j < pricelistItems.size(); j++) {
            current = pricelistItems.get(j);
            for (int i = j + 1; i < pricelistItems.size(); i++) {
                next = pricelistItems.get(i);
                if (current.getEndDate().isEqual(next.getStartDate().minusDays(1)) && current.getPrice() == next.getPrice()) {
                    current.setEndDate(next.getEndDate());
                    Long nextId = next.getId();
                    accommodation.getPriceList().removeIf(priceListItem -> priceListItem.getId().equals(nextId));
                    accommodationRepository.save(accommodation);
                    priceListItemRepository.delete(next);
                    priceListItemRepository.save(current);
                    pricelistItems.remove(next);
                    i--;
                }
            }
        }
    }

    @Override
    public void mergeAvailabilityIntervals(Long accommodationId) {
        ArrayList<Availability> availabilityItems = (ArrayList<Availability>) accommodationRepository.getAvailabilities(accommodationId);

        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);

        Comparator<Availability> startDateComparator = Comparator.comparing(Availability::getStartDate);
        availabilityItems.sort(startDateComparator);
        Availability current;
        Availability next;

        for (int j = 0; j < availabilityItems.size(); j++) {
            current = availabilityItems.get(j);
            for (int i = j + 1; i < availabilityItems.size(); i++) {
                next = availabilityItems.get(i);
//                if (current.getEndDate().isEqual(next.getStartDate().minusDays(1))) {
                if (dateCheck(current.getStartDate(), next.getStartDate().minusDays(1), next.getEndDate().plusDays(1)) ||
                        dateCheck(current.getEndDate(), next.getStartDate().minusDays(1), next.getEndDate().plusDays(1)) ||
                        dateRangeContains(current.getStartDate(), current.getEndDate(), next.getStartDate(), next.getEndDate())) {

                    current.setEndDate(next.getEndDate().compareTo(current.getEndDate()) > 0 ? next.getEndDate() : current.getEndDate());
                    current.setStartDate(next.getStartDate().compareTo(current.getStartDate()) < 0 ? next.getStartDate() : current.getStartDate());
                    Long nextId = next.getId();
                    accommodation.getAvailability().removeIf(availabilityItem -> availabilityItem.getId().equals(nextId));
                    accommodationRepository.save(accommodation);
                    availabilityRepository.delete(next);
                    availabilityRepository.save(current);
                    availabilityItems.remove(next);
                    i--;
                }
            }
        }
    }

    @Override
    public Boolean deletePriceListItem(Long accommodationId, PricelistItem item) {
        trimOverlapingIntervals(accommodationId, item.getStartDate(), item.getEndDate());
        trimOverlapingAvailabilityIntervals(accommodationId, item.getStartDate(), item.getEndDate());
        return true;
    }

    private Accommodation trimOverlapingAvailabilityIntervals(Long accommodationId, LocalDate start, LocalDate end) {
        Collection<Availability> forTrimming = accommodationRepository.getAvailabilityItemsOverlapsWith(accommodationId, start, end);
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        for (Availability a : forTrimming) {
            LocalDate pliStart = a.getStartDate();
            LocalDate pliEnd = a.getEndDate();
            if (pliStart.isBefore(start) && !pliEnd.isAfter(end)) {
                //  ___(___|____)____|______
                a.setEndDate(start.minusDays(1));
                availabilityRepository.save(a);
            } else if (pliEnd.isAfter(end) && !pliStart.isBefore(start)) {
                //  ______|____(____|___)___
                a.setStartDate(end.plusDays(1));
                availabilityRepository.save(a);
            } else if (!start.isAfter(pliStart) && !end.isBefore(pliEnd)) {
                //  ______|__(____)__|______
                accommodation.getAvailability().removeIf(availability -> availability.getId().equals(a.getId()));
                accommodationRepository.save(accommodation);
                availabilityRepository.delete(a);
            } else {
                //  ___(___|________|___)___
                Availability splittedAvailabilityItem = new Availability();
                splittedAvailabilityItem.setStartDate(end.plusDays(1));
                splittedAvailabilityItem.setEndDate(pliEnd);
                a.setEndDate(start.minusDays(1));
                accommodation.getAvailability().add(splittedAvailabilityItem);
                availabilityRepository.save(splittedAvailabilityItem);
                availabilityRepository.save(a);
                accommodationRepository.save(accommodation);
            }
        }
        return accommodation;
    }

    @Override
    public Long updatePriceListItem(Long accommodationId, PricelistItem item) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        PricelistItem pricelistItem = priceListItemRepository.getReferenceById(item.getId());
        accommodation.getPriceList().remove(pricelistItem);
//        if(!checkDatesPriceItem(accommodation, item)){
//            return null;
//        }
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
