package university.ui;

import university.domain.*;
import university.repository.TeacherRepository;
import university.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Random;
import university.exceptions.*;
import static university.service.SearchService.*;

public class TeacherMenu {

    protected final TeacherRepository teacherRepository = TeacherRepository.get(TeacherRepository.class);
    protected final TeacherService teacherService = new TeacherService(teacherRepository);
    boolean resume;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    int min = 0;
    int max = 999999999;
    Random random = new Random();
    Integer randomNumber = random.nextInt(max - min + 1) + min;

    protected void teacherManagement() {
        boolean status = true;
        while (status) {
            System.out.println("\n*-Управління викладачами-*");
            System.out.println("1 - додати викладача");
            System.out.println("2 - змінити інформацію про викладача");
            System.out.println("3 - видалити викладача з бази даних");
            System.out.println("0 - повернутись назад");
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
                teacher.setId(String.valueOf(random.nextInt(max - min + 1) + min));
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
                while (status) {
                    System.out.println("\n*-Оберіть, що змінити-*");
                    System.out.println("1 - ID");
                    System.out.println("2 - Ім'я");
                    System.out.println("3 - Прізвище");
                    System.out.println("4 - По батькові");
                    System.out.println("5 - Дату народження");
                    System.out.println("6 - Електронну пошту");
                    System.out.println("7 - Номер телефону");
                    System.out.println("8 - Посаду");
                    System.out.println("9 - Науковий ступінь");
                    System.out.println("10 - Вчене звання");
                    System.out.println("11 - Дату влаштування на роботу");
                    System.out.println("12 - Ставку");
                    System.out.println("0 - повернутись назад");
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


