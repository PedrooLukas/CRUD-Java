package ecommerce.service;

import ecommerce.model.*;
import ecommerce.repository.OrderRepository;
import ecommerce.util.ValidationUtil;
import java.math.BigDecimal;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(Customer customer, String paymentMethod) {
        ValidationUtil.validateNotNull(customer, "Customer cannot be null");
        ValidationUtil.validateNotEmpty(paymentMethod, "Payment method cannot be empty");
        
        Order order = new Order(null, customer, paymentMethod);
        System.out.println("Creating order for customer: " + customer.getName());
        return orderRepository.save(order);
    }

    public Order createOrder(Customer customer, String paymentMethod, List<OrderItem> items) {
        Order order = createOrder(customer, paymentMethod);
        items.forEach(order::addItem);
        return orderRepository.update(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public void addItemToOrder(Long orderId, Product product, int quantity) {
        Order order = getOrder(orderId);
        
        if (product instanceof PhysicalProduct) {
            PhysicalProduct physicalProduct = (PhysicalProduct) product;
            if (!physicalProduct.hasStock(quantity)) {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
            }
            physicalProduct.reduceStock(quantity);
            productService.updateProduct(physicalProduct);
        }
        
        OrderItem item = new OrderItem(null, product, quantity);
        order.addItem(item);
        orderRepository.update(order);
        System.out.println("Item added to order: " + product.getName());
    }

    public void applyDiscount(Long orderId, int discountPercentage) {
        Order order = getOrder(orderId);
        order.applyDiscount(discountPercentage);
        orderRepository.update(order);
        System.out.println("Discount applied: " + discountPercentage + "%");
    }

    public void applyDiscount(Long orderId, BigDecimal discountAmount) {
        Order order = getOrder(orderId);
        order.applyDiscount(discountAmount);
        orderRepository.update(order);
        System.out.println("Discount applied: R$ " + discountAmount);
    }

    public void confirmOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.confirmOrder();
        orderRepository.update(order);
    }

    public void processOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.processOrder();
        orderRepository.update(order);
    }

    public void shipOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.shipOrder();
        orderRepository.update(order);
    }

    public void deliverOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.deliverOrder();
        orderRepository.update(order);
    }

    public void cancelOrder(Long orderId) {
        Order order = getOrder(orderId);
        order.cancelOrder();
        
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            if (product instanceof PhysicalProduct) {
                PhysicalProduct physicalProduct = (PhysicalProduct) product;
                physicalProduct.addStock(item.getQuantity());
                productService.updateProduct(physicalProduct);
            }
        }
        
        orderRepository.update(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        System.out.println("Deleting order with ID: " + id);
        orderRepository.delete(id);
    }

    public void displayAllOrders() {
        List<Order> orders = getAllOrders();
        System.out.println("\n=== All Orders ===");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            orders.forEach(Order::displayOrderSummary);
        }
    }

    public BigDecimal calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }
}
