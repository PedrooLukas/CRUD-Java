package ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Customer customer;
    private List<OrderItem> items;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String paymentMethod;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    public enum OrderStatus {
        PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.subtotal = BigDecimal.ZERO;
        this.shippingCost = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
        this.totalAmount = BigDecimal.ZERO;
    }

    public Order(Long id, Customer customer, String paymentMethod) {
        this();
        this.id = id;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        calculateTotals();
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
        calculateTotals();
    }

    public void calculateTotals() {
        this.subtotal = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.shippingCost = items.stream()
                .map(item -> item.getProduct().calculateShipping())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = subtotal.add(shippingCost).subtract(discount);
    }

    public void applyDiscount(BigDecimal discountAmount) {
        this.discount = discountAmount;
        calculateTotals();
    }

    public void applyDiscount(int discountPercentage) {
        this.discount = subtotal.multiply(BigDecimal.valueOf(discountPercentage))
                .divide(BigDecimal.valueOf(100));
        calculateTotals();
    }

    public void confirmOrder() {
        if (status == OrderStatus.PENDING) {
            this.status = OrderStatus.CONFIRMED;
            System.out.println("Order #" + id + " confirmed!");
        }
    }

    public void processOrder() {
        if (status == OrderStatus.CONFIRMED) {
            this.status = OrderStatus.PROCESSING;
            System.out.println("Order #" + id + " is being processed...");
        }
    }

    public void shipOrder() {
        if (status == OrderStatus.PROCESSING) {
            this.status = OrderStatus.SHIPPED;
            System.out.println("Order #" + id + " has been shipped!");
        }
    }

    public void deliverOrder() {
        if (status == OrderStatus.SHIPPED) {
            this.status = OrderStatus.DELIVERED;
            this.deliveryDate = LocalDateTime.now();
            System.out.println("Order #" + id + " delivered successfully!");
        }
    }

    public void cancelOrder() {
        if (status != OrderStatus.DELIVERED) {
            this.status = OrderStatus.CANCELLED;
            System.out.println("Order #" + id + " has been cancelled.");
        }
    }

    public void displayOrderSummary() {
        System.out.println("\n=== Order Summary ===");
        System.out.println("Order ID: " + id);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Status: " + status);
        System.out.println("Order Date: " + orderDate);
        System.out.println("\nItems:");
        for (OrderItem item : items) {
            System.out.println("  - " + item.getProduct().getName() +
                    " x" + item.getQuantity() +
                    " = R$ " + item.getTotalPrice());
        }
        System.out.println("\nSubtotal: R$ " + subtotal);
        System.out.println("Shipping: R$ " + shippingCost);
        System.out.println("Discount: R$ " + discount);
        System.out.println("TOTAL: R$ " + totalAmount);
        System.out.println("Payment Method: " + paymentMethod);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotals();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer.getName() +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", orderDate=" + orderDate +
                '}';
    }
}
