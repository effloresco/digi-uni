package university.ui;

import university.domain.User;
import university.exceptions.UserAlreadyExistsException;
import university.exceptions.UsernameAlreadyUsedException;
import university.repository.UserRepository;
import university.service.UserService;
import university.storage.*;

import java.util.Scanner;

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
        } catch(UserAlreadyExistsException | UsernameAlreadyUsedException _){}
    }


public static void main() {
        MainMenu ui = new MainMenu();
        ui.run();
    }

    public void run() {
        while (true) {
            while (UserService.currentUser == null) {
                auth.auth();
            }
            System.out.println("\n*---DigiUni Registry---*");
            System.out.println("1 - Вихід з акаунта користувача");
            System.out.println("2 - Пошук");
            System.out.println("3 - Звіти");
            if ((UserService.currentUser & User.PERMISSION_EDIT) != 0)
                System.out.println("4 - Управління");
            System.out.println("0 - Вихід з програми");
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
