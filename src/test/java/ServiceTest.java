import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import university.domain.Faculty;
import university.domain.Teacher;
import university.exceptions.FacultyAlreadyExistsException;
import university.repository.FacultyRepository;
import university.service.FacultyService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ServiceTest {

    private static Teacher teacher;
    public static Faculty faculty;
    public FacultyService service = new FacultyService(FacultyRepository.get(FacultyRepository.class));

    @BeforeEach
    public void setupTest(){
        teacher = new Teacher();
        faculty = new Faculty("1", "", "", teacher.getID(), "");
    }

    @Test
    public void testAddSameIdEntity(){
        service.createFaculty(faculty);
        Assertions.assertThrows(FacultyAlreadyExistsException.class, () -> service.createFaculty(faculty));
    }
}
