package university.dto;

import lombok.Getter;
import university.domain.Student;

import java.time.LocalDate;

@Getter
public class StudentDto {

    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String studentId;
    private int course;
    private String group;
    private int enrollmentYear;
    private Student.StudyForm form;
    private Student.StudentStatus status;

    public StudentDto(String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String studentId, int course, String group, int enrollmentYear, Student.StudyForm form, Student.StudentStatus status) {
        this.studentId = studentId;
        this.course = course;
        this.group = group;
        this.enrollmentYear = enrollmentYear;
        this.form = form;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
    }


}
