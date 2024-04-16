package repository;

import entity.Task;

import java.util.List;

public interface TaskRepository {
    Task findById(Long id);
    List<Task> findAll();
    void save(Task task);
    void update(Task task);
    void delete(Long id);
}
