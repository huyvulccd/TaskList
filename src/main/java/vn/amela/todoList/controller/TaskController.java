package vn.amela.todoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.amela.todoList.dto.Process;
import vn.amela.todoList.dto.Search;
import vn.amela.todoList.model.Task;
import vn.amela.todoList.service.TaskService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    TaskService service;

    private Search search;
    private int currentPageRedirect = 0;

    @GetMapping("")
    public String getListTasks(Model model, @RequestParam("page") Optional<Integer> page) {
        if (Process.getCurrentUsername().isEmpty()) {
            return "redirect:/";// check
        }

        int currentPage = page.orElse(1);
        currentPageRedirect = currentPage;
        int lengthTasks = service.getListTasksByCondition(Search.status, Search.title).size(); // dể làm button phân trang
        List<Integer> pagesDisplay = new ArrayList<>();
        for (int i = 1; i <= lengthTasks; i += 10) {
            int displayMaxPage = 1;
            if (Math.abs(currentPage - (i/10+1)) <= displayMaxPage)
             pagesDisplay.add(i/10+1);
        }

        List<Task> tasks = service.findTasksByPaginated(currentPage, Search.status, Search.title);

        model.addAttribute("listTask", tasks);

        model.addAttribute("firstPage", 1);
        model.addAttribute("lastPage", (lengthTasks - 1) / 10 + 1);
        model.addAttribute("countPage", pagesDisplay);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("task", new Task());
        model.addAttribute("search", new Search());
        model.addAttribute("resultSearch", lengthTasks + " kết quả đưọc tìm thấy, trang (" + currentPage + "/" + ((lengthTasks-1)/10+1) + ")");
        return "TaskList.html";
    }

    @PostMapping("/filter")
    public String getListFilterTasks(@ModelAttribute("@{search}") Search filter) {
        Search.title = filter.getTitle();
        Search.status = filter.getStatus();
        return "redirect:/tasks";
    }/// sau fix lại hiển thị tối đa 2 trang trước đó


    @PostMapping("")
    public String postTask(Task task) {
        service.postTask(task);
        int lengthTasks = service.getListTasksByCondition(Search.status, Search.title).size();
        return "redirect:/tasks/?page=" + ((lengthTasks-1)/10+1); // đến trang có post vừa tạo mới
    }

    @PostMapping("/edit/{id}")
    public String putTask(@ModelAttribute("@{task}") Task task) {
        service.updateTask(task);
        return "redirect:/tasks/?page=" + currentPageRedirect; //
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.deleteTask(id);
        return "redirect:/tasks/?page=1";
    }

    @GetMapping("/exportCSV")
    public String exportToCSV(HttpServletResponse servletResponse) throws IOException {
        if (Process.getCurrentUsername().isEmpty()) {
            return "redirect:/";// check
        }
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"Tasks of "+ Process.getCurrentUsername() +".csv\"");
        service.ExportCSV(servletResponse.getWriter());

        return "redirect:/tasks/?page=" + currentPageRedirect;
    }
}
