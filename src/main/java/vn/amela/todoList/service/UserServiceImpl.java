package vn.amela.todoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.amela.todoList.mapper.UserMapper;
import vn.amela.todoList.model.ResponseUser;
import vn.amela.todoList.model.User;

import javax.jws.soap.SOAPBinding;
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

//    @Override
//    public boolean login(String username, String password) {
//        List<User> listUsers = userRepsitory.findAll();
//        for (User x_User : listUsers) {
//            if (x_User.getUsername().compareTo(username)==0 && x_User.getPassword().compareTo(password)==0){
//                Process.username = x_User.getUsername();
//                Process.id = x_User.getId_User();
//                return true;
//            }
//        }
//        Process.username = null;
//        return false;
//    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        List<User> listUsers = userMapper.findAll();
        for (User user : listUsers){
            if (user.getUsername().compareTo(username) == 0 && user.getPassword().compareTo(oldPassword) == 0){
                user.setPassword(new BCryptPasswordEncoder(4).encode(newPassword));
                userMapper.updatePassword(user);
            }
        }
    }

    @Override
    public void deleteAccount(String username, String password) {
        List<User> listUsers = userMapper.findAll();
        for (User user : listUsers){
            if (user.getUsername().compareTo(username) == 0 && user.getPassword().compareTo(password) == 0){
                userMapper.deleteById(user.getId_User());
                return;
            }
        }
    }

    @Query(value = "SELECT e.* FROM accounttable e Where e.username like %?1%", nativeQuery = true)
    public User findByUsername(String username) {
        return userMapper.findOneByUsername(username);
    }
}
