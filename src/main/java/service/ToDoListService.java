package service;

import entry.ToDoEntry;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToDoListService {
    List<ToDoEntry> listToDo;

    public ToDoListService() {
        this.listToDo = new ArrayList<>();
        try{
            BufferedReader fileList = new BufferedReader(new InputStreamReader(new FileInputStream("resources\\todo.file")));
            String line;
            while((line = fileList.readLine()) != null){
                if (!line.equals("")) {
                    String[] attr = line.split(";", 3);
                    listToDo.add(new ToDoEntry(attr[0], LocalDate.parse(attr[1], DateTimeFormatter.ofPattern("yyyy-MM-d")), Integer.parseInt(attr[2])));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void add(ToDoEntry item){
        listToDo.add(item);
    }

    public void remove(Integer toDoId){
        Iterator iterator = listToDo.iterator();
        boolean taskFound = false;
        while(iterator.hasNext()){
            ToDoEntry toDo = (ToDoEntry) iterator.next();
            if(toDo.getId()==toDoId.intValue()){
                iterator.remove();
                taskFound = true;
                break;
            }
        }
        if(!taskFound) {
            throw new NullPointerException("Could't remove task with id# " + toDoId);
        }
    }

    public ToDoEntry get(Integer toDoId){
        ToDoEntry toDo = null;
        Iterator iterator = listToDo.iterator();
        while(iterator.hasNext()){
            toDo = (ToDoEntry) iterator.next();
            if(toDo.getId()==toDoId.intValue()){
                break;
            }
            toDo =null;
        }
        return toDo;
    }
    public void set(int id, String name,LocalDate dueDate,int priority){
        ToDoEntry toDo = get(id);
        toDo.setName(name);
        toDo.setDueDate(dueDate);
        toDo.setPriority(priority);
    }

    public List<ToDoEntry> getAll(){
        return listToDo;
    }

}
