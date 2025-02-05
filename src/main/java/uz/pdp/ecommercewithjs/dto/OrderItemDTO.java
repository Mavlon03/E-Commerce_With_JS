package uz.pdp.ecommercewithjs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Integer orderId;
    private Integer productId;
    private Double quantity;
    private Double price;
    private Double totalPrice;
}
