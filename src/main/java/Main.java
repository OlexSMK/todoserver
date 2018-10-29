import entry.ToDoEntry;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.ToDoListService;
import servlets.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        ToDoListService toDoList = new ToDoListService();
        ListToDoServlet listServletRequest = new ListToDoServlet(toDoList);
        AddToDoServlet addServletRequest = new AddToDoServlet(toDoList);
        DeleteToDoServlet deleteServletRequest = new DeleteToDoServlet(toDoList);
        EditToDoServlet editServletRequest = new EditToDoServlet(toDoList);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(listServletRequest), "/todolist");
        context.addServlet(new ServletHolder(addServletRequest), "/todolist/add");
        context.addServlet(new ServletHolder(deleteServletRequest), "/todolist/delete/*");
        context.addServlet(new ServletHolder(editServletRequest), "/todolist/edit/*");


        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }

}
