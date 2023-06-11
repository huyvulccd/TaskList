package vn.amela.todoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.amela.todoList.dto.Process;
import vn.amela.todoList.dto.Search;
import vn.amela.todoList.model.User;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("")
    public String redirectLoginPage() {
        Search.resetFilter();
        if (!Process.getCurrentUsername().isEmpty()){
            return "redirect:/tasks";
        }
        return "redirect:login";
    }

    @GetMapping("login")
    public String loginPage(Model model) {
        if (!Process.getCurrentUsername().isEmpty()) {
            Search.resetFilter();
            return "redirect:/tasks/?page=1";
        }
        model.addAttribute("user", new User());
        return "Login.html";
    }

    @GetMapping("/login/false")
    public String loginPageFalse(Model model) {
        if (!Process.getCurrentUsername().isEmpty()) {
            Search.resetFilter();
            return "redirect:/tasks/?page=1";
        }

        model.addAttribute("user", new User());
        model.addAttribute("errRes", "Login failed! Wrong username or password.");
        return "Login.html";
    }

    @GetMapping("register")
    public String registerPage(Model model) {
        if (!Process.getCurrentUsername().isEmpty()) {
            Search.resetFilter();
            return "redirect:/tasks/?page=1";
        }
        model.addAttribute("user", new User());
        if (Process.notificationError)
            model.addAttribute("msgError", Process.msgError);
        Process.notificationError = false;
        return "Register.html";

    }
}
