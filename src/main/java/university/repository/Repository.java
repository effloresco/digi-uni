package university.repository;

import university.domain.Entity;

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
}
