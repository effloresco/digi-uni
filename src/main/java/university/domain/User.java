package university.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User implements Entity<Integer>{
    public static final int PERMISSION_MANAGE_USERS = 4;
    public static int PERMISSION_EDIT = 2;
    public static final int PERMISSION_VIEW = 1;

    @Setter
    private int id;
    private String username;
    private String password;
    private int userPermissions;


    public User(String username, String password, int userPermissions) {
        this.id = 0;
        this.username = username;
        this.password = password;
        this.userPermissions = userPermissions;
    }

    public User(int id, String username, String password, int userPermissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userPermissions = userPermissions;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return userPermissions;
    }

    public void setRole(int userPermissions) {
        this.userPermissions = userPermissions;
    }

    @Override
    public Integer getID() {
        return id;
    }
}