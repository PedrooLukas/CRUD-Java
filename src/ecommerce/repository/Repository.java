package ecommerce.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T save(T entity);
    T update(T entity);
    void delete(Long id);
    Optional<T> findById(Long id);
    List<T> findAll();
    boolean existsById(Long id);
    long count();
}
