package com.anderscore.springboot3demo.repository;

import com.anderscore.springboot3demo.entity.Task;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Component
public class TaskRepository {

    @PersistenceContext
    EntityManager em;

    public Task findById(Long id) {
        return em.find(Task.class, id);
    }

    public List<Task> findAll() {
        return em.createQuery("select t from Task t", Task.class).getResultList();
    }

    public void update(Task entity) {
        em.merge(entity);
    }

    public void store(Task entity) {
        em.persist(entity);
    }

    public void delete(Task entity) {
        em.remove(entity);
    }
}
