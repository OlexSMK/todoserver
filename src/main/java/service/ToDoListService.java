package service;

import dao.JdbcToDoDao;
import entry.ToDoEntry;
import exception.ToDoException;

import java.time.LocalDate;
import java.util.List;

public class ToDoListService {
    JdbcToDoDao jdbcToDoDao;

    public ToDoListService() {
        jdbcToDoDao = new JdbcToDoDao();
    }

    public void add(ToDoEntry item){
        jdbcToDoDao.add(item);
    }

    public void remove(Integer toDoId) throws ToDoException{
        int items = jdbcToDoDao.removeById(toDoId);
        if(items ==0) {
            throw new ToDoException("ToDo task was not removed with id " + toDoId);
        }
    }

    public ToDoEntry get(Integer toDoId) throws ToDoException{
        ToDoEntry toDo = jdbcToDoDao.getById(toDoId);
        if(toDo == null){
            throw new ToDoException("ToDo task was not founded with id " + toDoId);
        }
        return toDo;
    }
    public void set(int toDoId, String name,LocalDate dueDate,int priority) throws ToDoException {
        ToDoEntry toDo = get(toDoId);
        if(!toDo.getName().equals(name) || !toDo.getDueDate().equals(dueDate) || toDo.getPriority()!=priority){
            int items = jdbcToDoDao.updateById(toDoId,name,dueDate,priority);
            if(items==0){
                throw new ToDoException("ToDo task was not updated with id " + toDoId);
            }
        }
    }

    public List<ToDoEntry> getAll(){
        return jdbcToDoDao.getAll();
    }

}
