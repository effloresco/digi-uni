package university.domain;

public class User implements Entity<Integer>{
    public enum UserRole {ADMIN, MANAGER, USER}

    private static int currentId = 0;

    private final int id;
    private String username;
    private String password;
    private UserRole role;


    public User(String username, String password, UserRole role) {
        this.id = currentId;
        currentId++;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public Integer getID() {
        return id;
    }
}
