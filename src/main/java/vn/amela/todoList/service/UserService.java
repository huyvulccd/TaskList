package vn.amela.todoList.service;

import vn.amela.todoList.model.ResponseUser;
import vn.amela.todoList.model.User;

public interface UserService {
    public ResponseUser RegisterAccount(User user);
//    public boolean login(String username, String password);
    public void changePassword(String username, String oldPassword, String newPassword);
    public void deleteAccount(String username, String password);
}
