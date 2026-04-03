package university.ui;

import university.domain.User;
import university.repository.UserRepository;
import university.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static university.service.SearchService.scanner;

public class UserMenu {
    protected final UserRepository userRepository = UserRepository.get(UserRepository.class);
    protected final UserService userService = new UserService(userRepository);

    private List<String> roleList = new ArrayList<>();

    private final String opt0 = "0 - Вихід";
    private final String opt1role = "1 - Користувач";
    private final String opt2role = "2 - Менеджер";
    private final String opt3role = "3 - Адміністратор";

    private List<String> menuOptions = new ArrayList<>();
    private final String opt1g = "1 - Додати користувача";
    private final String opt2g = "2 - Змінити користувача";
    private final String opt3g = "3 - Видалити користувача";

    protected void userManagement() {
        boolean status = true;
        menuOptions.add(opt1g);
        menuOptions.add(opt2g);
        menuOptions.add(opt3g);
        menuOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Управління користувачами-*");
            for (String option : menuOptions) {
                System.out.println(option);
            }
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        changeUser();
                        break;
                    case 3:
                        deleteUser();
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

    protected User userGenerator(int id, String allowedUsername) {
        System.out.println("Введіть ім'я користувача (для виходу введіть 0)");
        String username = scanner.nextLine();
        if ("0".equals(username)) return null;
        if (userRepository.checkUsername(username) && !username.equals(allowedUsername)) {
            System.out.println("Це ім'я користувача вже використовується");
            return null;
        }
        System.out.println("Введіть пароль користувача (для виходу введіть 0)");
        String password = scanner.nextLine();
        if ("0".equals(password)) return null;
        User.UserRole role = null;

        roleList.add(opt1role);
        roleList.add(opt2role);
        roleList.add(opt3role);
        roleList.add(opt0);

        while(role == null){
            System.out.println("Оберіть роль користувача");
            for (String option : menuOptions) {
                System.out.println(option);
            }
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        role = User.UserRole.USER;
                        break;
                    case 2:
                        role = User.UserRole.MANAGER;
                        break;
                    case 3:
                        role = User.UserRole.ADMIN;
                        break;
                    case 0:
                        return null;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if(id != 0)
            return new User(username, password, role, id);
        return new User(username, password, role);
    }

    protected void addUser() {
        User user = userGenerator(0, "0");
        if (user == null) return;
        userService.createUser(user);
    }

    protected void deleteUser() {
        boolean found = false;
        boolean exit = false;
        User user = null;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор користувача, якого треба видалити (введіть 0 щоб повернутись назад)");
            String input = scanner.nextLine();
            try {
                int userId = Integer.parseInt(input);
                if (userId == 0)
                    exit = true;
                else{
                    Optional<User> optionalUser = userRepository.findById(userId);

                    if (optionalUser.isPresent()) {
                        user = optionalUser.get();
                        found = true;
                    } else
                        System.out.println("Користувача з таким ID не знайдено.");
                }
            }catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
        if (!exit)
            userService.deleteUser(user);
    }

    protected void changeUser() {
        boolean found = false;
        boolean exit = false;
        String userId = null;
        while (!found && !exit) {
            System.out.println("Введіть ідентифікатор користувача, якого треба замінити (введіть 0 щоб повернутись назад)");
            userId = scanner.nextLine();
            try {
                int id = Integer.parseInt(userId);
                if (id == 0)
                    exit = true;
                else{
                    Optional<User> optionalUser = userRepository.findById(id);

                    if (optionalUser.isPresent()) {
                        found = true;
                        User user = userGenerator(id, optionalUser.get().getUserName());
                        if (user == null) return;
                        userService.deleteUser(optionalUser.get());
                        userService.createUser(user);
                    } else
                        System.out.println("Користувача з таким ID не знайдено.");
                }
            }catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }
}
