package dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private boolean completed;
    private Long userId;
}
