package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

@Component
public class AccommodationBasicDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public AccommodationBasicDTOMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public static AccommodationBasicDTO fromAccommodationToBasicDTO(Accommodation accommodation) {
        return modelMapper.map(accommodation, AccommodationBasicDTO.class);
    }
}
