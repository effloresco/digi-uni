package university.ui;

import university.domain.User;
import university.exceptions.UserAlreadyExistsException;
import university.exceptions.UsernameAlreadyUsedException;
import university.repository.UserRepository;
import university.service.UserService;
import university.storage.*;

import java.util.List;
import java.util.Scanner;

import static university.service.Utils.*;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ManagementMenu management = new ManagementMenu();
    private final AuthMenu auth = new AuthMenu();
    private final ReportsMenu reportsMenu = new ReportsMenu();
    private final SearchMenu searchMenu = new SearchMenu();
    private static final UserRepository userRepository = UserRepository.get(UserRepository.class);
    private static final UserService userService = new UserService(userRepository);
    private static final StudentStorageManager studentStorageManager = new StudentStorageManager();
    private static final TeacherStorageManager teacherStorageManager = new TeacherStorageManager();
    private static final DepartmentStorageManager departmentStorageManager = new DepartmentStorageManager();
    private static final FacultyStorageManager facultyStorageManager = new FacultyStorageManager();
    private static final ServiceStorageManager serviceStorageManager = new ServiceStorageManager();
    private static final UserStorageManager userStorageManager = new UserStorageManager();
    private final List<String> menuOptions = List.of(
            "[1] Вихід з акаунта користувача",
            "[2] Пошук",
            "[3] Звіти",
            "[4] Управління",
            OPT0);
    private final int PERMISSION_EDIT_INDEX = 3;

    static {
        //loads all data from the storage
        studentStorageManager.loadAllData();
        teacherStorageManager.loadAllData();
        departmentStorageManager.loadAllData();
        facultyStorageManager.loadAllData();
        serviceStorageManager.loadAllData();
        userStorageManager.loadAllData();
        //adds default admin user
        try {
            userService.createUser(new User("admin", "12345678", User.PERMISSION_VIEW | User.PERMISSION_EDIT | User.PERMISSION_MANAGE_USERS));
        } catch (UserAlreadyExistsException | UsernameAlreadyUsedException _) {
        }
    }

    static void main() {
        MainMenu ui = new MainMenu();
        ui.run();
    }

    public void run() {
        List<String> options;
        while (true) {
            while (UserService.currentUser == null) {
                auth.auth();
            }

            if ((UserService.currentUser & User.PERMISSION_EDIT) != 0)
                options = menuOptions;
            else
                options = menuOptions.stream()
                        .filter(opt -> menuOptions.indexOf(opt) != PERMISSION_EDIT_INDEX)
                        .toList();
            printMenu("DigiUni Registry", options);
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        UserService.currentUser = null;
                        continue;
                    case 2:
                        searchMenu.searchOptions();
                        break;
                    case 3:
                        reportsMenu.reports();
                        break;
                    case 4:
                        if ((UserService.currentUser & User.PERMISSION_EDIT) != 0)
                            management.management();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }
}