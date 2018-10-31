package dao;

import entry.ToDoEntry;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JdbcToDoDao {
    private final static String SQL_GET_ALL = "SELECT id, name, duedate, priority FROM to_do_list ORDER BY id";
    private final static String SQL_GET_ONE = "SELECT name, duedate, priority FROM to_do_list WHERE id = ?";
    private final static String SQL_ADD_NEW = "INSERT INTO to_do_list(name, duedate, priority) VALUES(?,?,?)";
    private final static String SQL_UPDATE_ONE = "UPDATE to_do_list SET name = ?, duedate = ?, priority =? WHERE id = ?";
    private final static String SQL_REMOVE_ONE = "DELETE FROM to_do_list WHERE id = ?";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:database\\todo.sqlite";
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }

    public List<ToDoEntry> getAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL)) {

            List<ToDoEntry> toDoList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int priority = resultSet.getInt("priority");
                String dueDateString = resultSet.getString("duedate");
                LocalDate dueDate = LocalDate.parse(dueDateString, DATE_TIME_FORMATTER);


                ToDoEntry todo = new ToDoEntry(id, name, dueDate, priority);
                toDoList.add(todo);

            }
            return toDoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't get rows from to_do_list",e);
        }

    }

    public ToDoEntry getById(int id){
        ToDoEntry toDo = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ONE)){
             statement.setString(1, String.valueOf(id));
             ResultSet resultset = statement.executeQuery();
             if(resultset.next()){
                 String name = resultset.getString("name");
                 int priority = resultset.getInt("priority");
                 String dueDateString = resultset.getString("duedate");
                 LocalDate dueDate = LocalDate.parse(dueDateString, DATE_TIME_FORMATTER);
                 toDo = new ToDoEntry(id,name,dueDate,priority);
                if(resultset.next()){
                    throw new SQLException( "More then one row found for id=" + id);
                }
             }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return toDo;
    }

    public int removeById(int id){
        int rowCount;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_ONE)){
             statement.setInt(1, id);
             rowCount = statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return rowCount;
    }

    public int updateById(int id, String name, LocalDate dueDate, int priority){
        int rowCount;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ONE)){
             statement.setInt(4, id);
             statement.setString(1,name);
             statement.setString(2, dueDate.toString());
             statement.setInt(3, priority);
             rowCount = statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return rowCount;
    }

    public void add(ToDoEntry toDo){
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW)) {

             statement.setString(1, toDo.getName());
             statement.setString(2, toDo.getDueDate().toString());
             statement.setInt(3, toDo.getPriority());

             statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
