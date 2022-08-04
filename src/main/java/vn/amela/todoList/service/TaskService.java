package vn.amela.todoList.service;

import vn.amela.todoList.model.Task;

import java.io.Writer;
import java.util.List;

public interface TaskService {
    public List<Task> getListTasksByCondition(int status, String keyword);

    public int postTask(Task task);

    public int updateTask(Task task);

    public void deleteTask(Long id);

    public void ExportCSV(Writer writer);
}
