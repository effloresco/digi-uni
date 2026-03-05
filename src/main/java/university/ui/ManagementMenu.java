package university.ui;

import java.util.Scanner;

public class ManagementMenu {
    protected final Scanner scanner = new Scanner(System.in);

    static void main() {
        ManagementMenu management = new ManagementMenu();
    }

    public void management() {
        FacultyMenu fcManagement = new FacultyMenu();
        StudentMenu stManagement = new StudentMenu();
        DepartmentMenu dpManagement = new DepartmentMenu();
        TeacherMenu tcManagement = new TeacherMenu();

        boolean status = true;
        while (status) {
            System.out.println("\n*-Керування даними:-*");
            System.out.println("1 - Управління факультетами");
            System.out.println("2 - Управління кафедрами");
            System.out.println("3 - Управління студентами");
            System.out.println("4 - Управління викладачами");
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


