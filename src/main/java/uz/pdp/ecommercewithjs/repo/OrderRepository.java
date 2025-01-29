package uz.pdp.ecommercewithjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommercewithjs.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}