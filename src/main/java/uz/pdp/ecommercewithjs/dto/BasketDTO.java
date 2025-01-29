package uz.pdp.ecommercewithjs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO{

    private Integer productId;
    private  Integer amount;

}
