package university.ui;

import university.domain.User;
import university.service.UserService;

import java.util.List;
import java.util.Scanner;

public class ManagementMenu {
    protected final Scanner scanner = new Scanner(System.in);

    static void main() {
        ManagementMenu management = new ManagementMenu();
    }

    private final String opt0 = "0 - Вихід";

    private final List<String> menuOptions = List.of("1 - Управління факультетами", "2 - Управління кафедрами", "3 - Управління студентами", "4 - Управління викладачами", opt0);


    public void management() {
        FacultyMenu fcManagement = new FacultyMenu();
        StudentMenu stManagement = new StudentMenu();
        DepartmentMenu dpManagement = new DepartmentMenu();
        TeacherMenu tcManagement = new TeacherMenu();
        UserMenu usManagment = new UserMenu();

        boolean status = true;
        while (status) {
            System.out.println("\n*-Керування даними:-*");
            menuOptions.forEach(System.out::println);

            if ((UserService.currentUser & User.PERMISSION_MANAGE_USERS) != 0) System.out.println("5 - Управління користувачами");
            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        fcManagement.facultyManaging();
                        break;
                    case 2:
                        dpManagement.departmentManagement();
                        break;
                    case 3:
                        stManagement.studentManagement();
                        break;
                    case 4:
                        tcManagement.teacherManagement();
                        break;
                    case 5:
                        if ((UserService.currentUser & User.PERMISSION_MANAGE_USERS) != 0)
                            usManagment.userManagement();
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

}


