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

public class EditToDoServlet extends HttpServlet{
    ToDoListService toDoList;
    private final static String BASE_EDIT ="/todolist/edit/";
    public EditToDoServlet(ToDoListService toDoList) {
        super();
        this.toDoList = toDoList;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> listMap = new HashMap<>();
        String page;
        String sourcePage;
        String requested = request.getRequestURI().replaceFirst(BASE_EDIT,"");
        try {
                int toDoId= Integer.valueOf(requested);
            listMap.put("todo",toDoList.get(toDoId));
            sourcePage="edittask.html";
        }catch (Exception e){
            e.printStackTrace();
            listMap.put("requested",requested);
            listMap.put("action","updated");
            sourcePage="notfound.html";
        }
        page = pageGenerator.getPage(sourcePage, listMap);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requested = request.getRequestURI().replaceFirst(BASE_EDIT, "");
        try {
            int toDoId = Integer.parseInt(requested);
            String name = request.getParameter("name");
            int priority = Integer.parseInt(request.getParameter("priority"));
            LocalDate dueDate = LocalDate.parse(request.getParameter("DueDate"), DateTimeFormatter.ofPattern("yyyy-MM-d"));

            toDoList.set(toDoId, name, dueDate, priority);
            response.sendRedirect("/todolist");
        }catch (Exception e){
            PageGenerator pageGenerator = PageGenerator.instance();
            Map<String,Object> listMap= new HashMap<>();
            listMap.put("requested",requested);
            listMap.put("action","modification");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(pageGenerator.getPage("notfound.html", listMap));
        }


    }
}
