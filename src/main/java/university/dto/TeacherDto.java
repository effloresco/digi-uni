package university.dto;

import lombok.Getter;
import university.domain.Faculty;

import java.time.LocalDate;

@Getter
public class TeacherDto {
    private final String id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final LocalDate birthDate;
    private final String email;
    private final String phone;
    private final String position;
    private final String degree;
    private final String title;
    private final LocalDate hireDate;
    private final double rate;
    private final Faculty faculty;

    public TeacherDto(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String position, String degree, String title, LocalDate hireDate, double rate, Faculty faculty) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.degree = degree;
        this.title = title;
        this.hireDate = hireDate;
        this.rate = rate;
        this.faculty = faculty;
    }
}
