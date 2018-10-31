package servlets;

import entry.ToDoEntry;
import service.ToDoListService;
import templator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AddToDoServlet extends HttpServlet {
    ToDoListService toDoList;
    public AddToDoServlet(ToDoListService toDoList) {
        super();
        this.toDoList = toDoList;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addtask.html", null);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            int priority = Integer.parseInt(request.getParameter("priority"));

            LocalDate dueDate = LocalDate.parse(request.getParameter("DueDate"), DateTimeFormatter.ofPattern("yyyy-MM-d"));
            ToDoEntry toDo  = new ToDoEntry(-1,name,dueDate,priority);
            toDoList.add(toDo);

            responce.sendRedirect("/todolist");
        }catch (Exception e){
            e.printStackTrace();
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String,Object> listMap= new HashMap<>();
            listMap.put("requested","new task");
            listMap.put("action","add");
            responce.setContentType("text/html;charset=utf-8");
            responce.getWriter().write(pageGenerator.getPage("notfound.html", listMap));

        }

    }
}
