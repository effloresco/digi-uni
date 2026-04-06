package university.storage;

import com.google.gson.reflect.TypeToken;
import university.domain.Student;
import university.dto.StudentDto;
import university.mapper.Mapper;
import university.mapper.StudentMapper;
import university.repository.Repository;
import university.repository.StudentRepository;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StudentStorageManager extends StorageManager<Student, StudentDto, String> {

    private final Mapper<Student, StudentDto> mapper = new StudentMapper();
    private final Path filePath = Paths.get("data", "students.json");

    @Override
    protected Path getFilePath() {
        return filePath;
    }

    @Override
    protected Mapper<Student, StudentDto> getMapper() {
        return mapper;
    }

    @Override
    protected Repository<Student, String> getRepository() {
        return Repository.get(StudentRepository.class); // Твоя рефлексія з абстрактного репозиторію
    }

    @Override
    protected Type getDtoListType() {
        // Кажемо Gson-у, що чекаємо саме List<StudentDto>
        return new TypeToken<List<StudentDto>>(){}.getType();
    }
}