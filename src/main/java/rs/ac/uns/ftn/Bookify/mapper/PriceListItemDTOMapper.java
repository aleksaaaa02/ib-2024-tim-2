package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.PriceListItemDTO;
import rs.ac.uns.ftn.Bookify.model.Availability;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Component
public class PriceListItemDTOMapper {
    private static ModelMapper modelMapper;

    public PriceListItemDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static PricelistItem fromDTOtoPriceListItem(PriceListItemDTO dto){
        return modelMapper.map(dto, PricelistItem.class);
    }

    public static PriceListItemDTO fromPriceListItemtoDTO(PricelistItem dto){
        return modelMapper.map(dto, PriceListItemDTO.class);
    }

    public static Collection<PriceListItemDTO> fromPriceListItemtoDTO(Collection<PricelistItem> dto){
        Collection<PriceListItemDTO> priceListItemDTOS = new ArrayList<>();
        for(PricelistItem plt : dto){
            priceListItemDTOS.add(modelMapper.map(plt, PriceListItemDTO.class));
        }
        return priceListItemDTOS;
    }

    public static Availability fromDTOtoAvailability(PriceListItemDTO dto){
        return modelMapper.map(dto, Availability.class);
    }

    public static PriceListItemDTO fromAvailabilitytoDTO(Availability dto){
        return modelMapper.map(dto, PriceListItemDTO.class);
    }
}
