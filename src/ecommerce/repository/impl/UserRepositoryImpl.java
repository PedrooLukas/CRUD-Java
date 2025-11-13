package ecommerce.repository.impl;

import ecommerce.model.User;
import ecommerce.repository.UserRepository;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> database;
    private Long nextId;

    public UserRepositoryImpl() {
        this.database = new HashMap<>();
        this.nextId = 1L;
    }

    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public User update(User entity) {
        if (entity.getId() == null || !database.containsKey(entity.getId())) {
            throw new IllegalArgumentException("User not found with ID: " + entity.getId());
        }
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        if (!database.containsKey(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        database.remove(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<User> findAll() {
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
    public Optional<User> findByEmail(String email) {
        return database.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return database.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}
