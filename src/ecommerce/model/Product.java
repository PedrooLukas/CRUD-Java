package ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private LocalDateTime createdAt;
    private boolean available;

    public Product() {
        this.createdAt = LocalDateTime.now();
        this.available = true;
    }

    public Product(Long id, String name, String description, BigDecimal price, String category) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public abstract String getProductType();
    public abstract void displayProductDetails();
    public abstract BigDecimal calculateShipping();

    public BigDecimal calculateDiscount(int percentage) {
        return price.multiply(BigDecimal.valueOf(percentage)).divide(BigDecimal.valueOf(100));
    }

    public BigDecimal calculateDiscount(BigDecimal discountAmount) {
        return discountAmount;
    }

    public BigDecimal getFinalPrice(int discountPercentage) {
        return price.subtract(calculateDiscount(discountPercentage));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", available=" + available +
                '}';
    }
}
