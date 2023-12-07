package rs.ac.uns.ftn.Bookify.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItemDTO {
    private Date startDate;
    private Date endDate;
    private double price;
}
