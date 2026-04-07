package university.ui;

import university.domain.*;
import university.repository.TeacherRepository;
import university.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import university.exceptions.*;

import static university.service.SearchService.*;

public class TeacherMenu {

    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    protected final TeacherService teacherService = new TeacherService(teacherRepository);
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final String opt0 = "0 - Вихід";
    private List<String> changeList = List.of("1 - Ім'я", "2 - Прізвище", "3 - По батькові", "4 - Дату народження", "5 - Електронну пошту", "6 - Номер телефону", "7 - Посаду", "8 - Науковий ступінь", "9 - Вчене звання", "10 - Дату влаштування на роботу", "11 - Ставку", opt0);
    private List<String> menuOptions = List.of("1 - Додати викладача", "2 - Змінити інформацію про викладача", "3 - Видалити викладача з бази даних", opt0);

    protected void teacherManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління викладачами-*");
            menuOptions.forEach(System.out::println);

            String inputLine = scanner.nextLine();
            try {
                int input = Integer.parseInt(inputLine);
                switch (input) {
                    case 1:
                        addTeacher();
                        break;
                    case 2:
                        changeTeacher();
                        break;
                    case 3:

                        deleteTeacher();
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

    protected Teacher teacherGenerator() {
        Teacher teacher = new Teacher();

        System.out.println("Ідентифікатор викладача: " + teacher.getID());

        System.out.println("Введіть ім'я");
        do {
            try {
                teacher.setFirstName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть прізвище");
        do {
            try {
                teacher.setLastName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть по батькові");
        do {
            try {
                teacher.setMiddleName(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть дату народження викладача");
        do {
            try {
                teacher.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                resume = true;
            } catch (DateTimeParseException e) {
                System.out.println("Введіть коректну дату");
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть електронну пошту");
        do {
            try {
                teacher.setEmail(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть номер телефону");
        do {
            try {
                teacher.setPhone(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть посаду");
        do {
            try {
                teacher.setPosition(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть  науковий ступінь");
        do {
            try {
                teacher.setDegree(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть вчене звання");
        do {
            try {
                teacher.setTitle(scanner.nextLine());
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть дату влаштування на роботу");
        do {
            try {
                teacher.setHireDate(LocalDate.parse(scanner.nextLine(), formatter));
                resume = true;
            } catch (DateTimeParseException e) {
                System.out.println("Введіть коректну дату");
                resume = false;
            }
        } while (!resume);

        System.out.println("Введіть ставку");
        do {
            try {
                double inputRate = Double.parseDouble(scanner.nextLine());
                teacher.setRate(inputRate);
                resume = true;
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число");
                resume = false;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);

        System.out.println("Викладача " + teacher.getFullName() + " створено");
        return teacher;
    }

    protected void deleteTeacher() {
        boolean found = false;
        Teacher teacher = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор вчителя, якого треба видалити (нуль, щоб вийти)");
            String teacherId = scanner.nextLine();
            if (teacherId.equals("0")) {
                return;
            }

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                teacher = optionalTeacher.get();
                found = true;
                teacherService.deleteTeacher(teacher);
            } else {
                System.out.println("Викладача з таким ID не знайдено.");
                break;
            }
        }

    }

    protected void changeTeacher() {
        boolean found = false;
        String teachertId;
        Teacher teacher;
        while (!found) {
            System.out.println("Введіть ідентифікатор викладача, що треба замінити");
            teachertId = scanner.nextLine();
            Optional<Teacher> optionalTeacher = teacherRepository.findById(teachertId);

            if (optionalTeacher.isPresent()) {
                found = true;
                teacher = optionalTeacher.get();
                boolean status = true;
                changeList.forEach(System.out::println);

                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    for (String option : menuOptions) {
                        System.out.println(option);
                    }
                    String inputLine = scanner.nextLine();
                    try {
                        int input = Integer.parseInt(inputLine);
                        switch (input) {
                            case 1:
                                System.out.println("Введіть ім'я");
                                do {
                                    try {
                                        teacher.setFirstName(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 2:
                                System.out.println("Введіть прізвище");
                                do {
                                    try {
                                        teacher.setLastName(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 3:
                                System.out.println("Введіть по батькові");
                                do {
                                    try {
                                        teacher.setMiddleName(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 4:
                                do {
                                    try {
                                        teacher.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 5:
                                System.out.println("Введіть електронну пошту");
                                do {
                                    try {
                                        teacher.setEmail(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 6:
                                System.out.println("Введіть номер телефону");
                                do {
                                    try {
                                        teacher.setPhone(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 7:
                                System.out.println("Введіть посаду");
                                do {
                                    try {
                                        teacher.setPosition(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 8:
                                System.out.println("Введіть  науковий ступінь");
                                do {
                                    try {
                                        teacher.setDegree(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 9:
                                System.out.println("Введіть вчене звання");
                                do {
                                    try {
                                        teacher.setTitle(scanner.nextLine());
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 10:
                                System.out.println("Введіть дату влаштування на роботу");
                                do {
                                    try {
                                        teacher.setHireDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        teacherService.saveAllData();
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 11:
                                System.out.println("Введіть ставку");
                                do {
                                    try {
                                        teacher.setRate(Double.parseDouble(SearchService.scanner.nextLine()));
                                        teacherService.saveAllData();
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

    protected void addTeacher() {
        teacherService.createTeacher(teacherGenerator());
    }

}


