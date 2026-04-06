package university.storage;

import com.google.gson.reflect.TypeToken;
import university.domain.Faculty;
import university.dto.FacultyDto;
import university.mapper.Mapper;
import university.mapper.FacultyMapper;
import university.repository.Repository;
import university.repository.FacultyRepository;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FacultyStorageManager extends StorageManager<Faculty, FacultyDto, String> {

    private final Mapper<Faculty, FacultyDto> mapper = new FacultyMapper();
    private final Path filePath = Paths.get("data", "faculties.json");

    @Override
    protected Path getFilePath() {
        return filePath;
    }

    @Override
    protected Mapper<Faculty, FacultyDto> getMapper() {
        return mapper;
    }

    @Override
    protected Repository<Faculty, String> getRepository() {
        return Repository.get(FacultyRepository.class);
    }

    @Override
    protected Type getDtoListType() {
        return new TypeToken<List<FacultyDto>>() {
        }.getType();
    }
}