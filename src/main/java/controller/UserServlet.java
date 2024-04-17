package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDTO;
import service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private  UserService userService;
    private  ObjectMapper objectMapper;

    public UserServlet() {}

    //Метод добавил так как просто пустой конструктор требует инициализации объектов
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        this.userService = (UserService) context.getAttribute("userService");
        this.objectMapper = new ObjectMapper();
    }

    public UserServlet(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("id");
        if (userIdParam != null) {
            Long userId = Long.parseLong(userIdParam);
            UserDTO user = userService.getUserById(userId);
            if (user != null) {
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), user);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            List<UserDTO> users = userService.getAllUsers();
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), users);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        userService.createUser(user);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        UserDTO user = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        user.setId(userId);
        userService.updateUser(user);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        userService.deleteUser(userId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
