package vn.amela.todoList.dto;

public class Search {
    public static int status = 0;
    public static String title = "";
    public Search() {
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public static void resetFilter(){
        status = 0;
        title = "";
    }
}
