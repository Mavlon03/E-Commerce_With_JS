package uz.pdp.ecommercewithjs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private Integer price;
    private Integer attachmentId;
    private Integer categoryId;
}
