package uz.pdp.ecommercewithjs.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.ecommercewithjs.dto.OrderDTO;

import uz.pdp.ecommercewithjs.dto.OrderItemDTO;
import uz.pdp.ecommercewithjs.entity.Order;
import uz.pdp.ecommercewithjs.entity.OrderItem;
import uz.pdp.ecommercewithjs.entity.Product;
import uz.pdp.ecommercewithjs.entity.User;
import uz.pdp.ecommercewithjs.repo.OrderRepository;
import uz.pdp.ecommercewithjs.repo.ProductRepository;
import uz.pdp.ecommercewithjs.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Transactional
    public Order createOrder(OrderDTO orderDTO, String token) {
        User user = jwtService.getUserFromToken(token);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Order order = new Order();
        order.setDateTime(orderDTO.getDateTime());
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            orderItem.setPrice(itemDTO.getPrice());

            orderItems.add(orderItem);
        }
        order.setItems(orderItems);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUser(String token) {
        User user = jwtService.getUserFromToken(token);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return orderRepository.findByUserId(user.getId());
    }
}
