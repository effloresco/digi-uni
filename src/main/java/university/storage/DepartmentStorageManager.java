package university.storage;

import com.google.gson.reflect.TypeToken;
import university.domain.Department;
import university.dto.DepartmentDto;
import university.mapper.Mapper;
import university.mapper.DepartmentMapper;
import university.repository.Repository;
import university.repository.DepartmentRepository;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DepartmentStorageManager extends StorageManager<Department, DepartmentDto, String> {

    private final Mapper<Department, DepartmentDto> mapper = new DepartmentMapper();
    private final Path filePath = Paths.get("data", "departments.json");

    @Override
    protected Path getFilePath() {
        return filePath;
    }

    @Override
    protected Mapper<Department, DepartmentDto> getMapper() {
        return mapper;
    }

    @Override
    protected Repository<Department, String> getRepository() {
        return Repository.get(DepartmentRepository.class);
    }

    @Override
    protected Type getDtoListType() {
        return new TypeToken<List<DepartmentDto>>() {
        }.getType();
    }
}