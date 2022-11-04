package vn.amela.todoList.dto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import vn.amela.todoList.model.User;

import java.security.Principal;

public class Process {
    public static String msgError = "";
    public static boolean notificationError = false;
    public static Long getID_User(){
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId_User();
    }
    public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername().compareTo("anonymousUser") == 0 ? "" : ((UserDetails) principal).getUsername();
        }
        if (principal instanceof Principal) {
            return ((Principal) principal).getName().compareTo("anonymousUser") == 0 ? "" : ((Principal) principal).getName();
        }
        return String.valueOf(principal).compareTo("anonymousUser") == 0 ? "" : String.valueOf(principal);
    }
}
