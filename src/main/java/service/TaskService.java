package service;

import dto.TaskDTO;
import mapper.TaskMapper;
import repository.TaskRepository;

import java.util.List;

public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO getTaskById(Long id) {
        return taskMapper.toDTO(taskRepository.findById(id));
    }

    public List<TaskDTO> getAllTasks() {
        return taskMapper.toDTOList(taskRepository.findAll());
    }

    public void createTask(TaskDTO taskDTO) {
        taskRepository.save(taskMapper.toEntity(taskDTO));
    }

    public void updateTask(TaskDTO taskDTO) {
        taskRepository.update(taskMapper.toEntity(taskDTO));
    }

    public void deleteTask(Long id) {
        taskRepository.delete(id);
    }
}
