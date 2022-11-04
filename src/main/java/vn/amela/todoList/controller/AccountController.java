package vn.amela.todoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.amela.todoList.model.ResponseUser;
import vn.amela.todoList.model.User;
import vn.amela.todoList.dto.Process;
import vn.amela.todoList.service.UserServiceImpl;

@Controller
@RequestMapping("account")
public class AccountController {
    @PostMapping("login")
    public String pageLogin(Model model) {
        model.addAttribute("user", new User());
        return "Login.html";
    }

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("register")
    public String pageRegister(@ModelAttribute("@{user}") User user) {
        ResponseUser responseUser = userServiceImpl.RegisterAccount(user);

        if (!responseUser.isStatus()) {
            Process.notificationError = true;
            Process.msgError = responseUser.getMessage();
            return "redirect:/register";
        }
        return "redirect:/";
    }
}
