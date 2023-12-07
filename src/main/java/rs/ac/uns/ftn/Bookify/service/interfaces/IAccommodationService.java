package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.FilterDTO;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public interface IAccommodationService {
    public Collection<Accommodation> getAccommodationsForSearch(Integer persons, String location, Date begin, Date end);
    public long countByLocationAndGuestRange(Integer persons, String location, Date begin, Date end);
    public List<AccommodationBasicDTO> sortAccommodationBasicDTO(List<AccommodationBasicDTO> accommodations, String sort);
    public List<AccommodationBasicDTO> setPrices(List<AccommodationBasicDTO> accommodationBasicDTO, Date begin, Date end, int persons);
    public List<Accommodation> getForFilter(List<Accommodation> accommodations, FilterDTO filter);
    public List<AccommodationBasicDTO> getForPriceRange(List<AccommodationBasicDTO> accommodations, FilterDTO filter);
    public double getTotalPrice(Long id, Date begin, Date end);
    public double getOnePrice(Long id, Date begin, Date end);
    public FileSystemResource getImages(Long id);
    public Accommodation save(Accommodation accommodation);
}
