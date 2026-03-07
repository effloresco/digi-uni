package university.repository;

import university.domain.Entity;
import university.domain.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public abstract class Repository<T extends Entity<ID>, ID> {
    HashMap<ID, T> storage = new HashMap<>();
    public void add(T entity){
        storage.put(entity.getID(), entity);
    }
    public void deleteByID(ID id){
        storage.remove(id);
    }
    public Optional<T> findById(ID id){
        return Optional.ofNullable(storage.get(id));
    }
    public List<T> findAll(){
        return new ArrayList<>(storage.values());
    }
    private static final HashMap<Class<? extends Repository>, Repository<?, ?>> instances = new HashMap<>();
    public static <R extends Repository<?, ?>> R get(Class<R> repositoryClass) {
        try {
            if (!instances.containsKey(repositoryClass)) {
                instances.put(repositoryClass, repositoryClass.getDeclaredConstructor().newInstance());
            }
            return (R) instances.get(repositoryClass);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Не вдалося створити екземпляр репозиторію: " + repositoryClass.getSimpleName(), e);
        }
    }
}
