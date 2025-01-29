package uz.pdp.ecommercewithjs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Basket {

    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
    private Integer amount;



}
