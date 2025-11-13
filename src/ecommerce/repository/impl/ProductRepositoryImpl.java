package ecommerce.repository.impl;

import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {
    private final Map<Long, Product> database;
    private Long nextId;

    public ProductRepositoryImpl() {
        this.database = new HashMap<>();
        this.nextId = 1L;
    }

    @Override
    public Product save(Product entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Product update(Product entity) {
        if (entity.getId() == null || !database.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Product not found with ID: " + entity.getId());
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        if (!database.containsKey(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        database.remove(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }

    @Override
    public long count() {
        return database.size();
    }

    @Override
    public List<Product> findByCategory(String category) {
        return database.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAvailableProducts() {
        return database.values().stream()
                .filter(Product::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return database.values().stream()
                .filter(p -> p.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0 &&
                           p.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .collect(Collectors.toList());
    }
}
