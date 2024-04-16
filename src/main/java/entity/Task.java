package entity;

import lombok.Data;

@Data
public class Task {
    private Long id;
    private String name;
    private String description;
    private boolean completed;
    private Long userId;
}
