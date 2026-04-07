package university.mapper;

import university.domain.User;
import university.dto.UserDto;

public class UserMapper implements Mapper<User, UserDto>{

    public UserDto toDto(User user) {
        return new UserDto(
                user.getID(),
                user.getUserName(),
                user.getPassword(),
                user.getUserPermissions()
        );
    }

    public User toEntity(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getUserPermissions()
        );
    }
}
