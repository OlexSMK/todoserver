package servlets;

import service.ToDoListService;
import templator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ListToDoServlet extends HttpServlet {
    ToDoListService toDoList;
    public ListToDoServlet(ToDoListService toDoList) {
        super();
        this.toDoList = toDoList;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> listMap = new HashMap<>();
        listMap.put("todoList", toDoList.getAll());

        String page = pageGenerator.getPage("todolist.html", listMap);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);

    }
}
