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
    protected final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final FacultyService facultyService = new FacultyService(facultyRepository);
    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);

    protected void facultyManaging() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління факультетами-*");
            System.out.println("1 - Додати факультет");
            System.out.println("2 - Змінити факультет");
            System.out.println("3 - Видалити факультет");
            System.out.println("0 - Повернутись назад");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addFaculty();
                        break;
                    case 2:
                        changeFaculty();
                        break;
                    case 3:
                        deleteFaculty();
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
        Teacher dean = receiveDean();
        System.out.println("Введіть контакти факультету");
        String contacts = scanner.nextLine();
        return new Faculty(code, name, shortName, dean, contacts);
    }

    protected Teacher receiveDean(){
        Teacher dean = null;
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор декана(залиште поле пустим, щоб додати пізніше)");
            String teacherId = scanner.nextLine();

            if(!teacherId.isEmpty()){
                Optional<Teacher> optionalPerson = teacherRepository.findById(teacherId);

                if (optionalPerson.isPresent()) {
                    dean = optionalPerson.get();
                    found = true;
                } else {
                    System.out.println("Особу з таким ID не знайдено.");
                }
            }else found = true;
        }
        return dean;
    }

    protected void addFaculty() {
        facultyService.createFaculty(facultyGenerator());
    }

    protected void deleteFaculty() {
        boolean found = false;
        boolean exit = false;
        Faculty faculty = null;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор факультету, що треба видалити (введіть 0 щоб повернутись назад)");
            String facultyId = scanner.nextLine();

            if (facultyId.equals("0"))
                exit = true;
            else{
                Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

                if (optionalFaculty.isPresent()) {
                    faculty = optionalFaculty.get();
                    found = true;
                } else
                    System.out.println("Факультет з таким ID не знайдено.");
            }
        }
        if (!exit)
            facultyService.deleteFaculty(faculty);
    }

    protected void changeFaculty() {
        boolean found = false;
        boolean exit = false;
        String facultyId = null;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор факультету, що треба замінити (введіть 0 щоб повернутись назад)");
            facultyId = scanner.nextLine();

            if (facultyId.equals("0"))
                exit = true;
            else{
                Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

                if (optionalFaculty.isPresent()) {
                    found = true;
                } else
                    System.out.println("Факультет з таким ID не знайдено.");
            }
        }
        if (!exit)
            if (facultyRepository.findById(facultyId).get().getDean() == null){
                System.out.println("Додати лише декана? y/n");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "y":
                        Faculty faculty = facultyRepository.findById(facultyId).get();
                        new Faculty(faculty.getID(), faculty.getName(), faculty.getShortName(), receiveDean(), faculty.getContacts());
                        break;
                    case "n":
                        facultyService.updateFaculty(facultyId, facultyGenerator());
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            }

    }
}
