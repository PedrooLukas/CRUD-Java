package ecommerce.service;

import ecommerce.model.PhysicalProduct;
import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;
import ecommerce.util.ValidationUtil;
import java.math.BigDecimal;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        ValidationUtil.validateProduct(product);
        System.out.println("Creating product: " + product.getName());
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Product updateProduct(Product product) {
        ValidationUtil.validateProduct(product);
        if (!productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product not found with ID: " + product.getId());
        }
        System.out.println("Updating product: " + product.getName());
        return productRepository.update(product);
    }

    public Product updateProductPrice(Long id, BigDecimal newPrice) {
        Product product = getProduct(id);
        product.setPrice(newPrice);
        return productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        System.out.println("Deleting product with ID: " + id);
        productRepository.delete(id);
    }

    public void updateStock(Long productId, int quantity) {
        Product product = getProduct(productId);
        if (product instanceof PhysicalProduct) {
            PhysicalProduct physicalProduct = (PhysicalProduct) product;
            physicalProduct.addStock(quantity);
            productRepository.update(physicalProduct);
            System.out.println("Stock updated. New quantity: " + physicalProduct.getStockQuantity());
        } else {
            System.out.println("Cannot update stock for digital products");
        }
    }

    public void setProductAvailability(Long id, boolean available) {
        Product product = getProduct(id);
        product.setAvailable(available);
        productRepository.update(product);
        System.out.println("Product availability updated: " + available);
    }

    public void displayAllProducts() {
        List<Product> products = getAllProducts();
        System.out.println("\n=== All Products ===");
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            products.forEach(Product::displayProductDetails);
            System.out.println();
        }
    }

    public long getTotalProducts() {
        return productRepository.count();
    }
}
