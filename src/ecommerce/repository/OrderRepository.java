package ecommerce.repository;

import ecommerce.model.Order;
import java.util.List;

public interface OrderRepository extends Repository<Order> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(Order.OrderStatus status);
}
