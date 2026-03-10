package university.repository;

import university.domain.User;
import university.exceptions.UserAlreadyExistsException;
import university.exceptions.UsernameAlreadyUsedException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserRepository extends Repository<User, Integer> {
    private static final Set<String> usernames = new HashSet<>();

    public boolean checkUsername(String username){
        return usernames.contains(username);
    }

    @Override
    public void add(User entity){
        String username = entity.getUserName();
        if(usernames.contains(username))
            throw new UsernameAlreadyUsedException("Користувач з таким ім'ям вже існує");
        usernames.add(username);
        super.add(entity);
    }

    @Override
    public void deleteByID(Integer id){
        Optional<User> userOptional = super.findById(id);
        userOptional.ifPresent(user -> usernames.remove(user.getUserName()));
        super.deleteByID(id);
    }
}
