package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationInsertDTO;
import rs.ac.uns.ftn.Bookify.dto.OwnerBasicDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Owner;

@Component
public class OwnerBasicDTOMapper {
    private static ModelMapper modelMapper;

    public OwnerBasicDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Owner fromDTOtoOwner(OwnerBasicDTO dto) {
        return modelMapper.map(dto, Owner.class);
    }

    public static OwnerBasicDTO fromOwnertoDTO(Owner dto) {
        return modelMapper.map(dto, OwnerBasicDTO.class);
    }
}
