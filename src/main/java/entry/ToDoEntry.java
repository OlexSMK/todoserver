package entry;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

public class ToDoEntry {
    private int id ;
    private String name;
    private LocalDate dueDate;
    private int priority;
    private static int toDoNextId = 1;

    public ToDoEntry( String name, LocalDate dueDate, int priority) {
        this.id = toDoNextId++;
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoEntry toDoEntry = (ToDoEntry) o;
        return id == toDoEntry.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ToDoEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                '}';
    }
}
