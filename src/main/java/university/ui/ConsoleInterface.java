package university.ui;

import university.domain.Faculty;
import university.domain.Person;
import university.domain.Student;
import university.domain.Teacher;
import university.repository.DepartmentRepository;
import university.repository.FacultyRepository;
import university.repository.PersonRepository;
import university.service.DepartmentService;
import university.service.FacultyService;
import university.service.PersonService;
import static university.service.ReportService.*;
import static university.service.SearchService.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleInterface {

    private final Scanner scanner = new Scanner(System.in);

    private final FacultyRepository facultyRepository = new FacultyRepository();
    private final FacultyService facultyService = new FacultyService(facultyRepository);
    private final DepartmentRepository departmentRepository = new DepartmentRepository();
    private final DepartmentService departmentService = new DepartmentService(new DepartmentRepository());
    private final PersonRepository personRepository = new PersonRepository();
    private final PersonService personService = new PersonService(new PersonRepository());

    public static void main(String[] args) {
        ConsoleInterface ui = new ConsoleInterface();
        ui.run();
    }

    public void run() {
        System.out.println("*---DigiUni Registry---*");
        while(true){
            System.out.println("1 - управління факультетами");
            System.out.println("2 - управління кафедрами");
            System.out.println("3 - управління студентами");
            System.out.println("4 - управління викладачами");
            System.out.println("0 - вихід з програми");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input){
                    case 1:
                        facultyManaging();
                        break;
                    case 2:
                        departmentManaging();
                        break;
                    case 3:
                        studentManaging();
                        break;
                    case 4:
                        teacherManaging();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Введіть коректне значення");
                }
            }catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }
    private void facultyManaging(){
        boolean status = true;
        while(status){
            System.out.println("*-Управління факультетами-*");
            System.out.println("1 - додати факультет");
            System.out.println("2 - змінити факультет");
            System.out.println("3 - видалити факультет");
            System.out.println("0 - повернутись назад");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input){
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
            }catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }

    }
    private Faculty facultyGenerator(){
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

            Optional<Person> optionalPerson = personRepository.findById(teacherId);

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

    private void addFaculty(){
        facultyService.createFaculty(facultyGenerator());
    }
    private void deleteFaculty(){
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
    private void changeFaculty(){
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
    private void departmentManaging(){
        System.out.println("*-Управління кафедрами-*");

    }
    private void studentManaging(){
        System.out.println("\n*-Управління студентами-*");

        while (true) {
            System.out.println("1. Пошук");
            System.out.println("2. Звіти");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> search();
                case "2" -> reports();
                case "0" -> System.exit(0);
                default -> System.out.println("Невірний вибір!");
            }
        }
    }
    private void teacherGenerator(){

    }

    private void search(){
        System.out.println("\n*-Пошук студентів-*");
        while (true) {
            System.out.println("1. Пошук за ПІБ");
            System.out.println("2. Пошук за курсом");
            System.out.println("3. Пошук за групою");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchStudentByFullName();
                case "2" -> searchByCourse();
                case "3" -> searchByGroup();
                case "0" -> System.exit(0);
                default -> System.out.println("Невірний вибір!");
            }
        }
    }

    private void reports(){
        System.out.println("\n*-Списки студентів-*");
        while (true) {
            System.out.println("1. За алфавітом");
            System.out.println("2. За курсом");
            System.out.println("0. Вихід");
            System.out.print("Виберіть опцію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> printAllStudentsAlphabetically();
                case "2" -> printAllStudentsByCourse();
                case "0" -> System.exit(0);
                default -> System.out.println("Невірний вибір!");
            }
        }
    }

    private void teacherManaging(){
        System.out.println("*-управління викладачами-*");

    }
}
