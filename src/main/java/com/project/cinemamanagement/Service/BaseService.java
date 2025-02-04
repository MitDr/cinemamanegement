package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Exception.DataFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<T, ID> {
    protected abstract JpaRepository<T, ID> getRepository();

    public List<T> getAll() {
        return getRepository().findAll();
    }

    public T getById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new DataFoundException("Entity not found with id: " + id));
    }

    public void create(T entity) {
         getRepository().save(entity);}
    public T update(ID id, T updatedEntity) {
        getById(id);
        return getRepository().save(updatedEntity);
    }

    public void delete(ID id) {
        T existingEntity = getById(id);
        getRepository().delete(existingEntity);
    }

}
