package vn.amela.todoList.service;

import vn.amela.todoList.model.Task;

import java.io.Writer;
import java.util.List;

public interface TaskService {
    public List<Task> getListTasksByCondition(int status, String keyword);

    public void postTask(Task task);

    public void updateTask(Task task);

    public List<Task> findTasksByPaginated(int pageable, int status, String keyword);

    public void deleteTask(Long id);

    public void ExportCSV(Writer writer);
}
