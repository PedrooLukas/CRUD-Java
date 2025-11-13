package ecommerce.repository.impl;

import ecommerce.model.Order;
import ecommerce.repository.OrderRepository;
import java.util.*;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {
    private final Map<Long, Order> database;
    private Long nextId;

    public OrderRepositoryImpl() {
        this.database = new HashMap<>();
        this.nextId = 1L;
    }

    @Override
    public Order save(Order entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Order update(Order entity) {
        if (entity.getId() == null || !database.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Order not found with ID: " + entity.getId());
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        if (!database.containsKey(id)) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        database.remove(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Order> findAll() {
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
    public List<Order> findByCustomerId(Long customerId) {
        return database.values().stream()
                .filter(o -> o.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(Order.OrderStatus status) {
        return database.values().stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
    }
}
