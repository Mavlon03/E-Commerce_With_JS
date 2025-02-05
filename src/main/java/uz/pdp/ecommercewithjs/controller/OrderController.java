package uz.pdp.ecommercewithjs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommercewithjs.dto.OrderDTO;
import uz.pdp.ecommercewithjs.dto.OrderItemDTO;
import uz.pdp.ecommercewithjs.entity.Order;
import uz.pdp.ecommercewithjs.entity.OrderItem;
import uz.pdp.ecommercewithjs.entity.User;
import uz.pdp.ecommercewithjs.repo.OrderItemRepository;
import uz.pdp.ecommercewithjs.repo.OrderRepository;
import uz.pdp.ecommercewithjs.repo.ProductRepository;
import uz.pdp.ecommercewithjs.service.JwtService;
import uz.pdp.ecommercewithjs.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final JwtService jwtService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderDTO orderDTO,
            @RequestHeader("Authorization") String token) {

        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring(7) : token;
        Order order = orderService.createOrder(orderDTO, tokenWithoutBearer);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/order-item")
    public ResponseEntity<String> createOrderItem(@RequestBody OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found")));
        orderItem.setProduct(productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found")));
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());

        orderItemRepository.save(orderItem);
        return ResponseEntity.ok("Order item created successfully");
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(@RequestHeader("Authorization") String token) {
        // Tokenni olib, foydalanuvchining buyurtmalarini olish
        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring(7) : token;
        List<Order> orders = orderService.getOrdersByUser(tokenWithoutBearer);

        // Buyurtmalarni qaytarish
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
