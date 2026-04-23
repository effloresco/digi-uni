package university.domain;

import lombok.Getter;
import lombok.Setter;
import university.exceptions.InvalidValue;
import university.repository.DepartmentRepository;
import university.repository.FacultyRepository;

import java.time.LocalDate;
import java.util.Optional;

import static university.service.Utils.*;

@Getter
public non-sealed class Student extends Person {
    protected transient final FacultyRepository facultyRepository = FacultyRepository.get(FacultyRepository.class);
    protected transient final DepartmentRepository departmentRepository = DepartmentRepository.get(DepartmentRepository.class);
    public enum StudyForm {BUDGET, CONTRACT}

    public enum StudentStatus {STUDYING, ACADEMIC_LEAVE, EXPELLED}

    private String studentId;
    private int course;
    private String group;
    private int enrollmentYear;
    private StudyForm form;
    private StudentStatus status;
    @Setter
    private String facultyId;
    private String specialty;
    @Setter
    private String departmentId;

    public Student() {}

    public Student(String id, String lastName, String firstName, String middleName, LocalDate birthDate, String email, String phone, String studentId, int course, String group, int enrollmentYear, StudyForm form, StudentStatus status, String facultyId, String specialty, String departmentId) throws InvalidValue {
        super(id, lastName, firstName, middleName, birthDate, email, phone);
        setStudentId(studentId);
        setCourse(course);
        setGroup(group);
        setEnrollmentYear(enrollmentYear);
        setStudyForm(form);
        setStudentStatus(status);
        this.facultyId = facultyId;
        setSpecialty(specialty);
        this.departmentId = departmentId;
    }

    public void setSpecialty(String specialty) throws InvalidValue{
        if (containsNonLetter(String.valueOf(specialty))) {
            throw new InvalidValue("Назва спеціальносі може містити лише літери");
        }
        this.specialty = specialty;
    }

    public void setStudentId(String studentId) throws InvalidValue {
        if (containsNonDigit(String.valueOf(studentId))) {
            throw new InvalidValue("Ідентифікатор може містити лише цифри");
        }
        this.studentId = studentId;
    }

    public void setCourse(int course) throws InvalidValue {
        if (containsNonDigit(String.valueOf(course)) || (course < 1 || course > 6)) {
            throw new InvalidValue("Курс має бути від 1 до 6");
        }
        this.course = course;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setEnrollmentYear(int year) throws InvalidValue {
        if (containsNonDigit(String.valueOf(year)) || (String.valueOf(year).length() != 4) || (year < 1900 || year > 2100)) {
            throw new InvalidValue("Некоректний рік");
        }
        this.enrollmentYear = year;
    }

    public void setStudyForm(StudyForm form) {
        this.form = form;
    }

    public void setStudentStatus(StudentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" + "details=" + super.toString() + // Виклик toString() з Person
                ", studentId='" + studentId + '\'' + ", course=" + course + ", group='" + group + '\'' + ", enrollmentYear=" + enrollmentYear + ", form=" + form + ", status=" + status + '}';
    }


}