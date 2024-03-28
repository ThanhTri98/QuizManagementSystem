package com.demo.services;

import java.util.List;
import java.util.Optional;

/**
 * @author 165139
 */
public interface IBaseService<E, I> {
    List<E> findAll();

    Optional<E> findById(I id);

    void add(E entity);

    void update(E entity);

    void deleteById(I id);

}
