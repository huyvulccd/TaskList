package vn.amela.todoList.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import vn.amela.todoList.model.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    List<User> findAll();
    int registerUser(@Param("user") User user);

    int updatePassword(@Param("user") User user);

    int deleteById(@Param("id") Long id);

    User findOneByUsername(@Param("username") String username);
}
