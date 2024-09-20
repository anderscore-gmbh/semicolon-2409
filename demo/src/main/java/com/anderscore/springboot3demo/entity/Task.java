package com.anderscore.springboot3demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    private String title;
    
    private Priority priority;
    
    private boolean done;

    public Task() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (done != task.done) return false;
        if (!id.equals(task.id)) return false;
        if (!title.equals(task.title)) return false;
        return priority == task.priority;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
