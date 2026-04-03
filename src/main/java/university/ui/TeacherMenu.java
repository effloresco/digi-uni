package university.ui;

import university.domain.*;
import university.repository.TeacherRepository;
import university.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import university.exceptions.*;
import static university.service.SearchService.*;

public class TeacherMenu {

    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    protected final TeacherService teacherService = new TeacherService(teacherRepository);
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private List<String> changeList = new ArrayList<>();

    private final String opt0 = "0 - Вихід";
    private final String opt1change = "1 - ID";
    private final String opt2change = "2 - Ім'я";
    private final String opt3change = "3 - Прізвище";
    private final String opt4change = "4 - По батькові";
    private final String opt5change = "5 - Дату народження";
    private final String opt6change = "6 - Електронну пошту";
    private final String opt7change = "7 - Номер телефону";
    private final String opt8change = "8 - Посаду";
    private final String opt9change = "9 - Науковий ступінь";
    private final String opt10change = "10 - Вчене звання";
    private final String opt11change = "11 - Дату влаштування на роботу";
    private final String opt12change = "12 - Ставку";

    private List<String> menuOptions = new ArrayList<>();
    private final String opt1g = "1 - Додати викладача";
    private final String opt2g = "2 - Змінити інформацію про викладача";
    private final String opt3g = "3 - Видалити викладача з бази даних";

    protected void teacherManagement() {
        boolean status = true;
        menuOptions.add(opt1g);
        menuOptions.add(opt2g);
        menuOptions.add(opt3g);
        menuOptions.add(opt0);
        while (status) {
            System.out.println("\n*-Управління викладачами-*");
            for (String option : menuOptions) {
                System.out.println(option);
            }
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
        do {
            try {
                teacher.setId(String.valueOf(Utils.getRandomNumber()));
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);
        System.out.println("Ідентифікатор викладача: "+ teacher.getID());

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
                teacher.setHireDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                resume = true;
            } catch (DateTimeParseException e) {
                System.out.println("Введіть коректну дату");
                resume = false;
            }

        } while (!resume);

        System.out.println("Введіть ставку");
        do {
            try {
                teacher.setRate(Double.parseDouble(SearchService.scanner.nextLine()));
                resume = true;
            } catch (InvalidValue e) {
                System.out.println(e.getMessage());
                resume = false;
            }
        } while (!resume);
        System.out.println("Викладача "+teacher.getFullName()+" створено");
        return teacher;
    }


    protected void deleteTeacher() {
        boolean found = false;
        Teacher teacher = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор вчителя, якого треба видалити");
            String teachertId = scanner.nextLine();

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teachertId);
            if (optionalTeacher.isPresent()) {
                teacher = (Teacher) optionalTeacher.get();
                found = true;
            } else{ System.out.println("Викладача з таким ID не знайдено.");
            break;}
        }
        teacherService.deleteTeacher(teacher);
    }

    protected void changeTeacher() {
        boolean found = false;
        String teachertId = null;
        Teacher teacher = null;
        while (!found) {
            System.out.println("Введіть ідентифікатор викладача, що треба замінити");
            teachertId = scanner.nextLine();
            Optional<Teacher> optionalTeacher = teacherRepository.findById(teachertId);

            if (optionalTeacher.isPresent()) {
                found = true;
                teacher = (Teacher) optionalTeacher.get();
                boolean status = true;
                changeList.add(opt1change);
                changeList.add(opt2change);
                changeList.add(opt3change);
                changeList.add(opt4change);
                changeList.add(opt5change);
                changeList.add(opt6change);
                changeList.add(opt7change);
                changeList.add(opt8change);
                changeList.add(opt9change);
                changeList.add(opt10change);
                changeList.add(opt11change);
                changeList.add(opt12change);
                changeList.add(opt0);
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
                                System.out.println("Введіть ідентифікатор викладача");
                                do {
                                    try {
                                        teacher.setId(scanner.nextLine());
                                        resume = true;
                                    } catch (InvalidValue e) {
                                        System.out.println(e.getMessage());
                                        resume = false;
                                    }
                                } while (!resume);
                                break;
                            case 2:
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
                                break;
                            case 3:
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
                                break;
                            case 4:
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
                                break;
                            case 5:
                                do {
                                    try {
                                        teacher.setBirthDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 6:
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
                                break;
                            case 7:
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
                                break;
                            case 8:
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
                                break;
                            case 9:
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
                                break;
                            case 10:
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
                                break;
                            case 11:
                                System.out.println("Введіть дату влаштування на роботу");
                                do {
                                    try {
                                        teacher.setHireDate(LocalDate.parse(SearchService.scanner.nextLine(), formatter));
                                        resume = true;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Введіть коректну дату");
                                        resume = false;
                                    }

                                } while (!resume);
                                break;
                            case 12:
                                System.out.println("Введіть ставку");
                                do {
                                    try {
                                        teacher.setRate(Double.parseDouble(SearchService.scanner.nextLine()));
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


