package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAvailabilityRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IPriceListItemRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.Collection;

@Service
public class AccommodationService implements IAccommodationService {
    @Autowired
    IAccommodationRepository accommodationRepository;

    @Autowired
    IReservationService reservationService;

    @Autowired
    IUserService userService;

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
    public Collection<AccommodationBasicDTO> sortAccommodationBasicDTO(List<AccommodationBasicDTO> accommodations, String sort) {
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
    public Collection<AccommodationBasicDTO> setPrices(Collection<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons) {
        for (AccommodationBasicDTO accommodation : accommodationBasicDTO) {
            accommodation.setTotalPrice((float) this.getTotalPrice(accommodation.getId(), begin, end, accommodation.getPricePer(), persons));
            accommodation.setPriceOne((float) this.getOnePrice(accommodation.getId(), begin, end, accommodation.getPricePer(), persons));
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
        AccommodationDetailDTO accommodationDetailDTO = new AccommodationDetailDTO(a.getId(), a.getName(), a.getDescription(), 0, a.getReviews(), a.getFilters(), a.getAddress(), null, a.getPricePer());
        accommodationDetailDTO.setAvgRating(getAvgRating(id));
        accommodationDetailDTO.setOwner(userService.setOwnerForAccommodation(id));
        return accommodationDetailDTO;
    }

    @Override
    public Collection<AccommodationBasicDTO> getForPriceRange(Collection<AccommodationBasicDTO> accommodations, FilterDTO filter) {
        Collection<AccommodationBasicDTO> accommodationFilter = new ArrayList<>();
        for (AccommodationBasicDTO accommodation : accommodations) {
            if (fitsPriceRange(accommodation, filter.getMinPrice(), filter.getMaxPrice()))
                accommodationFilter.add(accommodation);
        }
        return accommodationFilter;
    }

    private boolean fitsPriceRange(AccommodationBasicDTO accommodation, float minPrice, float maxPrice) {
        return (minPrice == -1 || accommodation.getTotalPrice() <= maxPrice && accommodation.getTotalPrice() >= minPrice);
    }

    private boolean fitsAccommodationType(Accommodation accommodation, List<AccommodationType> type) {
        return type.contains(accommodation.getAccommodationType());
    }

    private boolean fitsFilters(Accommodation accommodation, List<Filter> filters) {
        return accommodation.getFilters().containsAll(filters);
    }

    @Override
    public double getTotalPrice(Long id, LocalDate begin, LocalDate end, PricePer pricePer, int persons) {
        double price = 0;
        while (!begin.isEqual(end)) {
            price += accommodationRepository.findPriceForDay(begin, id).get();
            begin = begin.plusDays(1);
        }
        BigDecimal originalBigDecimal = BigDecimal.valueOf(price);
        BigDecimal roundedValue = originalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (pricePer == PricePer.ROOM)
            return roundedValue.floatValue();
        else
            return roundedValue.floatValue()*persons;
    }

    @Override
    public double getOnePrice(Long id, LocalDate begin, LocalDate end, PricePer pricePer, int persons) {
        double price = 0;
        int days = 0;
        while (!begin.isEqual(end)) {
            price += accommodationRepository.findPriceForDay(begin, id).get();
            days += 1;
            begin = begin.plusDays(1);
        }
        BigDecimal originalBigDecimal = BigDecimal.valueOf(price / days);
        BigDecimal roundedValue = originalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (pricePer == PricePer.ROOM)
            return roundedValue.floatValue();
        else
            return roundedValue.floatValue()*persons;
    }

    @Autowired
    private IAvailabilityRepository availabilityRepository;

    @Autowired
    private IPriceListItemRepository priceListItemRepository;

    @Override
    public Accommodation save(Accommodation accommodation){
        accommodation.setStatus(AccommodationStatusRequest.CREATED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Long update(Accommodation accommodation) {
        Accommodation a = accommodationRepository.getReferenceById(accommodation.getId());
        if(this.reservationService.hasFutureReservationsAccommodation(a))
            throw new BadRequestException("Accommodation has reservations in the future");
        List<Filter> filters = (List<Filter>) accommodation.getFilters();
        accommodation.setFilters(new HashSet<>());
        accommodation.setReviews(a.getReviews());
        accommodation.setImages(a.getImages());
        accommodation.setStatus(AccommodationStatusRequest.EDITED);
        accommodation.setAvailability(a.getAvailability());
        accommodation.setPriceList(a.getPriceList());
        accommodation.setFilters(filters);
        accommodationRepository.save(accommodation);

        return 1L;
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

        if(reservationService.hasReservationInRange(accommodationId, item.getStartDate(), item.getEndDate())){
            throw new BadRequestException("Accommodation has reservations");
        }

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

        if(reservationService.hasReservationInRange(accommodationId, item.getStartDate(), item.getEndDate())){
            throw new BadRequestException("Accommodation has reservations");
        }

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
    public List<FileSystemResource> getAllImages(Long accommodationId) {
        Accommodation a = accommodationRepository.findById(accommodationId).get();
        List<FileSystemResource> returns = new ArrayList<>();
        for (Image image : a.getImages()) {
            returns.add(imageService.find(image.getId()));
        }
        return returns;
    }

    @Override
    public List<FileSystemResourcesDTO> getAllImagesDTO(Long accommodationId) {
        Accommodation a = accommodationRepository.findById(accommodationId).get();
        List<FileSystemResourcesDTO> returns = new ArrayList<>();
        for (Image image : a.getImages()) {
            returns.add( new FileSystemResourcesDTO(imageService.find(image.getId()), image.getId()));
        }
        return returns;
    }

    @Override
    public float getAvgRating(Long id) {
        Float avg = accommodationRepository.getAverageReviewByAccommodationId(id);
        if (avg == null)
            return 0f;
        else
            return avg.floatValue();
    }

    @Override
    public Collection<AccommodationBasicDTO> getAvgRatings(Collection<AccommodationBasicDTO> accommodations) {
        for (AccommodationBasicDTO a : accommodations) {
            a.setAvgRating(this.getAvgRating(a.getId()));
        }
        return accommodations;
    }

    @Override
    public List<AccommodationOwnerDTO> getOwnerAccommodation(Long ownerId) {
        List<AccommodationOwnerDTO> result = new ArrayList<>();
        for(Accommodation accommodation : accommodationRepository.getOwnerAccommodation(ownerId)){
            result.add(new AccommodationOwnerDTO(accommodation, getAvgRating(accommodation.getId())));
        }
        return result;
    }

    @Override
    public Accommodation getAccommodation(Long accommodationId) {
        return accommodationRepository.getReferenceById(accommodationId);
    }
    public SearchResponseDTO getSearchResponseForSearch(Collection<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons, String location, int page, int size) {
        accommodationBasicDTO = setPrices(accommodationBasicDTO, begin, end, persons);
        long totalResults = countByLocationAndGuestRange(persons, location, begin, end);
        float minPrice = getMinPrice(accommodationBasicDTO);
        float maxPrice = getMaxPrice(accommodationBasicDTO);
        accommodationBasicDTO = paging(accommodationBasicDTO, page, size);
        accommodationBasicDTO = getAvgRatings(accommodationBasicDTO);
        return new SearchResponseDTO(accommodationBasicDTO, (int) totalResults, minPrice, maxPrice);
    }

    @Override
    public SearchResponseDTO getSearchReposnseForFilter(Collection<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons, String location, int page, int size, String sort, FilterDTO filter) {
        accommodationBasicDTO = setPrices(accommodationBasicDTO, begin, end, persons);
        accommodationBasicDTO = getForPriceRange(accommodationBasicDTO, filter);
        accommodationBasicDTO = sortAccommodationBasicDTO(new ArrayList<>(accommodationBasicDTO), sort);
        int totalResults = accommodationBasicDTO.size();
        accommodationBasicDTO = paging(accommodationBasicDTO, page, size);
        accommodationBasicDTO = getAvgRatings(accommodationBasicDTO);
        return new SearchResponseDTO(accommodationBasicDTO, totalResults, 0, 0);
    }

    @Override
    public float getMinPrice(Collection<AccommodationBasicDTO> accommodationBasicDTO) {
        try {
            return accommodationBasicDTO.stream().min(Comparator.comparingDouble(AccommodationBasicDTO::getTotalPrice)).orElse(null).getTotalPrice();
        } catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public float getMaxPrice(Collection<AccommodationBasicDTO> accommodationBasicDTO) {
        try {
            return accommodationBasicDTO.stream().max(Comparator.comparingDouble(AccommodationBasicDTO::getTotalPrice)).orElse(null).getTotalPrice();
        } catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Collection<AccommodationBasicDTO> paging(Collection<AccommodationBasicDTO> accommodationBasicDTO, int page, int size) {
        if ((page+1)*size > accommodationBasicDTO.size())
            return new ArrayList<>(accommodationBasicDTO).subList(page*size, accommodationBasicDTO.size());
        else
            return new ArrayList<>(accommodationBasicDTO).subList(page*size, (page+1)*size);
    }

    @Override
    public Collection<Accommodation> filterAccommodations(int persons, String location, LocalDate begin, LocalDate end, FilterDTO filter) {
        return getForFilter((List<Accommodation>) getAccommodationsForSearch(persons, location, begin, end), filter);
    }

    @Override
    public boolean isAvailable(Long id, LocalDate beginL, LocalDate endL) {
        return accommodationRepository.checkIfAccommodationAvailable(id, beginL, endL) > 0;
    }

    @Override
    public boolean checkPersons(Long id, int persons) {
        return accommodationRepository.checkPersons(id, persons) == 1;
    }

    @Override
    public void setAccommodationStatus(Long id, AccommodationStatusRequest newStatus) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if(accommodation.isEmpty()) {
            return;
        }
        Accommodation a = accommodation.get();
        a.setStatus(newStatus);
        accommodationRepository.save(a);
    }

    @Override
    public void deleteAccommodation(Long accommodationId) {
        this.accommodationRepository.deleteById(accommodationId);
    }

    @Override
    public void insertForGuest(Long guestId, Long accommodationId) {
        userService.addToFavorites(guestId, accommodationId);
    }

    public FileSystemResource getImage(Long id) {
        return imageService.find(id);
    }
}
