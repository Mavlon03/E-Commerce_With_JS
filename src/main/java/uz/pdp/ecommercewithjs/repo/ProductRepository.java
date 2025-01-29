package uz.pdp.ecommercewithjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommercewithjs.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}