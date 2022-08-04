package vn.amela.todoList.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.amela.todoList.mapper.TaskMapper;
import vn.amela.todoList.dto.Search;
import vn.amela.todoList.model.Task;
import vn.amela.todoList.dto.Process;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Task> getListTasksByCondition(int status, String keyword) {// trả về list theo điều kiện
        if (status == 0){
            return taskMapper.selectAllById_userAndTitleContaining(Process.getID_User(),keyword);
        }
        return taskMapper.selectAllById_userAndTitleContainingAndStatus(Process.getID_User(),keyword,status);
    }
    @Override
    public int postTask(Task task) {
        if (task.getTitle().isEmpty())
            return -1;
        List<Task> tasks = taskMapper.selectAll();
        int tasksLength = tasks.size();
        if (tasksLength==0){
            task.setId(1L); // nếu list trống thì +1
        }else{
            task.setId(tasks.get(tasksLength-1).getId()+1); // lấy id phần tử cuối sau đó +1 vào id mới
        }
        task.setStatus(1);
        task.setId_user(Process.getID_User());
        return taskMapper.creatTask(task);
    }

    @Override
    public int updateTask(Task task) {
        return taskMapper.updateTask(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskMapper.deleteById(id);
    }

    public List<Task> findTasksByPaginated(int pageable, int status, String keyword) {
        List<Task> tasks = getListTasksByCondition(status, keyword);
        int length = tasks.size();
        return tasks.subList((pageable - 1) * 10, pageable * 10 >= length ? length : pageable * 10);
    }
    @Override
    public void ExportCSV(Writer writer) {
        List<Task> tasks = getListTasksByCondition(Search.status, Search.title);

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            csvPrinter.printRecord("Title", "Content", "Status");
            for (Task task: tasks) {
                csvPrinter.printRecord(task.getTitle(), task.getContent(), task.getStatus()==1 ? "Open" : task.getStatus() ==2 ? "Doing" : "Done");
            }
        } catch (IOException e) {
        }
    }

}
