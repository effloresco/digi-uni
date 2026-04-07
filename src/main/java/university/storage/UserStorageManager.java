package university.storage;

import com.google.gson.reflect.TypeToken;
import university.domain.User;
import university.dto.UserDto;
import university.mapper.Mapper;
import university.mapper.UserMapper;
import university.repository.Repository;
import university.repository.UserRepository;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UserStorageManager extends StorageManager<User, UserDto, Integer> {

    private final Mapper<User, UserDto> mapper = new UserMapper();
    private final Path filePath = Paths.get("data", "users.json");

    @Override
    protected Path getFilePath() {
        return filePath;
    }

    @Override
    protected Mapper<User, UserDto> getMapper() {
        return mapper;
    }

    @Override
    protected Repository<User, Integer> getRepository() {
        return Repository.get(UserRepository.class);
    }

    @Override
    protected Type getDtoListType() {
        return new TypeToken<List<UserDto>>() {
        }.getType();
    }
}