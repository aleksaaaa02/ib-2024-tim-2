package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Reservation;

@Component
public class ReservationDTOMapper {

    private static ModelMapper modelMapper;

    public ReservationDTOMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public static ReservationDTO toReservationDTO(Reservation reservation) {
        ReservationDTO r = modelMapper.map(reservation, ReservationDTO.class);
        return r;
    }
}
