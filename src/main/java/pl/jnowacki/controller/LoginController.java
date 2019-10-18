package pl.jnowacki.controller;

import org.apache.commons.lang3.StringUtils;
import pl.jnowacki.service.UserService;
import pl.jnowacki.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        boolean isActivated = false;

        if(StringUtils.isNotEmpty(token)) {
            isActivated = userService.activateUser(token);
        }

        if(isActivated) {
            resp.getWriter().println("Aktywowano twojego uzytkownika!");
        } else {
            resp.getWriter().println("Cos poszlo nie tak :(");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        switch (action) {
            case "login":
                login(req, resp);
                break;
            case "register":
                register(req, resp);
                break;
            case "logout":
            default:
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userName = req.getParameter("email");
        String password = req.getParameter("password");

        if (StringUtils.isNotEmpty(userName.trim()) &&
                StringUtils.isNotEmpty(password.trim()) &&
                userService.isUserValid(userName, password)) {

            req.getSession().setAttribute("username", userName);
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/?error=true");
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userName = req.getParameter("email");
        String password = req.getParameter("password");

        if (StringUtils.isNotEmpty(userName.trim()) &&
                StringUtils.isNotEmpty(password.trim())) {

            userService.registerUser(userName, password, req.getContextPath());
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/?error=true");
        }
    }
}
