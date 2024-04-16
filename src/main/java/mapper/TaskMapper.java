package mapper;

import dto.TaskDTO;
import entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        TaskDTO taskDto = new TaskDTO();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setCompleted(task.isCompleted());
        taskDto.setUserId(task.getUserId());
        return taskDto;
    }

    public Task toEntity(TaskDTO taskDto) {
        if (taskDto == null) {
            return null;
        }
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        task.setUserId(taskDto.getUserId());
        return task;
    }

    public List<TaskDTO> toDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Task> toEntityList(List<TaskDTO> taskDtos) {
        return taskDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
