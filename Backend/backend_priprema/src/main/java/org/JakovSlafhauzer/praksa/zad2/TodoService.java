package org.JakovSlafhauzer.praksa.zad2;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class TodoService {

    private final Map<Long, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Todo> getAll() {
        return new ArrayList<>(todos.values());
    }

    public Todo create(String title, Boolean completed) {
        long id = nextId.getAndIncrement();
        boolean completedValue = completed != null && completed;
        Todo todo = new Todo(id, title, completedValue);
        todos.put(id, todo);
        return todo;
    }

    public Optional<Todo> update(long id, String newTitle, Boolean newCompleted) {
        Todo existing = todos.get(id);
        if (existing == null) {
            return Optional.empty();
        }

        String title = newTitle != null ? newTitle : existing.title();
        boolean completed = newCompleted != null ? newCompleted : existing.completed();

        Todo updated = new Todo(id, title, completed);
        todos.put(id, updated);
        return Optional.of(updated);
    }

    public boolean delete(long id) {
        return todos.remove(id) != null;
    }
}
