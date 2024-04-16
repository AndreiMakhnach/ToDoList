package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TaskDTO;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TaskServlet extends HttpServlet {
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

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
