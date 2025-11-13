package ecommerce.model;

import java.math.BigDecimal;

public class PhysicalProduct extends Product {
    private double weight;
    private double height;
    private double width;
    private double length;
    private int stockQuantity;

    public PhysicalProduct() {
        super();
    }

    public PhysicalProduct(Long id, String name, String description, BigDecimal price, String category,
                          double weight, double height, double width, double length, int stockQuantity) {
        super(id, name, description, price, category);
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String getProductType() {
        return "PHYSICAL";
    }

    @Override
    public void displayProductDetails() {
        System.out.println("=== Physical Product ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Price: R$ " + getPrice());
        System.out.println("Category: " + getCategory());
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Dimensions: " + length + "x" + width + "x" + height + " cm");
        System.out.println("Stock: " + stockQuantity + " units");
        System.out.println("Shipping: R$ " + calculateShipping());
        System.out.println("Available: " + (isAvailable() ? "Yes" : "No"));
    }

    @Override
    public BigDecimal calculateShipping() {
        double volumetricWeight = (length * width * height) / 6000;
        double finalWeight = Math.max(weight, volumetricWeight);
        return BigDecimal.valueOf(finalWeight * 2.5 + 10);
    }

    public boolean hasStock() {
        return stockQuantity > 0;
    }

    public boolean hasStock(int quantity) {
        return stockQuantity >= quantity;
    }

    public void reduceStock(int quantity) {
        if (hasStock(quantity)) {
            this.stockQuantity -= quantity;
        } else {
            throw new IllegalStateException("Insufficient stock");
        }
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "PhysicalProduct{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", stock=" + stockQuantity +
                ", weight=" + weight + "kg" +
                '}';
    }
}
