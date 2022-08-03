package vn.amela.todoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.amela.todoList.model.Process;
import vn.amela.todoList.model.Search;
import vn.amela.todoList.model.Task;
import vn.amela.todoList.service.TaskServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("tasks")
public class TaskController {
    private final int displayMaxPage = 1;
    @Autowired
    private TaskServiceImpl taskServiceImpl;
    private Search search;

    @GetMapping("")
    public String getListTasks(Model model, @RequestParam("page") Optional<Integer> page) {

        if (Process.getCurrentUsername().isEmpty()) {
            return "redirect:/";
        }

        int currentPage = page.orElse(1);
        int lengthTasks = taskServiceImpl.getListTasksByCondition(search.status, search.title).size(); // dể làm button phân trang
        List<Integer> count_page = new ArrayList<>();
        for (int i = 1; i <= lengthTasks; i += 10) {
            if (Math.abs(currentPage - (i/10+1)) <= displayMaxPage)
            count_page.add(i/10+1);
        }

        List<Task> tasks = taskServiceImpl.findTasksByPaginated(currentPage, search.status, search.title);

        model.addAttribute("listTask", tasks);

        model.addAttribute("firstPage", 1);
        model.addAttribute("lastPage", (lengthTasks - 1) / 10 + 1);
        model.addAttribute("countPage", count_page);

        model.addAttribute("task", new Task());
        model.addAttribute("search", new Search());
        model.addAttribute("resultSearch", lengthTasks + " kết quả đưọc tìm thấy, trang (" + currentPage + "/" + ((lengthTasks-1)/10+1) + ")");
        return "TaskList.html";
    }

    @PostMapping("/filter")
    public String getListFilterTasks(@ModelAttribute("@{search}") Search filter) {
        search.title = filter.getTitle();
        search.status = filter.getStatus();
        return "redirect:/tasks";
    }/// sau fix lại hiển thị tối đa 2 trang trước đó


    @PostMapping("")
    public String postTask(Task task) {
        taskServiceImpl.postTask(task);
        int lengthTasks = taskServiceImpl.getListTasksByCondition(search.status, search.title).size();
        return "redirect:/tasks/?page=" + ((lengthTasks-1)/10+1); // đến trang có post vừa tạo mới
    }

    @PostMapping("/edit/{id}")
    public String putTask(@ModelAttribute("@{task}") Task task) {
        taskServiceImpl.updateTask(task);
        return "redirect:/tasks/?page=1";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        taskServiceImpl.deleteTask(id);
        return "redirect:/tasks/?page=1";
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) {
    }
}
