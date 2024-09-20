package com.anderscore.springboot3demo.resource;

import com.anderscore.springboot3demo.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.anderscore.springboot3demo.repository.TaskRepository;

import jakarta.websocket.server.PathParam;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController("api/v1/tasks")
public class TaskResource {

    @Autowired
    TaskRepository repository;

    @GetMapping(produces = "application/json")
    public List<Task> findAll() {
        return repository.findAll();
    }
    
    @GetMapping(path = "/{id}", produces = "application/json")
    public Task findById(@PathParam("id") Long id) {
        Task task = repository.findById(id);

        if (task != null) {
            return task;
        }

        throw new ResponseStatusException(NOT_FOUND);
    }
    
    @PutMapping(path = "/{id}", consumes = "application/json")
    public void update(@PathParam("id") Long id, Task task) {
        repository.update(task);
    }

    @PostMapping(consumes = "application/json")
    public Task create(Task task) {
        repository.store(task);

        return task;
    }
    
    @DeleteMapping(path = "/{id}")
    public void delete(@PathParam("id") Long id) {
        Task task = repository.findById(id);
        repository.delete(task);
    }
}
