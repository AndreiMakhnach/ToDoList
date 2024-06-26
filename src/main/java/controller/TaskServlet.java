package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TaskDTO;
import service.TaskService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {
    private  TaskService taskService;
    private  ObjectMapper objectMapper;

    public TaskServlet() {}

    //Метод добавил так как просто пустой конструктор требует инициализации объектов
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.taskService = (TaskService) context.getAttribute("taskService");
        if (this.taskService == null) {
            throw new ServletException("TaskService not initialized. Make sure it is properly configured and injected.");
        }
        this.objectMapper = new ObjectMapper();
    }

    public TaskServlet(TaskService taskService, ObjectMapper objectMapper) {
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskIdParam = req.getParameter("id");
        if (taskIdParam != null) {
            Long taskId = Long.parseLong(taskIdParam);
            TaskDTO task = taskService.getTaskById(taskId);
            if (task != null) {
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), task);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            List<TaskDTO> tasks = taskService.getAllTasks();
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), tasks);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskDTO taskDTO = objectMapper.readValue(req.getInputStream(), TaskDTO.class);
        taskService.createTask(taskDTO);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long taskId = Long.parseLong(req.getParameter("id"));
        TaskDTO taskDTO = objectMapper.readValue(req.getInputStream(), TaskDTO.class);
        taskDTO.setId(taskId);
        taskService.updateTask(taskDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long taskId = Long.parseLong(req.getParameter("id"));
        taskService.deleteTask(taskId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
