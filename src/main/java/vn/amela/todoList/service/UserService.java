package vn.amela.todoList.service;

import vn.amela.todoList.model.ResponseUser;
import vn.amela.todoList.model.User;

public interface UserService {
    public ResponseUser RegisterAccount(User user);
}
