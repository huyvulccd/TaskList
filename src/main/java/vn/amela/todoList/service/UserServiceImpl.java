package vn.amela.todoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.amela.todoList.mapper.UserMapper;
import vn.amela.todoList.model.ResponseUser;
import vn.amela.todoList.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseUser RegisterAccount(User user) {
        long ID = 1;
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty())
            return new ResponseUser("Do not allow empty fields", false);
        List<User> listUsers = userMapper.findAll();
        for (User x_User : listUsers) {
            if (x_User.getUsername().compareTo(user.getUsername())==0){
                return new ResponseUser("Exist username", false);
            }
            if (x_User.getId_User()==ID){
                ID++;
            }
        }
        user.setPassword(new BCryptPasswordEncoder(4).encode(user.getPassword()));
        user.setId(ID);
        userMapper.registerUser(user);
        return new ResponseUser("creat account complete", true);
    }

    @Query(value = "SELECT e.* FROM accounttable e Where e.username like %?1%", nativeQuery = true)
    public User findByUsername(String username) {
        return userMapper.findOneByUsername(username);
    }
}
