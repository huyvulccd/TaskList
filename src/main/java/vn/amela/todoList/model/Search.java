package vn.amela.todoList.model;

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

    public Search(int status, String title) {
        this.status = status;
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
