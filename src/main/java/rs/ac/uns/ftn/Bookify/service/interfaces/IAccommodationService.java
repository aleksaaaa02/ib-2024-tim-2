package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

import java.util.Collection;

@Service
public interface IAccommodationService {
    public Collection<Accommodation> getAccommodations();
    public FileSystemResource getImages(Long id);
}
