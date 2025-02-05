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


    private Integer userId;
    private List<OrderItemDTO> items;
    private LocalDateTime dateTime;
}
