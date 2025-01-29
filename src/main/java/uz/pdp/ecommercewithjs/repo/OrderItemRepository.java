package uz.pdp.ecommercewithjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommercewithjs.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}