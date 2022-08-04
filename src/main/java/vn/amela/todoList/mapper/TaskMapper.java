package vn.amela.todoList.mapper;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;
import vn.amela.todoList.model.Task;

import java.util.List;

@Repository
public interface TaskMapper {
    List<Task> selectAll();
    List<Task> selectAllById_userAndTitleContaining(@Param("id_user") Long id_user, @Param("title") String title);
    List<Task> selectAllById_userAndTitleContainingAndStatus(@Param("id_user") Long id_user, @Param("title") String title, @Param("status") int status);
    List<Task> findAllById_user(@Param("id_user") Long id_user);
    int creatTask(@Param("task") Task task);
    int updateTask(@Param("task") Task task);
    int deleteById(@Param("id") Long id);
}
