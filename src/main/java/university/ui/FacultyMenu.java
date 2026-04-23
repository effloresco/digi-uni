package university.ui;

import university.domain.*;
import university.exceptions.InvalidValue;
import university.network.Client;
import university.repository.FacultyRepository;
import university.repository.TeacherRepository;
import university.service.FacultyService;
import university.service.RemoteDepartmentService;
import university.service.RemoteFacultyService;
import university.storage.FacultyStorageManager;

import static university.service.SearchService.*;

import java.util.List;
import java.util.Optional;

public class FacultyMenu {
    private final Client client;
    protected final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected final RemoteFacultyService facultyService;
    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    protected final FacultyStorageManager facultyStorageManager = new FacultyStorageManager();

    boolean resume;

    private final String opt0 = "0 - Повернутись назад";
    private final List<String> menuOptions = List.of("1 - Додати факультет", "2 - Змінити інформацію про факультет", "3 - Видалити факультет з бази даних", opt0);

    private final List<String> changeList = List.of("1 - Назва", "2 - Коротка назва", "3 - Декан", "4 - Контакти", opt0);
    private String exitOpt = null;

    public FacultyMenu(Client client) {
        this.client = client;
        facultyService = new RemoteFacultyService(client);
    }

    protected void facultyManaging() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління факультетами-*");
            menuOptions.forEach(System.out::println);

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
        Faculty faculty = new Faculty();

        System.out.println("Ідентифікатор факультету: " + faculty.getID());
        
        System.out.println("Введіть назву факультету");
        do {
            try {
                faculty.setName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }

        } while (!resume);

        System.out.println("Введіть коротку назву факультету");
        do {
            try {
                faculty.setShortName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }

        } while (!resume);


        do {
            try {
                faculty.setDean(receiveDean());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
                System.out.println("0 - Пропустити");
                exitOpt = scanner.nextLine();
                if (exitOpt.equals("0")) break;
            }

        } while (!resume);

        System.out.println("Введіть контакти");
        do {
            try {
                faculty.setContacts(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }

        } while (!resume);

        return faculty;

    }

    protected Teacher receiveDean() {
        Teacher dean = null;
        boolean found = false;
        while (!found) {
            System.out.println("Введіть ідентифікатор декана(залиште поле пустим, щоб додати пізніше)");
            String teacherId = scanner.nextLine();

            if (!teacherId.isEmpty()) {
                Optional<Teacher> optionalPerson = teacherRepository.findById(teacherId);

                if (optionalPerson.isPresent()) {
                    dean = optionalPerson.get();
                    found = true;
                } else {
                    System.out.println("Особу з таким ID не знайдено.");
                }
            } else found = true;
        }
        return dean;
    }

    protected void addFaculty() {
        facultyService.createFaculty(facultyGenerator());
    }

    protected void deleteFaculty() {
        boolean found = false;
        Faculty faculty = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор факультету, що треба видалити (нуль, щоб вийти)");
            String facultyId = scanner.nextLine();
            if (facultyId.equals("0")) return;
            facultyService.deleteFaculty(facultyId);
        }
    }

    protected void changeFaculty() {
        boolean found = false;
        boolean exit = false;
        String facultyId;
        Faculty faculty;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор факультету, що треба замінити (введіть 0 щоб повернутись назад)");
            facultyId = scanner.nextLine();

            if (facultyId.equals("0")) exit = true;
            else {
                while (!found) {
                    System.out.println("Введіть ідентифікатор факультету, що треба замінити");
                    facultyId = scanner.nextLine();

                    Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);

                    if (optionalFaculty.isPresent()) {
                        found = true;
                        faculty = optionalFaculty.get();
                        boolean status = true;
                        while (status) {
                            System.out.println("\n*-Оберіть, що змінити-*");
                            changeList.forEach(System.out::println);
                            String inputLine = scanner.nextLine();
                            try {
                                int input = Integer.parseInt(inputLine);
                                switch (input) {
                                    case 1:
                                        System.out.println("Введіть назву факультету");
                                        do {
                                            try {
                                                faculty.setName(scanner.nextLine());
                                                facultyStorageManager.saveAllData();
                                                resume = true;
                                            } catch (InvalidValue e) {
                                                System.out.println(e.getMessage());
                                                resume = false;
                                            }
                                        } while (!resume);
                                        break;
                                    case 2:
                                        System.out.println("Введіть коротку назву факультету");
                                        do {
                                            try {
                                                faculty.setShortName(scanner.nextLine());
                                                facultyStorageManager.saveAllData();
                                                resume = true;
                                            } catch (InvalidValue e) {
                                                System.out.println(e.getMessage());
                                                resume = false;
                                            }
                                        } while (!resume);
                                        break;
                                    case 3:
                                        do {
                                            try {
                                                faculty.setDean(receiveDean());
                                                facultyStorageManager.saveAllData();
                                                resume = true;
                                            } catch (InvalidValue e) {
                                                System.out.println(e.getMessage());
                                                resume = false;
                                                System.out.println("0 - Вихід");
                                                exitOpt = scanner.nextLine();
                                                if (exitOpt.equals("0")) break;
                                            }
                                        } while (!resume);
                                        break;
                                    case 4:
                                        System.out.println("Введіть контакти факультету");
                                        do {
                                            try {
                                                faculty.setContacts(scanner.nextLine());
                                                facultyStorageManager.saveAllData();
                                                resume = true;
                                            } catch (InvalidValue e) {
                                                System.out.println(e.getMessage());
                                                resume = false;
                                            }
                                        } while (!resume);
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
                    } else System.out.println("Кафедри з таким ID не знайдено.");
                }
            }
        }
    }
}
