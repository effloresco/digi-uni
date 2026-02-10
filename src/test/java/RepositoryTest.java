import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import university.domain.Faculty;
import university.domain.Teacher;
import university.repository.FacultyRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RepositoryTest {

    private static Teacher teacher;
    public static Faculty faculty1;
    public static Faculty faculty2;
    public static FacultyRepository repo;

    @BeforeAll
    public static void setupTest(){
        teacher = new Teacher("TestTeacherID", "","","", LocalDate.now(), "","","","","",LocalDate.now(),1);
        faculty1 = new Faculty("1", "", "", teacher, "");
        faculty2 = new Faculty("2", "", "", teacher, "");
        repo = new FacultyRepository();
    }
    @Test
    public void testFindAll(){
        repo.add(faculty1);
        repo.add(faculty2);

        assertEquals(faculty1, repo.findAll().get(0), "should be equal");
        assertEquals(faculty2, repo.findAll().get(1), "should be equal");

        assertNotEquals(faculty1, repo.findAll().get(1), "shouldn't be equal");
        assertNotEquals(faculty2, repo.findAll().get(0), "shouldn't be equal");
    }
    @Test
    public void testFindByID(){
        repo.add(faculty1);

        Optional<Faculty> faculty1GotByID = repo.findById(faculty1.getID());
        faculty1GotByID.ifPresent(faculty -> assertEquals(faculty1, faculty, "should be equal"));


        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Optional<Faculty> faculty2GotByID = repo.findById(faculty2.getID());
                    faculty2GotByID.orElseThrow(
                            () -> new IllegalArgumentException("User not found")
                    );
                },
                "IllegalArgumentException was expected" // Optional failure message
        );
    }
}
