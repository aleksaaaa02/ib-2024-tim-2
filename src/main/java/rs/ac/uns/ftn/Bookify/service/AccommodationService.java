package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;

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
    public Page<Accommodation> getAccommodationsForSearch(Integer persons, String location, Date begin, Date end, Pageable pageable) {
        return accommodationRepository.findByLocationAndGuestRange(location, persons, begin, end, pageable);
    }

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
    public double getTotalPrice(Long id, Date begin, Date end) {
        LocalDate beginL = begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        double price = 0;
        while (!beginL.isEqual(endL)) {
            price += accommodationRepository.findPriceForDay(Date.from(beginL.atStartOfDay(ZoneId.systemDefault()).toInstant()), id).get();
            beginL = beginL.plusDays(1);
        }
        return price;
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
        return price / days;
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
