package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {
    @Autowired
    IAccommodationRepository accommodationRepository;

    @Autowired
    IImageService imageService;

    @Override
    public Page<Accommodation> getAccommodationsForSearch(Integer persons, String location, Date begin, Date end, Pageable pageable) {
        return accommodationRepository.findByLocationAndGuestRange(location, persons, begin, end, pageable);
    }

    @Override
    public long countByLocationAndGuestRange(Integer persons, String location, Date begin ,Date end) {
        return accommodationRepository.countByLocationAndGuestRange(location, persons, begin, end);
    }

    @Override
    public FileSystemResource getImages(Long id) {
        return imageService.find(id);
    }
}
