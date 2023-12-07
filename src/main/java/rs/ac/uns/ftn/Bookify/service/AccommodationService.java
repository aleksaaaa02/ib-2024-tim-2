package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.FilterDTO;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public FileSystemResource getImages(Long id) {
        return imageService.find(id);
    }
}
