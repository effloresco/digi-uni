package university.ui;

import university.service.Utils;
import university.network.Client;
import university.service.ReportService;
import java.util.List;
import java.util.Scanner;
import static university.service.Utils.*;

public class ReportsMenu {
    private final ReportService reportService;
    public static Scanner scanner = new Scanner(System.in);
    private final String opt0 = "0 - Вихід";

    private final List<String> menuOptions = List.of(
            "[1] Список студентів",
            "[2] Список викладачів",
            OPT0
    );

    private final List<String> studentOptions = List.of(
            "[1] Усі за алфавітом",
            "[2] Усі за курсом",
            "[3] Факультет: за алфавітом",
            "[4] Кафедра: за алфавітом",
            "[5] Кафедра: за курсом",
            "[6] Кафедра + Конкретний курс",
            OPT0
    );

    private final List<String> teacherOptions = List.of(
            "[1] Усі за алфавітом",
            "[2] Факультет: за алфавітом",
            "[3] Кафедра: за алфавітом",
            OPT0);

    public ReportsMenu(Client client) {
        reportService = new ReportService(client);
    }

    protected void reports() {
        boolean status = true;

        while (status) {
            printMenu("Списки", menuOptions);
            System.out.print("Виберіть опцію: ");
            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    reportStud();
                    break;
                case 2:
                    reportTeacher();
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }

    private void reportStud() {
        boolean status = true;
        while (status) {
            printMenu("Вивести список студентів", studentOptions);
            System.out.print("Виберіть опцію: ");
            try {
                String choice = scanner.nextLine();
                int input = Integer.parseInt(choice);
                switch (input) {
                    case 1:
                        reportService.printAllStudentsAlphabetically();
                        break;
                    case 2:
                        reportService.printAllStudentsByCourse();
                        break;
                    case 3:
                        System.out.print("Введіть ID факультету: ");
                        String facultyId = scanner.nextLine();
                        reportService.printStudentsByFacultyAlphabetically(facultyId);
                        break;

                    case 4: {
                        System.out.print("Введіть ID кафедри: ");
                        String departmentId = scanner.nextLine();
                        reportService.printStudentsByDepartmentAlphabetically(departmentId);
                        break;
                    }
                    case 5: {
                        System.out.print("Введіть ID кафедри: ");
                        String departmentId = scanner.nextLine();
                        reportService.printStudentsByDepartmentByCourse(departmentId);
                        break;
                    }
                    case 6: {
                        System.out.print("Введіть ID кафедри: ");
                        String departmentId = scanner.nextLine();
                        System.out.print("Введіть курс (1-6): ");
                        int course = Integer.parseInt(scanner.nextLine());
                        reportService.printStudentsByDeptAndCourse(departmentId, course);
                        break;
                    }
                    case 0:
                        status = false;
                        break;
                    default:
                        printMessage(Utils.Mt.Error, "Некоректне значення");
                }
            } catch (NumberFormatException e) {
                printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        }
    }

    private void reportTeacher() {
        boolean status = true;
        while (status) {
            printMenu("Вивести список викладачів", teacherOptions);

            System.out.print("Виберіть опцію: ");
            try{
            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    reportService.printAllTeachersAlphabetically();
                    break;
                case 2:
                    System.out.print("Введіть ID факультету: ");
                    String facultyId = scanner.nextLine();
                    reportService.printTeachersByFacultyAlphabetically(facultyId);
                    break;
                case 3:
                    System.out.print("Введіть ID кафедри: ");
                    String departmentId = scanner.nextLine();
                    reportService.printTeachersByDepartmentAlphabetically(departmentId);
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    printMessage(Utils.Mt.Error, "Некоректне значення");
            }
        } catch (NumberFormatException e) {
                printMessage(Utils.Mt.Error, "Некоректне значення");
        }
        }
    }
}
