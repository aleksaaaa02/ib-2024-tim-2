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
    public List<Accommodation> getAccommodationsForSearch(Integer persons, String location) {
        return accommodationRepository.findByLocationAndGuestRange(location, persons);
    }

    @Override
    public FileSystemResource getImages(Long id) {
        return imageService.find(id);
    }
}
