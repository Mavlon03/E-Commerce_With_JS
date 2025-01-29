package uz.pdp.ecommercewithjs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {


    private LocalDateTime dateTime;
    private List<OrderItemDTO> items;
}
