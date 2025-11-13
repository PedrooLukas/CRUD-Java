package ecommerce.repository;

import ecommerce.model.Product;
import java.util.List;

public interface ProductRepository extends Repository<Product> {
    List<Product> findByCategory(String category);
    List<Product> findAvailableProducts();
    List<Product> findByPriceRange(Double minPrice, Double maxPrice);
}
