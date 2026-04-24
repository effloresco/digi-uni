import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import university.domain.User;
import university.repository.FacultyRepository;
import university.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private static User user;
    public UserRepository repo;

    @BeforeEach
    public void setupTest(){
        user = new User("test", "12345678", 7);
        repo = new UserRepository();
    }

    @Test
    public void testReuseUsername(){
        repo.add(user);
        repo.deleteByID(user.getID());
        repo.add(user);
        assertEquals(repo.findById(2).get(), user, "should be equal");
    }
    @Test
    public void testUsernameCheck(){
        repo.add(user);
        repo.checkUsername(user.getUserName());
        assertTrue(repo.checkUsername(user.getUserName()));
        repo.deleteByID(user.getID());
        assertFalse(repo.checkUsername(user.getUserName()));
    }
}
