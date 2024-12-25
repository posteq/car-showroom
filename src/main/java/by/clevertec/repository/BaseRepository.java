package by.clevertec.repository;

import java.util.List;

public interface BaseRepository<E,K> {

    E create(E entity);

    void delete(K id);

    E update(E entity);

    E findById(K id);

    List<E> findAll();
}
