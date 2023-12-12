package rs.ac.uns.ftn.Bookify.service.interfaces;


import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDetailDTO;
import rs.ac.uns.ftn.Bookify.dto.FilterDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public interface IAccommodationService {
    public Collection<Accommodation> getAccommodationsForSearch(Integer persons, String location, LocalDate begin, LocalDate end);
    public long countByLocationAndGuestRange(Integer persons, String location, LocalDate begin, LocalDate end);
    public List<AccommodationBasicDTO> sortAccommodationBasicDTO(List<AccommodationBasicDTO> accommodations, String sort);
    public List<AccommodationBasicDTO> setPrices(List<AccommodationBasicDTO> accommodationBasicDTO, LocalDate begin, LocalDate end, int persons);
    public List<Accommodation> getForFilter(List<Accommodation> accommodations, FilterDTO filter);
    public AccommodationDetailDTO getAccommodationDetails(Long id);
    public List<AccommodationBasicDTO> getForPriceRange(List<AccommodationBasicDTO> accommodations, FilterDTO filter);
    public double getTotalPrice(Long id, LocalDate begin, LocalDate end);
    public double getOnePrice(Long id, LocalDate begin, LocalDate end);
    public FileSystemResource getImage(Long id);
    public Accommodation save(Accommodation accommodation);
    public Long addPriceList(Long accommodationId, PricelistItem item);
    public Long addAvailability(Long accommodationId, Availability availability);
    public Collection<PricelistItem> getAccommodationPriceListItems(Long accommodationId);
    public Collection<PricelistItem> savePriceListItem(Long accommodationId, PricelistItem item);
    public void mergePricelistIntervals(Long accommodationId);
    public void mergeAvailabilityIntervals(Long accommodationId);
    public Boolean deletePriceListItem(Long accommodationId, PricelistItem item);
    public Long updatePriceListItem(Long accommodationId, PricelistItem item);
    public Long updateAvailabilityItem(Long accommodationId, Availability availability);
    public List<FileSystemResource> getAllImages(Long accommodationId);
    public float getAvgRating(Long id);
    public List<AccommodationBasicDTO> getAvgRatings(List<AccommodationBasicDTO> accommodations);
    public List<Accommodation> getOwnerAccommodation(Long ownerId);
    public Accommodation getAccommodation(Long accommodationId);
}
