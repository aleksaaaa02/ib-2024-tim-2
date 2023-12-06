package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import java.util.Date;

@Service
public interface IAccommodationService {
    public Page<Accommodation> getAccommodationsForSearch(Integer persons, String location, Date begin, Date end, Pageable pageable);
    public long countByLocationAndGuestRange(Integer persons, String location, Date begin, Date end);
    public double getTotalPrice(Long id, Date begin, Date end);
    public double getOnePrice(Long id, Date begin, Date end);
    public FileSystemResource getImages(Long id);
    public Accommodation save(Accommodation accommodation);
}
