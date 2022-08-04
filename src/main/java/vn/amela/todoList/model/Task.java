package vn.amela.todoList.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "tasklisttable")
public class Task {
    @Id
    Long id;
    @Column(name = "id_user")
    private Long id_user;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private int status;

    public static final int DONE = 3;
    public static final int IN_PROGRESS = 2;
    public static final int OPEN = 1;

    public Task() {
    }

    public Task(String title, String content, int status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
