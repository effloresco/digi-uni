package university.ui;

import university.domain.User;
import university.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagementMenu {
    protected final Scanner scanner = new Scanner(System.in);

    static void main() {
        ManagementMenu management = new ManagementMenu();
    }

    private final String opt1 = "1 - Управління факультетами";
    private final String opt2  = "2 - Управління кафедрами";
    private final String opt3  = "3 - Управління студентами";
    private final String opt4  = "4 - Управління викладачами";

    private List<String> menuOptions = new ArrayList<>();

    public void management() {
        FacultyMenu fcManagement = new FacultyMenu();
        StudentMenu stManagement = new StudentMenu();
        DepartmentMenu dpManagement = new DepartmentMenu();
        TeacherMenu tcManagement = new TeacherMenu();
        UserMenu usManagment = new UserMenu();

        boolean status = true;
        menuOptions.add(opt1);
        menuOptions.add(opt2);
        menuOptions.add(opt3);
        menuOptions.add(opt4);
        while (status) {
            System.out.println("\n*-Керування даними:-*");
                for (String option : menuOptions) {
                    System.out.println(option);
                }

            if (UserService.currentUser == User.UserRole.ADMIN)
                System.out.println("5 - Управління користувачами");
            System.out.println("0 - Повернутись назад");
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


