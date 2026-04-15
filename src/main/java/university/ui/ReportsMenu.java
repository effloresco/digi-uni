package university.ui;

import java.util.List;

import static university.service.ReportService.*;
import static university.service.SearchService.*;

public class ReportsMenu {

    private final String opt0 = "0 - Вихід";

    private final List<String> menuOptions = List.of(
            "1 - Список студентів",
            "2 - Список викладачів",
            opt0

    );

    private final List<String> studentOptions = List.of(
            "1. Усі за алфавітом",
            "2. Усі за курсом",
            "3. Факультет: за алфавітом",
            "4. Кафедра: за алфавітом",
            "5. Кафедра: за курсом",
            "6. Кафедра + Конкретний курс",
            opt0

    );

    private final List<String> teacherOptions = List.of(
            "1. Усі за алфавітом",
            "2. Факультет: за алфавітом",
            "3. Кафедра: за алфавітом",
            opt0);


    protected void reports() {
        boolean status = true;

        while (status) {
            System.out.println("\n*-Списки-*");
            menuOptions.forEach(System.out::println);

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
                    System.out.println("Введіть коректне значення");
            }
        }
    }

    private void reportStud() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Вивести список студентів-*");
            studentOptions.forEach(System.out::println);

            System.out.print("Виберіть опцію: ");

            try {
                String choice = scanner.nextLine();
                int input = Integer.parseInt(choice);
                switch (input) {
                    case 1:
                        printAllStudentsAlphabetically();
                        break;
                    case 2:
                        printAllStudentsByCourse();
                        break;
                    case 3:
                        System.out.print("Введіть ID факультету: ");
                        String facultyId = scanner.nextLine();
                        printStudentsByFacultyAlphabetically(facultyId);
                        break;

                    case 4: {
                        System.out.print("Введіть ID кафедри: ");
                        String departmentId = scanner.nextLine();
                        printStudentsByDepartmentAlphabetically(departmentId);
                        break;
                    }
                    case 5: {
                        System.out.print("Введіть ID кафедри: ");
                        String departmentId = scanner.nextLine();
                        printStudentsByDepartmentByCourse(departmentId);
                        break;
                    }
                    case 6: {
                        System.out.print("Введіть ID кафедри: ");
                        String departmentId = scanner.nextLine();
                        System.out.print("Введіть курс (1-6): ");
                        int course = Integer.parseInt(scanner.nextLine());
                        printStudentsByDeptAndCourse(departmentId, course);
                        break;
                    }
                    case 0:
                        status = false;
                        break;
                    default:
                        System.out.println("Введіть коректне значення");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число.");
            }
        }
    }

    private void reportTeacher() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Вивести список викладачів-*");
            teacherOptions.forEach(System.out::println);
            System.out.print("Виберіть опцію: ");

            try{
            String choice = scanner.nextLine();

            int input = Integer.parseInt(choice);
            switch (input) {
                case 1:
                    printAllTeachersAlphabetically();
                    break;
                case 2:
                    System.out.print("Введіть ID факультету: ");
                    String facultyId = scanner.nextLine();
                    printTeachersByFacultyAlphabetically(facultyId);
                    break;
                case 3:
                    System.out.print("Введіть ID кафедри: ");
                    String departmentId = scanner.nextLine();
                    printTeachersByDepartmentAlphabetically(departmentId);
                    break;
                case 0:
                    status = false;
                    break;
                default:
                    System.out.println("Введіть коректне значення");
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть коректне число.");
        }
        }
    }
}
