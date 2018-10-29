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

public class DeleteToDoServlet extends HttpServlet {
    ToDoListService toDoList;
    private final static String BASE_DELETE ="/todolist/delete/";
    public DeleteToDoServlet(ToDoListService toDoList) {
        super();
        this.toDoList = toDoList;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> listMap = new HashMap<>();
        String page;
        String sourcePage;
        String requested = request.getRequestURI().replaceFirst(BASE_DELETE,"");
        try {
            int toDoId= Integer.valueOf(requested);
            listMap.put("todo",toDoList.get(toDoId));
            toDoList.remove(toDoId);
            sourcePage="removed.html";
        }catch (Exception e){
            e.printStackTrace();
            listMap.put("requested",requested);
            listMap.put("action","delete");
            sourcePage="notfound.html";

        }
        page = pageGenerator.getPage(sourcePage, listMap);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(page);
    }
}
