package university.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import university.domain.Department;
import university.domain.Faculty;
import university.domain.Person;
import university.dto.ServiceDto;
import university.mapper.Mapper;
import university.repository.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class ServiceStorageManager {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

    private final Path path = Paths.get("data", "service.json");

    public void saveAllData() {
        try {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            ServiceDto serviceDto = new ServiceDto();

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(serviceDto, writer);
            }
            System.out.println("Дані успішно збережено у файл: " + path.getFileName());

        } catch (Exception e) {
            System.err.println("Помилка збереження " + path.getFileName() + ": " + e.getMessage());
        }
    }

    public void loadAllData() {
        if (Files.notExists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            ServiceDto serviceDto = gson.fromJson(reader, ServiceDto.class);

            if (serviceDto != null) {
                Person.setIdCounter(serviceDto.getPersonCounter());
                Department.setIdCounter(serviceDto.getDepartmentCounter());
                Faculty.setIdCounter(serviceDto.getFacultyCounter());

                System.out.println("Дані успішно завантажено з: " + path.getFileName());
            }
        } catch (Exception e) {
            System.err.println("Помилка завантаження " + path.getFileName() + ": " + e.getMessage());
        }
    }
}
