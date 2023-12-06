package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    private IAccommodationRepository accommodationRepository;

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }
}
