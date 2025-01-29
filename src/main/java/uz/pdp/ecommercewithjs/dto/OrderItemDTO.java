package uz.pdp.ecommercewithjs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Integer productId;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
}
