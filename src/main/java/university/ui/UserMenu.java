package university.ui;

import university.domain.Student;
import university.domain.User;
import university.exceptions.InvalidValue;
import university.exceptions.PersonNotFoundException;
import university.exceptions.UserNotFoundException;
import university.network.Client;
import university.repository.UserRepository;
import university.service.RemoteUserService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static university.domain.Student.StudyForm.BUDGET;
import static university.domain.Student.StudyForm.CONTRACT;

public class UserMenu {
    private final Client client;
    private final Scanner scanner = new Scanner(System.in);
    protected final UserRepository userRepository = UserRepository.get(UserRepository.class);
    protected final RemoteUserService userService;

    private final String opt0 = "0 - Вихід";
    private final List<String> roleOptions = List.of("1 - Користувач", "2 - Менеджер", "3 - Адміністратор", opt0);
    private final List<String> menuOptions = List.of("1 - Додати користувача", "2 - Змінити користувача", "3 - Видалити користувача", opt0);
    private List<String> changeList = List.of(
            "1 - Ім'я користувача",
            "2 - Пароль",
            "3 - Роль",
            opt0);
    boolean resume;

    public UserMenu(Client client) {
        this.client = client;
        userService = new RemoteUserService(client);
    }

    protected void userManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління користувачами-*");
            menuOptions.forEach(System.out::println);
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
        Integer role = null;

        while (role == null) {
            System.out.println("Оберіть роль користувача");
            roleOptions.forEach(System.out::println);
            String inputLine = scanner.nextLine();

            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        role = User.PERMISSION_VIEW;
                        break;
                    case 2:
                        role = User.PERMISSION_VIEW | User.PERMISSION_EDIT;
                        break;
                    case 3:
                        role = User.PERMISSION_VIEW | User.PERMISSION_EDIT | User.PERMISSION_MANAGE_USERS;
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
        if (id != 0) return new User(id, username, password, role);
        return new User(username, password, role);
    }

    protected void addUser() {
        User user = userGenerator(0, "0");
        if (user == null) return;
        userService.createUser(user);
    }

    protected void deleteUser() {
        while (true) {
            System.out.println("Введіть ідентифікатор користувача, якого треба видалити (введіть 0 щоб повернутись назад)");
            String input = scanner.nextLine();
            if (input.equals("0")) return;
            try {
                int userId = Integer.parseInt(input);
                userService.deleteUser(userId);

            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне значення");
            }
        }
    }

    protected void changeUser() {
        boolean found = false;
        String userId;
        User user;
        while (!found) {
            try {
                System.out.println("Введіть ідентифікатор користувача, якого треба змінити");
                userId = scanner.nextLine();
                user = userService.getUser(userId);
                found = true;
                boolean status = true;
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    changeList.forEach(System.out::println);
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть нове ім'я користувача");
                                do {
                                    try {
                                        user.setUserName(scanner.nextLine());
                                        userService.updateUser(user);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 2:
                                System.out.println("Введіть новий пароль");
                                do {
                                    try {
                                        user.setPassword(scanner.nextLine());
                                        userService.updateUser(user);
                                        resume = true;
                                    } catch (InvalidValue | PersonNotFoundException e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 3:
                                System.out.println("Оберіть нову роль");
                                do {
                                    status = true;
                                    System.out.println("\n*-Оберіть роль користувача: -*");
                                    roleOptions.forEach(System.out::println);
                                    String inputLocalLine = scanner.nextLine();
                                    try {
                                        int inputLocal = Integer.parseInt(inputLocalLine);
                                        switch (inputLocal) {
                                            case 1:
                                                user.setUserPermissions(User.PERMISSION_VIEW);
                                                userService.updateUser(user);
                                                break;
                                            case 2:
                                                user.setUserPermissions(User.PERMISSION_VIEW | User.PERMISSION_EDIT);
                                                userService.updateUser(user);
                                                break;
                                            case 3:
                                                user.setUserPermissions(User.PERMISSION_VIEW | User.PERMISSION_EDIT | User.PERMISSION_MANAGE_USERS);
                                                userService.updateUser(user);
                                                break;
                                            case 0:
                                                resume = true;
                                                break;
                                            default:
                                                System.out.println("Введіть коректне значення");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Введіть коректне значення");
                                    } catch (InvalidValue | PersonNotFoundException e) {
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
            } catch (UserNotFoundException e) {
                System.out.println("Студента з таким ID не знайдено.");
            }
        }
    }
}
