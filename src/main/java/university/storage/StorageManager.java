package university.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import university.domain.Entity;
import university.mapper.Mapper;
import university.repository.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public abstract class StorageManager<E extends Entity<ID>, D, ID> {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

    protected abstract Path getFilePath();
    protected abstract Mapper<E, D> getMapper();
    protected abstract Repository<E, ID> getRepository();
    protected abstract Type getDtoListType();

    public void saveAllData() {
        Path path = getFilePath();
        try {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            List<E> entities = getRepository().findAll();
            List<D> dtos = getMapper().toDtoList(entities);

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(dtos, writer);
            }
            System.out.println("Дані успішно збережено у файл: " + path.getFileName());

        } catch (Exception e) {
            System.err.println("Помилка збереження " + path.getFileName() + ": " + e.getMessage());
        }
    }

    public void loadAllData() {
        Path path = getFilePath();
        if (Files.notExists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<D> dtos = gson.fromJson(reader, getDtoListType());

            if (dtos != null) {
                Repository<E, ID> repo = getRepository();
                for (D dto : dtos) {
                    repo.add(getMapper().toEntity(dto));
                }
                System.out.println("Дані успішно завантажено з: " + path.getFileName());
            }
        } catch (Exception e) {
            System.err.println("Помилка завантаження " + path.getFileName() + ": " + e.getMessage());
        }
    }
}
