package university.service;


import university.domain.User;
import university.exceptions.UserAlreadyExistsException;
import university.exceptions.UserNotFoundException;
import university.repository.Repository;

import java.util.Optional;

public class UserService {
    public static User.UserRole currentUser = null;

    private final Repository<User, Integer> userRepository;

    public UserService(Repository<User, Integer> repository) {
        this.userRepository = repository;
    }

    public void createUser(User user){
        Optional<User> testCopy = userRepository.findById(user.getID());
        testCopy.ifPresent(
                exists -> {throw new UserAlreadyExistsException("Не вдалось додати користувача з id " + user.getID() + " причина: користувач вже існує");}
        );
        userRepository.add(user);
    }
    public void deleteUser(User user){
        Optional<User> testCopy = userRepository.findById(user.getID());
        testCopy.orElseThrow(
                () -> new UserNotFoundException("Не вдалось видалити користувача з id " + user.getID() + " причина: не знайдено в репозиторії")
        );
        userRepository.deleteByID(user.getID());
    }
    public void updateUser(int currentId, User user){
        Optional<User> testCopy = userRepository.findById(currentId);
        testCopy.orElseThrow(
                () -> new UserNotFoundException("Не вдалось оновити користувача з id " + currentId + " причина: не знайдено в репозиторії")
        );
        int newId = user.getID();
        if(currentId == newId){
            userRepository.findById(newId).ifPresent(
                    exists -> {throw new UserAlreadyExistsException("Не вдалось оновити користувача з id " + currentId + " причина: користувач з id " + newId + " вже існує");}

            );
        }
        userRepository.deleteByID(currentId);
        userRepository.add(user);
    }
}
