package uz.pdp.ecommercewithjs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.ecommercewithjs.dto.OrderDTO;
import uz.pdp.ecommercewithjs.entity.Order;
import uz.pdp.ecommercewithjs.entity.OrderItem;
import uz.pdp.ecommercewithjs.entity.Product;
import uz.pdp.ecommercewithjs.repo.OrderRepository;
import uz.pdp.ecommercewithjs.repo.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .dateTime(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = orderDTO.getItems().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(product.getPrice())
                    .build();
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
