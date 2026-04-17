package university.dto;

import lombok.Getter;
import university.domain.Department;
import university.domain.Faculty;
import university.domain.Student;

import java.time.LocalDate;

@Getter
public class StudentDto {

    private final String id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final LocalDate birthDate;
    private final String email;
    private final String phone;
    private final String studentId;
    private final int course;
    private final String group;
    private final int enrollmentYear;
    private final Student.StudyForm form;
    private final Student.StudentStatus status;
    private final Faculty faculty;
    private final String specialty;
    private final Department department;

    public StudentDto(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String studentId, int course, String group, int enrollmentYear, Student.StudyForm form, Student.StudentStatus status, Faculty  faculty, String specialty, Department department) {
        this.id = id;
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
        this.faculty = faculty;
        this.specialty = specialty;
        this.department = department;
    }

}
