package university.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private final int id;
    private final String username;
    private final String password;
    private final int userPermissions;
    public UserDto(int id, String username, String password, int userPermissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userPermissions = userPermissions;
    }
}
