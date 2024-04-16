import controller.UserServlet;
import controller.TaskServlet;
import repository.TaskRepositoryImpl;
import repository.UserRepository;
import repository.TaskRepository;
import mapper.UserMapper;
import mapper.TaskMapper;
import repository.UserRepositoryImpl;
import service.UserService;
import service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

public class AppLauncher implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        try {
            // Получение соединения с базой данных
            Connection connection = getConnection("jdbc:postgresql://localhost:5432/TodoList", "postgres", "boba312");

            // Инициализация всех компонентов
            UserRepository userRepository = new UserRepositoryImpl(connection);
            TaskRepository taskRepository = new TaskRepositoryImpl(connection);
            UserMapper userMapper = new UserMapper();
            TaskMapper taskMapper = new TaskMapper();
            UserService userService = new UserService(userRepository, userMapper);
            TaskService taskService = new TaskService(taskRepository, taskMapper);
            ObjectMapper objectMapper = new ObjectMapper();

            // Регистрация сервлетов и связывание их с URL-шаблонами
            ServletRegistration.Dynamic userServlet = ctx.addServlet("userServlet", new UserServlet(userService, objectMapper));
            userServlet.addMapping("/users/*");

            ServletRegistration.Dynamic taskServlet = ctx.addServlet("taskServlet", new TaskServlet(taskService, objectMapper));
            taskServlet.addMapping("/tasks/*");
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize application", e);
        }
    }

    // Метод для получения соединения с базой данных
    private Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}