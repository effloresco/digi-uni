package university.ui;

import university.domain.Faculty;
import university.domain.Person;
import university.domain.Teacher;
import university.repository.FacultyRepository;
import university.repository.TeacherRepository;
import university.service.FacultyService;

import static university.service.SearchService.*;

import java.util.Optional;

public class FacultyMenu {
    protected final FacultyRepository facultyRepository = FacultyRepository.get();
    protected final FacultyService facultyService = new FacultyService(facultyRepository);
    protected final TeacherRepository teacherRepository = TeacherRepository.get();

    protected void facultyManaging() {
        System.out.println("\n*-Управління факультетами-*");
        System.out.println("1 - додати факультет");
        System.out.println("2 - змінити факультет");
        System.out.println("3 - видалити факультет");
        System.out.println("0 - повернутись назад");
        boolean status = true;
        while (status) {
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addFaculty();
                        break;
                    case 2:
                        deleteFaculty();
                        break;
                    case 3:
                        changeFaculty();
                        break;
                    case 0:
                        status = false;
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }

    }

    protected Faculty facultyGenerator() {
        System.out.println("Введіть ідентифікатор факультету");
        String code = scanner.nextLine();
        System.out.println("Введіть назву факультету");
        String name = scanner.nextLine();
        System.out.println("Введіть коротку назву факультету");
        String shortName = scanner.nextLine();
        Teacher dean = null;
        boolean found = false;

        while (!found) {
            System.out.println("Введіть ідентифікатор декана");
            String teacherId = scanner.nextLine();

            Optional<Person> optionalPerson = teacherRepository.findById(teacherId);

            if (optionalPerson.isPresent()) {
                Person person = optionalPerson.get();
                if (person instanceof Teacher t) { // Pattern matching
                    dean = t;
                    found = true;
                } else {
                    System.out.println("Ця особа не є викладачем");
                }
            } else {
                System.out.println("Особу з таким ID не знайдено.");
            }
        }

        System.out.println("Введіть контакти факультету");
        String contacts = scanner.nextLine();
        return new Faculty(code, name, shortName, dean, contacts);
    }

    protected void addFaculty() {
        facultyService.createFaculty(facultyGenerator());
    }

    protected void deleteFaculty() {
        boolean found = false;
        Faculty faculty = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор факультету, що треба видалити");
            String facultyId = scanner.nextLine();

            Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

            if (optionalFaculty.isPresent()) {
                faculty = optionalFaculty.get();
                found = true;
            } else
                System.out.println("Факультет з таким ID не знайдено.");
        }
        facultyService.deleteFaculty(faculty);
    }

    protected void changeFaculty() {
        boolean found = false;
        String facultyId = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор факультету, що треба замінити");
            facultyId = scanner.nextLine();

            Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

            if (optionalFaculty.isPresent()) {
                found = true;
            } else
                System.out.println("Факультет з таким ID не знайдено.");
        }
        facultyService.updateFaculty(facultyId, facultyGenerator());
    }
}
