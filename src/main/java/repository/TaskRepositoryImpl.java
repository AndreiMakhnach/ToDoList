package repository;

import entity.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository{

    private Connection connection;

    public TaskRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Task findById(Long id) {
        Task task = null;
        try {
            String query = "SELECT * FROM tasks WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setUserId(resultSet.getLong("user_id"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        try {
            String query = "SELECT * FROM tasks";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setCompleted(resultSet.getBoolean("completed"));
                task.setUserId(resultSet.getLong("user_id"));
                tasks.add(task);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void save(Task task) {
        try {
            String query = "INSERT INTO tasks (name, description, completed, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setLong(4, task.getUserId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {
        try {
            String query = "UPDATE tasks SET name = ?, description = ?, completed = ?, user_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setLong(4, task.getUserId());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            String query = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
