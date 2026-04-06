package university.storage;

import com.google.gson.reflect.TypeToken;
import university.domain.Teacher;
import university.dto.TeacherDto;
import university.mapper.Mapper;
import university.mapper.TeacherMapper;
import university.repository.Repository;
import university.repository.TeacherRepository;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TeacherStorageManager extends StorageManager<Teacher, TeacherDto, String> {

    private final Mapper<Teacher, TeacherDto> mapper = new TeacherMapper();
    private final Path filePath = Paths.get("data", "teachers.json");

    @Override
    protected Path getFilePath() {
        return filePath;
    }

    @Override
    protected Mapper<Teacher, TeacherDto> getMapper() {
        return mapper;
    }

    @Override
    protected Repository<Teacher, String> getRepository() {
        return Repository.get(TeacherRepository.class);
    }

    @Override
    protected Type getDtoListType() {
        return new TypeToken<List<TeacherDto>>() {
        }.getType();
    }
}